package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.Building;
import com.softwareverde.utopia.DraftRate;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.ThieveryOperation;
import com.softwareverde.utopia.TrainArmyData;
import com.softwareverde.utopia.bundle.ThieveryOperationBundle;
import com.softwareverde.utopia.ui.adapter.ThieveryOperationAdapter;
import com.softwareverde.utopia.ui.dialog.EditOptionDialog;

import java.util.ArrayList;
import java.util.List;

public class ThieveryOpsFragment extends Fragment {
    public static final String OP_LIST_CALLBACK_IDENTIFIER = "ThieveryOpsFragment_OpListUpdateCallbackIdentifier";
    public static final String THRONE_CALLBACK_IDENTIFIER = "ThieveryOpsFragment_ThroneCallbackIdentifier";

    private Activity _activity;
    private Session _session;
    private View _view;
    private Province _targetProvince;
    private Building.Type _targetBuilding;

    private ThieveryOperationAdapter _adapter;

    public ThieveryOpsFragment() { }
    public void setTargetProvince(Province province) {
        _setTargetProvince(province);
    }
    private void _setTargetProvince(Province province) {
        _targetProvince = province;

        if (_session != null && _targetProvince != null) {
            _targetProvince = _session.loadProvinceFromStore(_targetProvince);

            _session.downloadAvailableThieveryOperations(_targetProvince.getKingdomIdentifier(), null);
        }
    }
    public Province getTargetProvince() {
        return _targetProvince;
    }

