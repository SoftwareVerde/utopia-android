package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;

public class ExplorationFragment extends Fragment {
    public static final String THRONE_CALLBACK_IDENTIFIER = "ExplorationFragment_ThroneUpdateCallbackIdentifier";

    private Activity _activity;
    private View _view;

    private Session _session;

    protected void _showLoadingScreen() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.showLoadingScreen(_activity);
            }
        });
    }
    protected void _hideLoadingScreen() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.hideLoadingScreen();
            }
        });
    }

    public ExplorationFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.exploration, container, false);
        _view = rootView;

        // Padding-Right Hack: (Bug in Android)
        Integer[] padRightIds = new Integer[] {
                R.id.explore_acres
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

        _session.downloadExplorationCosts(new Session.Callback() {
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

        final EditText acresInput = ((EditText) _view.findViewById(R.id.explore_acres));
        acresInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Province province = _session.getProvince();
                if (province == null) {
                    return;
                }

                Integer acres = Util.parseInt(acresInput.getText().toString());

                Integer totalGoldCost = acres * province.getExplorationGoldCost();
                Integer totalSoldierCost = acres * province.getExplorationSoldiersCost();

                ((TextView) _view.findViewById(R.id.explore_total_gold_cost)).setText(Util.formatNumberString(totalGoldCost));
                ((TextView) _view.findViewById(R.id.explore_total_soldier_cost)).setText(Util.formatNumberString(totalSoldierCost));
            }
        });

        _view.findViewById(R.id.explore_send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _showLoadingScreen();

                Integer acres = Util.parseInt(acresInput.getText().toString());
                _session.exploreAcres(acres, new Session.Callback() {
                    @Override
                    public void run(final Session.SessionResponse response) {
                        final TextView responseView = (TextView) _view.findViewById(R.id.exploration_results);
                        if (!response.getWasSuccess()) {
                            _activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    responseView.setText(response.getErrorMessage());
                                    _hideLoadingScreen();
                                    return;
                                }
                            });
                        }

                        _session.downloadExplorationCosts(new Session.Callback() {
                            @Override
                            public void run(Session.SessionResponse response) {
                                _session.downloadThrone(new Session.Callback() {
                                    @Override
                                    public void run(Session.SessionResponse response) {
                                        _activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                responseView.setText("Expedition sent!");
                                                _hideLoadingScreen();
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

        return rootView;
    }

    private void _drawData() {
        if (_view == null) {
            return;
        }

        Province province = _session.getProvince();

        ((TextView) _view.findViewById(R.id.explore_current_acres)).setText(Util.formatNumberString(province.getAcres()));
        ((TextView) _view.findViewById(R.id.explore_acres_in_progress)).setText("+"+ Util.formatNumberString(province.getExplorationAcresInProgress()));
        ((TextView) _view.findViewById(R.id.explore_current_money)).setText(Util.formatNumberString(province.getMoney()));
        ((TextView) _view.findViewById(R.id.explore_current_soldiers)).setText(Util.formatNumberString(province.getSoldiers()));

        ((TextView) _view.findViewById(R.id.explore_gold_cost)).setText(Util.formatNumberString(province.getExplorationGoldCost()));
        ((TextView) _view.findViewById(R.id.explore_soldier_cost)).setText(Util.formatNumberString(province.getExplorationSoldiersCost()));
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