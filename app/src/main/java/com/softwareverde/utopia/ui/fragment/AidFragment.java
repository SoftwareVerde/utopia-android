package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.softwareverde.util.StringUtil;
import com.softwareverde.utopia.Util;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.UtopiaUtil;

public class AidFragment extends Fragment {
    protected Activity _activity;
    protected View _view;
    protected Session _session;
    protected Province _targetProvince;

    public AidFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public void onDestroy() { super.onDestroy(); }

    protected void _showLoadingScreen() {
        AndroidUtil.showLoadingScreen(_activity);
    }
    protected void _hideLoadingScreen() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.hideLoadingScreen();
            }
        });
    }

    protected void _drawData() {
        if (_view == null || _targetProvince == null) {
            return;
        }

        ((TextView) _view.findViewById(R.id.aid_province_name)).setText(_targetProvince.getName());

        Province province = _session.getProvince();
        ((TextView) _view.findViewById(R.id.aid_current_food)).setText(StringUtil.formatNumberString(province.getFood()));
        ((TextView) _view.findViewById(R.id.aid_current_gold)).setText(StringUtil.formatNumberString(province.getMoney()));
        ((TextView) _view.findViewById(R.id.aid_current_runes)).setText(StringUtil.formatNumberString(province.getRunes()));
        ((TextView) _view.findViewById(R.id.aid_current_soldiers)).setText(StringUtil.formatNumberString(province.getSoldiers()));

        /*
            Boolean allowsIncomingAid = province.allowsIncomingAid();
            Boolean showAidBlocked = false;
            if (allowsIncomingAid == null || allowsIncomingAid == false) {
                showAidBlocked = true;
            }
            _view.findViewById(R.id.aid_currently_blocking).setVisibility(showAidBlocked ? View.VISIBLE : View.GONE);
        */
    }

    protected Integer _calculateAidShipmentValue() {
        final Float foodWorth = 0.05F;
        final Float goldWorth = 1.0F;
        final Float soldiersWorth = 100.0F;
        final Float runesWorth = 3.0F;

        Float shipmentValue = 0F;
        shipmentValue += Util.parseInt(((EditText) _view.findViewById(R.id.aid_send_food_amount)).getText().toString()) * foodWorth;
        shipmentValue += Util.parseInt(((EditText) _view.findViewById(R.id.aid_send_gold_amount)).getText().toString()) * goldWorth;
        shipmentValue += Util.parseInt(((EditText) _view.findViewById(R.id.aid_send_soldiers_amount)).getText().toString()) * soldiersWorth;
        shipmentValue += Util.parseInt(((EditText) _view.findViewById(R.id.aid_send_runes_amount)).getText().toString()) * runesWorth;

        return shipmentValue.intValue();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.send_aid, container, false);
        _view = rootView;

        // _view.findViewById(R.id.aid_currently_blocking).setVisibility(View.GONE); // Initially hide the blocked-aid icon.

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ((TextView) _view.findViewById(R.id.aid_trade_balance_change)).setText(StringUtil.formatNumberString(_calculateAidShipmentValue()));
            }
        };
        ((EditText) _view.findViewById(R.id.aid_send_food_amount)).addTextChangedListener(textWatcher);
        ((EditText) _view.findViewById(R.id.aid_send_gold_amount)).addTextChangedListener(textWatcher);
        ((EditText) _view.findViewById(R.id.aid_send_soldiers_amount)).addTextChangedListener(textWatcher);
        ((EditText) _view.findViewById(R.id.aid_send_runes_amount)).addTextChangedListener(textWatcher);

        _view.findViewById(R.id.aid_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _showTargetProvince();
            }
        });

        _view.findViewById(R.id.aid_send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtopiaUtil.AidShipment aidShipment = new UtopiaUtil.AidShipment();

                aidShipment.food = Util.parseInt(((EditText) _view.findViewById(R.id.aid_send_food_amount)).getText().toString());
                aidShipment.gold = Util.parseInt(((EditText) _view.findViewById(R.id.aid_send_gold_amount)).getText().toString());
                aidShipment.soldiers = Util.parseInt(((EditText) _view.findViewById(R.id.aid_send_soldiers_amount)).getText().toString());
                aidShipment.runes = Util.parseInt(((EditText) _view.findViewById(R.id.aid_send_runes_amount)).getText().toString());

                _session.sendAid(aidShipment, _targetProvince, new Session.Callback() {
                    @Override
                    public void run(final Session.SessionResponse response) {
                        final TextView resultView = (TextView) _view.findViewById(R.id.aid_result_text);

                        if (! response.getWasSuccess()) {
                            _activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    resultView.setText(response.getErrorMessage());
                                }
                            });

                            return;
                        }

                        _activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultView.setText("Aid shipment sent!");
                            }
                        });

                        _session.downloadThrone(new Session.Callback() {
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
        });

        _drawData();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        _activity = activity;
        _session = Session.getInstance();

        _drawData();

        _session.downloadTradeSettings(new Session.Callback() {
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

    public void setProvince(Province province) {
        _targetProvince = province;
        _drawData();
    }

    private void _showTargetProvince() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        TabbedThroneFragment throneFragment = new TabbedThroneFragment();
        throneFragment.setProvince(_targetProvince);

        transaction.replace(R.id.container, throneFragment);
        transaction.commit();
    }
}