    private void _showLoadingScreen() {
        AndroidUtil.showLoadingScreen(_activity);
    }
    private void _hideLoadingScreen() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.hideLoadingScreen();
            }
        });
    }

    private <T extends Object> String _asOptionalString(T object) {
        if (object == null) {
            return "?";
        }
        return object.toString();
    }

    private void _drawData() {
        Province province = _session.getProvince();

        TextView currentThieveCount = (TextView) _view.findViewById(R.id.thievery_operation_current_thieves);
        TextView sendThievesPercent = (TextView) _view.findViewById(R.id.thievery_operation_send_percent);
        EditText sendThievesInput = (EditText) _view.findViewById(R.id.thievery_operation_send_thieves_count);
        TextView currentTpa = ((TextView) _view.findViewById(R.id.thievery_operation_current_tpa));
        TextView currentStealth = ((TextView) _view.findViewById(R.id.thievery_operation_current_stealth));

        currentStealth.setText(province.getStealth() + "%");
        currentTpa.setText(Util.formatPercentString((float) province.getThieves() / (float) province.getAcres()));
        currentThieveCount.setText(Util.formatNumberString(province.getThieves()));

        float sendPercent = Util.parseInt(sendThievesInput.getText().toString()) / ((float) province.getThieves());
        sendThievesPercent.setText(Util.formatPercentString(Float.valueOf(sendPercent * 100.0f)) + "%");

        ((TextView) _view.findViewById(R.id.thievery_op_target_name)).setText(_asOptionalString(_targetProvince.getName()));

        Integer targetMoney = _targetProvince.getMoney();
        if (targetMoney == null) {
            ((TextView) _view.findViewById(R.id.thievery_op_target_money)).setText("?");
        }
        else {
            ((TextView) _view.findViewById(R.id.thievery_op_target_money)).setText(Util.formatNumberString(targetMoney));
        }

        Integer targetRunes = _targetProvince.getRunes();
        if (targetRunes == null) {
            ((TextView) _view.findViewById(R.id.thievery_op_target_runes)).setText("?");
        }
        else {
            ((TextView) _view.findViewById(R.id.thievery_op_target_runes)).setText(Util.formatNumberString(targetRunes));
        }

        Integer targetFood = _targetProvince.getFood();
        if (targetFood == null) {
            ((TextView) _view.findViewById(R.id.thievery_op_target_food)).setText("?");
        }
        else {
            ((TextView) _view.findViewById(R.id.thievery_op_target_food)).setText(Util.formatNumberString(targetFood));
        }

        Integer targetPeasants = _targetProvince.getPeasants();
        if (targetPeasants == null) {
            ((TextView) _view.findViewById(R.id.thievery_op_target_peasants)).setText("?");
        }
        else {
            ((TextView) _view.findViewById(R.id.thievery_op_target_peasants)).setText(Util.formatNumberString(targetPeasants));
        }

        Integer targetThieves = _targetProvince.getThieves();
        if (targetThieves == null) {
            ((TextView) _view.findViewById(R.id.thievery_op_target_thieves)).setText("?");
        }
        else {
            ((TextView) _view.findViewById(R.id.thievery_op_target_thieves)).setText(Util.formatNumberString(targetThieves));
        }

        Integer targetMilitaryPopulation = _targetProvince.getMilitaryPopulation();
        if (targetMilitaryPopulation == null) {
            ((TextView) _view.findViewById(R.id.thievery_op_target_army)).setText("?");
        }
        else {
            ((TextView) _view.findViewById(R.id.thievery_op_target_army)).setText(Util.formatNumberString(targetMilitaryPopulation));
        }

        _adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.thievery_operations, container, false);
        _view = rootView;

        _view.findViewById(R.id.thievery_operation_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _back();
            }
        });

        _view.findViewById(R.id.thievery_tab_province).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _showTargetProvince();
                    }
                });
            }
        });
        _view.findViewById(R.id.thievery_tab_magic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _showTargetMagic();
                    }
                });
            }
        });
        _view.findViewById(R.id.thievery_tab_survey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _showTargetSurvey();
                    }
                });
            }
        });

        final ListView listView = (ListView) _view.findViewById(R.id.thievery_option_list);
        final TextView currentThieveCount = (TextView) _view.findViewById(R.id.thievery_operation_current_thieves);
        final TextView sendThievesPercent = (TextView) _view.findViewById(R.id.thievery_operation_send_percent);
        final EditText sendThievesInput = (EditText) _view.findViewById(R.id.thievery_operation_send_thieves_count);
        final TextView resultArea = ((TextView) _view.findViewById(R.id.thievery_operation_result));

        listView.setAdapter(_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ThieveryOperation thieveryOperation = _adapter.getItem(i);

                _session.executeThieveryOperation(thieveryOperation, Util.parseInt(sendThievesInput.getText().toString()), _targetProvince, _targetBuilding, new Session.ThieveryOperationCallback() {
                    @Override
                    public void run(final Session.ThieveryOperationResponse response) {
                        _activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (! response.getWasSuccess()) {
                                    resultArea.setText(response.getErrorMessage());
                                }
                                else {
                                    ThieveryOperationBundle thieveryOperationBundle = response.getThieveryOperationBundle();
                                    resultArea.setText(thieveryOperationBundle.get(ThieveryOperationBundle.Keys.RESULT_TEXT));

                                    _activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            _drawData();
                                        }
                                    });
                                }
                            }
                        });

                        _session.downloadThrone(new Session.Callback() {
                            @Override
                            public void run(Session.SessionResponse response) {
                                if (response.getWasSuccess()) {
                                    final Province userProvince = _session.getProvince();

                                    Integer desiredThievesAmount = Util.parseInt(sendThievesInput.getText().toString());
                                    if (desiredThievesAmount > userProvince.getThieves()) {
                                        _activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                sendThievesInput.setText(userProvince.getThieves().toString());
                                                _drawData();
                                            }
                                        });
                                    }
                                    else {
                                        _activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                _drawData();
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                });
            }
        });

        sendThievesInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Province userProvince = _session.getProvince();

                        if (Util.parseInt(sendThievesInput.getText().toString()) > userProvince.getThieves()) {
                            sendThievesInput.setText(userProvince.getThieves().toString());
                            sendThievesInput.setSelection(sendThievesInput.getText().toString().length());
                        }

                        float sendPercent = Util.parseInt(sendThievesInput.getText().toString()) / ((float) userProvince.getThieves());
                        sendThievesPercent.setText(Util.formatPercentString(Float.valueOf(sendPercent * 100.0f)) + "%");
                    }
                });
                return false;
            }
        });

        _showLoadingScreen();

        Province province = _session.getProvince();
        sendThievesInput.setText(Integer.valueOf((int) Math.ceil(province.getThieves() * 0.10d)).toString());
        currentThieveCount.setText(province.getThieves().toString());

        if (_session.thieveryOperationsAreDownloaded(_targetProvince.getKingdomIdentifier())) {
            // Draw the cached list...
            _drawData();
        }

        _session.downloadAvailableThieveryOperations(_targetProvince.getKingdomIdentifier(), null);

        _drawData();
        _hideLoadingScreen();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;

        _session = Session.getInstance();

        if (_targetProvince != null) {
            _session.downloadAvailableThieveryOperations(_targetProvince.getKingdomIdentifier(), null);
        }

        _adapter = new ThieveryOperationAdapter(_activity);

        _adapter.setOnTargetBuildingClicked(new Runnable() {
            @Override
            public void run() {
                final EditOptionDialog editOptionDialog = new EditOptionDialog();
                editOptionDialog.setActivity(_activity);
                editOptionDialog.setTitle("Target Building");
                editOptionDialog.setContent("Set target building:");

                final String currentValue;
                {
                    final Building.Type targetBuilding = _adapter.getTargetBuilding();
                    if (targetBuilding != null) {
                        currentValue = Building.getBuildingName(targetBuilding);
                    }
                    else {
                        currentValue = "Random";
                    }
                }
                editOptionDialog.setCurrentValue(currentValue);

                final List<String> buildingNames = new ArrayList<String>();
                final List<Building.Type> availableBuildingTypes = Building.getBuildingTypes();
                availableBuildingTypes.remove(Building.Type.BARREN);
                for (final Building.Type buildingType : availableBuildingTypes) {
                    buildingNames.add(Building.getBuildingName(buildingType));
                }
                editOptionDialog.setOptions(buildingNames);

                editOptionDialog.setCallback(new EditOptionDialog.Callback() {
                    @Override
                    public void run(final String newValue) {
                        Building.Type selectedBuildingType = null;
                        Boolean isValid = false;
                        for (Building.Type buildingType : availableBuildingTypes) {
                            if (Building.getBuildingName(buildingType).equals(newValue)) {
                                isValid = true;
                                selectedBuildingType = buildingType;
                                break;
                            }
                        }

                        if (! isValid) {
                            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_activity, AlertDialog.THEME_HOLO_DARK);
                            dialogBuilder.setTitle("Invalid Value");
                            dialogBuilder.setMessage("Invalid value.");
                            dialogBuilder.setPositiveButton("Ok", null);
                            dialogBuilder.create().show();
                        }
                        else {
                            _targetBuilding = selectedBuildingType;
                            _adapter.setTargetBuilding(_targetBuilding);
                        }
                    }
                });
                editOptionDialog.show(_activity.getFragmentManager(), "EDITABLE_VALUE_FIELD_CLICKED");
            }
        });

        _session.addThieveryOperationsCallback(OP_LIST_CALLBACK_IDENTIFIER, new Runnable() {
            public void run() {
                _activity.runOnUiThread(new Runnable() {
                    public void run() {
                        _adapter.clear();
                        _adapter.addAll(_session.getAvailableThieveryOperations(_targetProvince.getKingdomIdentifier()));
                        _adapter.notifyDataSetChanged();

                        _drawData();
                    }
                });
            }
        });
        _session.addThroneCallback(THRONE_CALLBACK_IDENTIFIER, new Runnable() {
            @Override
            public void run() {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _drawData();
                    }
                });
            }
        });

        if (_targetProvince != null) {
            _targetProvince = _session.loadProvinceFromStore(_targetProvince);
        }
    }

    @Override
    public void onDetach() {
        _session.removeThieveryOperationsCallback(OP_LIST_CALLBACK_IDENTIFIER);
        _session.removeThroneCallback(THRONE_CALLBACK_IDENTIFIER);

        super.onDetach();
    }

    private void _back() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        KingdomFragment kingdomFragment = new KingdomFragment();
        kingdomFragment.setKingdomIdentifier(_targetProvince.getKingdomIdentifier());
        kingdomFragment.setShouldUseCachedKingdomData(true);

        transaction.replace(R.id.container, kingdomFragment);
        transaction.commit();
    }

    private void _showTargetProvince() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        TabbedThroneFragment throneFragment = new TabbedThroneFragment();
        throneFragment.setProvince(_targetProvince);

        transaction.replace(R.id.container, throneFragment);
        transaction.commit();
    }
    private void _showTargetMagic() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        OffensiveSpellListFragment offensiveSpellListFragment = new OffensiveSpellListFragment();
        offensiveSpellListFragment.setTargetProvince(_targetProvince);

        transaction.replace(R.id.container, offensiveSpellListFragment);
        transaction.commit();
    }
    private void _showTargetSurvey() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        TabbedBuildingsCouncilFragment tabbedBuildingsCouncilFragment = new TabbedBuildingsCouncilFragment();
        tabbedBuildingsCouncilFragment.setProvince(_targetProvince);

        transaction.replace(R.id.container, tabbedBuildingsCouncilFragment);
        transaction.commit();
    }
}