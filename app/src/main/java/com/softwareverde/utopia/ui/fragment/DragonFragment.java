package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.Dragon;
import com.softwareverde.utopia.Kingdom;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.UtopiaUtil;

public class DragonFragment extends Fragment {
    public static final String THRONE_CALLBACK_IDENTIFIER = "DragonFragment_ThroneUpdateCallbackIdentifier";

    private Activity _activity;
    private View _view;

    private Session _session;

    public DragonFragment() { }

    private void _setButtonPressedStyle(Integer button, Boolean isPressed) {
        if (isPressed) {
            _view.findViewById(button).setBackgroundColor(Color.parseColor("#202020"));
        }
        else {
            _view.findViewById(button).setBackgroundResource(android.R.drawable.dialog_holo_dark_frame);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dragon, container, false);
        _view = rootView;

        final EditText fundDragonAmount = (EditText) _view.findViewById(R.id.dragon_fund_dragon);

        final Button submitButton = (Button) _view.findViewById(R.id.dragon_fund_dragon_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Integer fundedAmount = Util.parseInt(fundDragonAmount.getText().toString());
                _session.fundDragon(fundedAmount, new Session.Callback() {
                    @Override
                    public void run(final Session.SessionResponse response) {

                        final TextView fundDragonResultTextView = (TextView) _view.findViewById(R.id.fund_dragon_result_text);

                        if (! response.getWasSuccess()) {
                            _activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fundDragonResultTextView.setText(response.getErrorMessage());
                                }
                            });
                            return;
                        }

                        _session.downloadThrone(new Session.Callback() {
                            @Override
                            public void run(Session.SessionResponse response) {
                                _session.downloadFundDragonInfo(new Session.Callback() {
                                    @Override
                                    public void run(Session.SessionResponse response) {
                                        _activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                fundDragonResultTextView.setText("You have donated " + Util.formatNumberString(fundedAmount) + " gold coins to the quest of launching a dragon.");
                                                fundDragonAmount.setText("");
                                                _drawData();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });


        // Set initial button settings
        _setButtonPressedStyle(R.id.dragon_tab_attack_dragon, false);
        _setButtonPressedStyle(R.id.dragon_tab_fund_dragon, true);
        _view.findViewById(R.id.dragon_attack_dragon_layout).setVisibility(View.GONE);
        _view.findViewById(R.id.dragon_fund_dragon_layout).setVisibility(View.VISIBLE);


        _view.findViewById(R.id.dragon_tab_attack_dragon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _setButtonPressedStyle(R.id.dragon_tab_attack_dragon, true);
                _setButtonPressedStyle(R.id.dragon_tab_fund_dragon, false);

                _view.findViewById(R.id.dragon_attack_dragon_layout).setVisibility(View.VISIBLE);
                _view.findViewById(R.id.dragon_fund_dragon_layout).setVisibility(View.GONE);

                ((TextView) _view.findViewById(R.id.attack_dragon_result_text)).setText("");
            }
        });
        _view.findViewById(R.id.dragon_tab_fund_dragon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _setButtonPressedStyle(R.id.dragon_tab_attack_dragon, false);
                _setButtonPressedStyle(R.id.dragon_tab_fund_dragon, true);

                _view.findViewById(R.id.dragon_attack_dragon_layout).setVisibility(View.GONE);
                _view.findViewById(R.id.dragon_fund_dragon_layout).setVisibility(View.VISIBLE);

                ((TextView) _view.findViewById(R.id.fund_dragon_result_text)).setText("");
            }
        });

        _view.findViewById(R.id.dragon_attack_dragon_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtopiaUtil.AttackDragonArmy attackDragonArmy = new UtopiaUtil.AttackDragonArmy();
                attackDragonArmy.soldiers = Util.parseInt(((EditText) _view.findViewById(R.id.dragon_attack_dragon_soldiers)).getText().toString());
                attackDragonArmy.offensiveUnits = Util.parseInt(((EditText) _view.findViewById(R.id.dragon_attack_dragon_offensive_units)).getText().toString());
                attackDragonArmy.defensiveUnits = Util.parseInt(((EditText) _view.findViewById(R.id.dragon_attack_dragon_defensive_units)).getText().toString());
                attackDragonArmy.elites = Util.parseInt(((EditText) _view.findViewById(R.id.dragon_attack_dragon_elites)).getText().toString());

                _session.attackDragon(attackDragonArmy, new Session.Callback() {
                    @Override
                    public void run(final Session.SessionResponse response) {
                        final TextView resultArea = (TextView) _view.findViewById(R.id.attack_dragon_result_text);

                        if (! response.getWasSuccess()) {
                            _activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    resultArea.setText(response.getErrorMessage());
                                }
                            });

                            return;
                        }

                        _session.downloadThrone(new Session.Callback() {
                            @Override
                            public void run(Session.SessionResponse response) {
                                _session.downloadAttackDragonInfo(new Session.Callback() {
                                    @Override
                                    public void run(Session.SessionResponse response) {
                                        // TODO: Parse result text and display here.
                                        _activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                resultArea.setText("You send out troops to fight the dragon. All are lost in the fight, but the dragon is weakened.");

                                                ((TextView) _view.findViewById(R.id.dragon_attack_dragon_current_soldiers)).setText("");
                                                ((TextView) _view.findViewById(R.id.dragon_attack_dragon_current_offensive_units)).setText("");
                                                ((TextView) _view.findViewById(R.id.dragon_attack_dragon_current_defensive_units)).setText("");
                                                ((TextView) _view.findViewById(R.id.dragon_attack_dragon_current_elites)).setText("");

                                                _drawDragonHealth();
                                                _drawData();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });


        // Padding-Right Hack: (Bug in Android)
        Integer[] padRightIds = new Integer[] {
                R.id.dragon_fund_dragon, R.id.dragon_attack_dragon_soldiers, R.id.dragon_attack_dragon_offensive_units,
                R.id.dragon_attack_dragon_defensive_units, R.id.dragon_attack_dragon_elites
        };
        for (Integer viewId : padRightIds) {
            EditText editText = ((EditText) _view.findViewById(viewId));
            editText.setPadding(editText.getPaddingLeft(), editText.getPaddingTop(), 40, editText.getPaddingBottom());
        }

        _drawData();

        _session.addThroneCallback(THRONE_CALLBACK_IDENTIFIER, new Runnable() {
            public void run() {
                _activity.runOnUiThread(new Runnable() {
                    public void run() {
                        _drawData();
                    }
                });
            }
        });

        _session.downloadAttackDragonInfo(new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                _drawDragonHealth();
            }
        });

        _session.downloadFundDragonInfo(new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _drawData();
                    }
                });
            }
        });

