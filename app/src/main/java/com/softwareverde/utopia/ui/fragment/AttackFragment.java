package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softwareverde.util.Dialog;
import com.softwareverde.util.StringUtil;
import com.softwareverde.utopia.Util;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.UtopiaUtil;
import com.softwareverde.utopia.bundle.AttackBundle;
import com.softwareverde.utopia.ui.dialog.EditOptionDialog;
import com.softwareverde.utopia.ui.dialog.EditValueDialog;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class AttackFragment extends Fragment {
    private Province _provice;
    private Province _targetProvince;
    private Activity _activity;
    private Session _session;
    private View _view;

    private UtopiaUtil.Attack _attack;
    private UtopiaUtil.Army _army;

    public AttackFragment() { }
    public void setTargetProvince(Province province) {
        _setTargetProvince(province);
    }
    private void _setTargetProvince(Province province) {
        _targetProvince = province;
        // _targetProvince = _session.loadProvinceFromStore(_targetProvince);

        if (_attack != null) {
            _attack.setTargetProvince(_targetProvince);
        }

        if (_targetProvince != null) {
            if (_targetProvince.getKingdomIdentifier() == null) {
                System.out.println("WARNING: Using a province without an identifier within AttackFragment.");
            }
        }
    }
    public Province getTargetProvince() {
        return _targetProvince;
    }

    private void _showLoadingScreen() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.showLoadingScreen(_activity);
            }
        });
    }
    private void _hideLoadingScreen() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.hideLoadingScreen();
            }
        });
    }

    private Integer _defenseOverride = null;
    private Integer _getTargetDefense() {
        if (_defenseOverride != null) {
            return _defenseOverride;
        }

        Integer defenseAtHome = _targetProvince.getTotalDefenseAtHome();
        if (defenseAtHome != null) {
            defenseAtHome += _targetProvince.getAcres();
        }
        else {
            defenseAtHome = _targetProvince.getAcres();
        }

        return defenseAtHome;
    }

    private void _calculateAttackTime() {
        _session.calculateAttackTime(_attack, new Session.CalculateAttackTimeCallback() {
            @Override
            public void run(final Float attackTime) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView) _view.findViewById(R.id.attack_calculated_attack_time)).setText(Util.formatPercentString(attackTime) + " days");
                    }
                });
            }
        });
    }

    private void _drawData() {
        if (_view == null) { return; }

        TextView attackSuccessPercentText = (TextView) _view.findViewById(R.id.attack_success_percent_text);
        attackSuccessPercentText.setText(" ");

        Integer successBarColor = Color.parseColor("#AA0000");
        String provinceOffenseText = "-";
        LinearLayout attackSuccessPercentView = (LinearLayout) _view.findViewById(R.id.attack_success_percent);
        LinearLayout attackAntiSuccessPercentView = (LinearLayout) _view.findViewById(R.id.attack_success_anti_percent);
        LinearLayout.LayoutParams successLayoutParams = (LinearLayout.LayoutParams) attackSuccessPercentView.getLayoutParams();
        LinearLayout.LayoutParams antiSuccessLayoutParams = (LinearLayout.LayoutParams) attackAntiSuccessPercentView.getLayoutParams();;
        successLayoutParams.weight = 0.0F;
        antiSuccessLayoutParams.weight = 1.0F;

        if (_attack.isOffenseCalculated()) {
            Integer offenseCalculated = _attack.getCalculatedOffense();
            provinceOffenseText = StringUtil.formatNumberString(offenseCalculated);
            Integer defenseAtHome = _getTargetDefense();
            if (defenseAtHome != null && defenseAtHome > 0) {
                Float offenseRatio = ((float) offenseCalculated) / ((float) defenseAtHome);
                // provinceOffenseText += " - "+ Util.formatPercentString(offenseRatio * 100.0F) +"%";

                final Float oversendRatio = 1.08F;
                final Float successRatio = 1.040362694F;
                final Float unlikelyRatio = 0.97F;
                Float successPercent = offenseRatio / successRatio;
                if (successPercent > 1.0F) {
                    successPercent = 1.0F;
                }

                successLayoutParams.weight = successPercent;
                antiSuccessLayoutParams.weight = 1.0F - successPercent;

                attackSuccessPercentText.setText(Util.formatPercentString(successPercent * 100.0F) +"%");

                // Determine Success-Bar Color
                if (offenseRatio >= oversendRatio) {
                    successBarColor = Color.parseColor("#CC6600");
                }
                else if (offenseRatio >= successRatio) {
                    successBarColor = Color.parseColor("#00AA00");
                }
                else if (offenseRatio >= unlikelyRatio) {
                    successBarColor = Color.parseColor("#CCCC00");
                }
            }
        }
        ((TextView) _view.findViewById(R.id.attack_province_offense)).setText(provinceOffenseText);
        attackSuccessPercentView.setBackgroundColor(successBarColor);
        attackSuccessPercentView.setLayoutParams(successLayoutParams);
        attackAntiSuccessPercentView.setLayoutParams(antiSuccessLayoutParams);

        String defenseAtHomeString = "??";
        if (_targetProvince != null) {
            Integer defenseAtHome = _getTargetDefense();
            defenseAtHomeString = StringUtil.formatNumberString(defenseAtHome);
        }
        TextView defenseTextView = ((TextView) _view.findViewById(R.id.attack_province_defense));
        defenseTextView.setText(defenseAtHomeString);

        if (_targetProvince != null) {
            ((TextView) _view.findViewById(R.id.attack_title_label)).setText(_targetProvince.getName());
        }
        else {
            ((TextView) _view.findViewById(R.id.attack_title_label)).setText("Attack Province");
        }

        Float networthRatio = ((float) _targetProvince.getNetworth()) / ((float) _provice.getNetworth());
        ((TextView) _view.findViewById(R.id.attack_networth_ratio)).setText(Util.formatPercentString(networthRatio));

        ((TextView) _view.findViewById(R.id.attack_attack_type)).setText(_attack.getType() == null ? "" : UtopiaUtil.Attack.getDisplayNameForAttackType(_attack.getType()));
        ((TextView) _view.findViewById(R.id.attack_attack_time)).setText(_attack.getTime() == null ? "" : UtopiaUtil.Attack.getDisplayNameForAttackTime(_attack.getTime()));

        ((TextView) _view.findViewById(R.id.attack_soldiers_count)).setText(StringUtil.formatNumberString(_provice.getSoldiersHome()));
        ((TextView) _view.findViewById(R.id.attack_attackers_count)).setText(StringUtil.formatNumberString(_provice.getOffensiveUnitsHome()));
        ((TextView) _view.findViewById(R.id.attack_horses_count)).setText(StringUtil.formatNumberString(_provice.getHorsesHome()));
        ((TextView) _view.findViewById(R.id.attack_elites_count)).setText(StringUtil.formatNumberString(_provice.getElitesHome()));
        ((TextView) _view.findViewById(R.id.attack_prisoners_count)).setText(StringUtil.formatNumberString(_provice.getPrisoners()));

        Integer maxPrisoners = 0;
        if (_army != null && _provice.getPrisoners() != null &&  _provice.getMercenaryRate() != null) {
            Integer armySize = Util.coalesce(_army.getSoldiers()) + Util.coalesce(_army.getOffensiveUnits()) + Util.coalesce(_army.getElites()) - Util.coalesce(_army.getMercenaries());
            maxPrisoners = (int) (((float) armySize) * _provice.getMercenaryRate());
            if (maxPrisoners > _provice.getPrisoners()) {
                maxPrisoners = _provice.getPrisoners();
            }
            if (maxPrisoners < 0) {
                maxPrisoners = 0;
            }
        }
        ((TextView) _view.findViewById(R.id.attack_max_prisoners)).setText(StringUtil.formatNumberString(maxPrisoners) +" Maximum");

        Integer maxMercenaries = 0;
        if (_army != null && _provice.getMercenaryCost() != null && _provice.getMercenaryRate() != null) {
            Integer armySize = Util.coalesce(_army.getSoldiers()) + Util.coalesce(_army.getOffensiveUnits()) + Util.coalesce(_army.getElites()) - Util.coalesce(_army.getPrisoners());
            maxMercenaries = (int) (((float) armySize) * _provice.getMercenaryRate());
            if (maxMercenaries * _provice.getMercenaryCost() > Util.coalesce(_provice.getMoney())) {
                maxMercenaries = Util.coalesce(_provice.getMoney()) / _provice.getMercenaryCost();
            }
            if (maxMercenaries < 0) {
                maxMercenaries = 0;
            }
        }
        ((TextView) _view.findViewById(R.id.attack_max_mercenaries)).setText(StringUtil.formatNumberString(maxMercenaries) +" Maximum");

        // Padding-Right Hack: (Bug in Android)
        Integer[] padRightIds = new Integer[] {
            R.id.attack_send_generals_count, R.id.attack_send_soldiers_count, R.id.attack_send_attackers_count,
            R.id.attack_send_elites_count, R.id.attack_send_horses_count, R.id.attack_send_prisoners_count, R.id.attack_send_mercenaries_count
        };
        for (Integer viewId : padRightIds) {
            EditText editText = ((EditText) _view.findViewById(viewId));
            editText.setPadding(editText.getPaddingLeft(), editText.getPaddingTop(), 40, editText.getPaddingBottom());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.attack, container, false);
        _view = rootView;

        _showLoadingScreen();

        View backButton = _view.findViewById(R.id.attack_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _back();
            }
        });

        _drawData();

        _bindInputs();

        _calculateAttackTime();
        _hideLoadingScreen();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;

        _session = Session.getInstance();

        _provice = _session.getProvince();

        _attack = new UtopiaUtil.Attack(_provice);

        if (_targetProvince != null) {
            _targetProvince = _session.loadProvinceFromStore(_targetProvince);
            _attack.setTargetProvince(_targetProvince);
        }

        _attack.setType(UtopiaUtil.Attack.Type.TRADITIONAL_MARCH); // Default to TM
        _attack.setTime(UtopiaUtil.Attack.Time.DEFAULT); // Default Attack-Time
        _army = new UtopiaUtil.Army();
        _attack.setArmy(_army);

        _session.downloadProvinceIntel(_targetProvince, new Session.Callback() {
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
    }

    private void _bindInputs() {
        TextView.OnEditorActionListener onEditorActionListener = new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    textView.clearFocus();
                    AndroidUtil.closeKeyboard(_activity);

                    _updateAttackObject();
                    _calculateOffense();

                    return true;
                }

                return false;
            }
        };

        _view.findViewById(R.id.attack_attack_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditOptionDialog editOptionDialog = new EditOptionDialog();
                editOptionDialog.setActivity(_activity);
                editOptionDialog.setTitle("Attack Type");
                editOptionDialog.setContent("Set attack type:");

                editOptionDialog.setCurrentValue(
                    _attack.getType() != null ?
                        UtopiaUtil.Attack.getDisplayNameForAttackType(_attack.getType())
                    :
                        UtopiaUtil.Attack.getDisplayNameForAttackType(UtopiaUtil.Attack.Type.TRADITIONAL_MARCH)
                );

                final List<UtopiaUtil.Attack.Type> attackTypes = new ArrayList<UtopiaUtil.Attack.Type>(EnumSet.allOf(UtopiaUtil.Attack.Type.class));
                List<String> attackTypeStrings = new ArrayList<String>();
                for (UtopiaUtil.Attack.Type attackType : attackTypes) {
                    attackTypeStrings.add(UtopiaUtil.Attack.getDisplayNameForAttackType(attackType));
                }
                editOptionDialog.setOptions(attackTypeStrings);

                editOptionDialog.setCallback(new EditOptionDialog.Callback() {
                    @Override
                    public void run(String newValue) {
                        UtopiaUtil.Attack.Type selectedAttackType = null;
                        Boolean isValid = false;
                        for (UtopiaUtil.Attack.Type attackType : attackTypes) {
                            if (Util.coalesce(UtopiaUtil.Attack.getDisplayNameForAttackType(attackType)).equals(newValue)) {
                                isValid = true;
                                selectedAttackType = attackType;
                                break;
                            }
                        }

                        if (! isValid) {
                            Dialog.alert("Attack", "Invalid value.", null);
                        }
                        else {
                            _attack.setType(selectedAttackType);
                            _activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    _updateAttackObject();
                                    _calculateOffense();
                                    _drawData();
                                }
                            });
                        }

                        _activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                _calculateAttackTime();
                            }
                        });
                    }
                });
                editOptionDialog.show(_activity.getFragmentManager(), "EDIT_ATTACK_TYPE");
            }
        });

        _view.findViewById(R.id.attack_attack_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditOptionDialog editOptionDialog = new EditOptionDialog();
                editOptionDialog.setActivity(_activity);
                editOptionDialog.setTitle("Attack Time");
                editOptionDialog.setContent("Set attack time:");

                editOptionDialog.setCurrentValue(
                    _attack.getTime() != null ?
                        UtopiaUtil.Attack.getDisplayNameForAttackTime(_attack.getTime())
                        :
                        UtopiaUtil.Attack.getDisplayNameForAttackTime(UtopiaUtil.Attack.Time.DEFAULT)
                );

                final List<UtopiaUtil.Attack.Time> attackTimes = new ArrayList<UtopiaUtil.Attack.Time>(EnumSet.allOf(UtopiaUtil.Attack.Time.class));
                List<String> attackTimeStrings = new ArrayList<String>();
                for (UtopiaUtil.Attack.Time attackTime : attackTimes) {
                    attackTimeStrings.add(UtopiaUtil.Attack.getDisplayNameForAttackTime(attackTime));
                }
                editOptionDialog.setOptions(attackTimeStrings);

                editOptionDialog.setCallback(new EditOptionDialog.Callback() {
                    @Override
                    public void run(String newValue) {
                        UtopiaUtil.Attack.Time selectedAttackTime = null;
                        Boolean isValid = false;
                        for (UtopiaUtil.Attack.Time attackTime : attackTimes) {
                            if (Util.coalesce(UtopiaUtil.Attack.getDisplayNameForAttackTime(attackTime)).equals(newValue)) {
                                isValid = true;
                                selectedAttackTime = attackTime;
                                break;
                            }
                        }

                        if (! isValid) {
                            Dialog.alert("Attack", "Invalid value.", null);
                        }
                        else {
                            _attack.setTime(selectedAttackTime);
                            _activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    _updateAttackObject();
                                    _calculateOffense();
                                    _drawData();
                                }
                            });
                        }

                        _activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                _calculateAttackTime();
                            }
                        });
                    }
                });
                editOptionDialog.show(_activity.getFragmentManager(), "EDIT_ATTACK_TIME");
            }
        });

        ((EditText) _view.findViewById(R.id.attack_send_generals_count)).setOnEditorActionListener(onEditorActionListener);
        ((EditText) _view.findViewById(R.id.attack_send_soldiers_count)).setOnEditorActionListener(onEditorActionListener);
        ((EditText) _view.findViewById(R.id.attack_send_attackers_count)).setOnEditorActionListener(onEditorActionListener);
        ((EditText) _view.findViewById(R.id.attack_send_elites_count)).setOnEditorActionListener(onEditorActionListener);
        ((EditText) _view.findViewById(R.id.attack_send_horses_count)).setOnEditorActionListener(onEditorActionListener);
        ((EditText) _view.findViewById(R.id.attack_send_prisoners_count)).setOnEditorActionListener(onEditorActionListener);
        ((EditText) _view.findViewById(R.id.attack_send_mercenaries_count)).setOnEditorActionListener(onEditorActionListener);

        _view.findViewById(R.id.attack_send_army).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _updateAttackObject();
                Dialog.setActivity(_activity);
                Dialog.confirm("Confirm Attack", "Are you sure you want to attack " + _targetProvince.getName() + " " + _targetProvince.getKingdomIdentifier().toString(), new Runnable() {
                    @Override
                    public void run() {
                        _session.executeAttack(_attack, new Session.AttackCallback() {
                            @Override
                            public void run(Session.AttackResponse response) {
                                if (!response.getWasSuccess()) {
                                    Dialog.setActivity(_activity);
                                    Dialog.alert("Attack", response.getErrorMessage(), null);
                                    return;
                                }

                                AttackBundle attackBundle = response.getAttackBundle();
                                Dialog.setActivity(_activity);
                                Dialog.alert("Attack", attackBundle.get(AttackBundle.Keys.RESULT_TEXT), null);

                                _session.downloadMilitaryCouncil(new Session.Callback() {
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
                            }
                        });
                    }
                }, null);
            }
        });

        _view.findViewById(R.id.attack_province_defense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditValueDialog editDefenseDialog = new EditValueDialog();
                editDefenseDialog.setActivity(_activity);
                editDefenseDialog.setTitle("Override Target Defense");
                editDefenseDialog.setContent("Target Defense:");
                editDefenseDialog.setInProgressValue(null);
                editDefenseDialog.setPositiveButtonText("Set Defense");

                editDefenseDialog.setCurrentValue(StringUtil.formatNumberString(_getTargetDefense()));

                editDefenseDialog.setCallback(new EditValueDialog.Callback() {
                    @Override
                    public void run(String newValue, Boolean isExpedited) {
                        Integer newValueInteger = Util.parseInt(newValue);
                        if (newValueInteger > 0) {
                            _defenseOverride = newValueInteger;
                        }
                        else {
                            _defenseOverride = null;
                        }

                        _drawData();
                    }
                });
                editDefenseDialog.show(_activity.getFragmentManager(), "EDITABLE_VALUE_FIELD_CLICKED");
            }
        });
    }

    private Integer _getIntegerForField(int id) {
        String text = ((EditText) _view.findViewById(id)).getText().toString();
        if (text.length() == 0) {
            return 0;
        }

        return Util.parseInt(text);
    }

    private void _updateAttackObject() {
        _army.setGenerals(_getIntegerForField(R.id.attack_send_generals_count));
        _army.setSoldiers(_getIntegerForField(R.id.attack_send_soldiers_count));
        _army.setOffensiveUnits(_getIntegerForField(R.id.attack_send_attackers_count));
        _army.setElites(_getIntegerForField(R.id.attack_send_elites_count));
        _army.setHorses(_getIntegerForField(R.id.attack_send_horses_count));
        _army.setPrisoners(_getIntegerForField(R.id.attack_send_prisoners_count));
        _army.setMercenaries(_getIntegerForField(R.id.attack_send_mercenaries_count));
    }

    private void _calculateOffense() {
        _session.calculateOffense(_attack, new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                if (! response.getWasSuccess()) {
                    System.out.println(response.getErrorMessage());
                    return;
                }

                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _drawData();
                    }
                });
            }
        });
    }

    private void _back() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        KingdomFragment kingdomFragment = new KingdomFragment();
        kingdomFragment.setKingdomIdentifier(_targetProvince.getKingdomIdentifier());
        kingdomFragment.setShouldUseCachedKingdomData(true);
        KingdomFragment fragment = kingdomFragment;

        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