        return rootView;
    }

    private void _drawDragonHealth() {
        Kingdom kingdom = _session.getKingdom();

        if (Util.coalesce(kingdom.hasDragon(), false)) {
            Dragon dragon = kingdom.getDragon();

            Integer dragonHealth = dragon.getHealth();
            Integer dragonMaxHealth = dragon.getMaxHealth();

            LinearLayout dragonHealthPercentView = (LinearLayout) _view.findViewById(R.id.dragon_health_percent);
            LinearLayout dragonHealthAntiPercentView = (LinearLayout) _view.findViewById(R.id.dragon_health_anti_percent);

            dragonHealthPercentView.setBackgroundColor(Color.parseColor(dragon.getColorString()));

            if (dragonHealth != null && dragonMaxHealth != null) {
                LinearLayout.LayoutParams dragonHealthLayoutParams = (LinearLayout.LayoutParams) dragonHealthPercentView.getLayoutParams();
                LinearLayout.LayoutParams antiDragonHealthLayoutParams = (LinearLayout.LayoutParams) dragonHealthAntiPercentView.getLayoutParams();
                Float healthPercent = (float) dragonHealth / (float) dragonMaxHealth;
                dragonHealthLayoutParams.weight = healthPercent;
                antiDragonHealthLayoutParams.weight = 1.0F - healthPercent;
            }

            ((TextView) _view.findViewById(R.id.dragon_health)).setText(Util.formatNumberString(dragonHealth));
        }
    }

    private void _drawData() {
        if (_view == null) {
            return;
        }

        Province province = _session.getProvince();
        Kingdom kingdom = _session.getKingdom();

        if (province == null || kingdom == null) {
            return;
        }

        // ((TextView) _view.findViewById(R.id.dragon_kingdom_name)).setText(_session.getKingdom().getName());

        ((TextView) _view.findViewById(R.id.dragon_cost)).setText(Util.formatNumberString(kingdom.getDragonCostRemaining()));

        ((TextView) _view.findViewById(R.id.dragon_fund_dragon_gc_available)).setText(Util.formatNumberString(province.getMoney()));

        ((TextView) _view.findViewById(R.id.dragon_attack_dragon_current_soldiers)).setText(Util.formatNumberString(province.getSoldiers()));
        ((TextView) _view.findViewById(R.id.dragon_attack_dragon_current_offensive_units)).setText(Util.formatNumberString(province.getOffensiveUnits()));
        ((TextView) _view.findViewById(R.id.dragon_attack_dragon_current_defensive_units)).setText(Util.formatNumberString(province.getDefensiveUnits()));
        ((TextView) _view.findViewById(R.id.dragon_attack_dragon_current_elites)).setText(Util.formatNumberString(province.getElites()));

        if (kingdom.hasDragon()) {
            _view.findViewById(R.id.dragon_tab_layout).setVisibility(View.VISIBLE);
            _drawDragonHealth();
        }
        else {
            _view.findViewById(R.id.dragon_tab_layout).setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
        _session = Session.getInstance();
    }

    @Override
    public void onDestroy() {
        _session.removeThroneCallback(THRONE_CALLBACK_IDENTIFIER);

        super.onDestroy();
    }
}