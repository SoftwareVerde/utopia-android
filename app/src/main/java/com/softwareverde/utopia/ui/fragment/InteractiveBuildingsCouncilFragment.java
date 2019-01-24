package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softwareverde.util.Dialog;
import com.softwareverde.util.Util;
import com.softwareverde.utopia.Building;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.ui.dialog.BuildBuildingDialog;

public class InteractiveBuildingsCouncilFragment extends BuildingsCouncilFragment {
    public static final String BUILDINGS_COUNCIL_CALLBACK_IDENTIFIER = "BuildingCouncilFragmentBuildingsCouncilUpdateCallbackIdentifier";
    public static final String BUILDINGS_COUNCIL_THRONE_CALLBACK_IDENTIFIER = "BuildingCouncilFragmenThroneUpdateCallbackIdentifier";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        _province = _session.getProvince();

        _session.addBuildingsCouncilCallback(BUILDINGS_COUNCIL_CALLBACK_IDENTIFIER, new Runnable() {
            public void run() {
                _activity.runOnUiThread(new Runnable() {
                    public void run() {
                        _drawData();
                    }
                });
            }
        });
        _session.addThroneCallback(BUILDINGS_COUNCIL_THRONE_CALLBACK_IDENTIFIER, new Runnable() {
            public void run() {
                _activity.runOnUiThread(new Runnable() {
                    public void run() {
                        _drawData();
                    }
                });
            }
        });

        _showLoadingScreen();
        _session.downloadBuildCosts(new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                // Note: _bindInputs requires BuildCosts be downloaded in order to calculate buildCosts...

                _session.downloadBuildingsCouncil(new Session.Callback() {
                    @Override
                    public void run(Session.SessionResponse response) {
                        _activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                _bindInputs();
                                _drawData();
                                _hideLoadingScreen();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    protected void _drawData() {
        super._drawData();

        ((TextView) _view.findViewById(R.id.building_province_name)).setText(_province.getName());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        _view.findViewById(R.id.building_province_name).setVisibility(View.VISIBLE);

        View backButton = _view.findViewById(R.id.building_back_button);
        backButton.setVisibility(View.GONE);
        backButton.setOnClickListener(null);

        return view;
    }

    @Override
    public void onDetach() {
        _session.removeBuildingsCouncilCallback(BUILDINGS_COUNCIL_CALLBACK_IDENTIFIER);
        _session.removeThroneCallback(BUILDINGS_COUNCIL_THRONE_CALLBACK_IDENTIFIER);

        super.onDetach();
    }

    protected void _bindInput(final Building.Type type, Integer viewId, Integer viewId2) {
        final String buildingName = Building.getBuildingName(type);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer buildingCount = 0;

                Building building = _province.getBuilding(type);
                if (building != null) {
                     buildingCount = Util.coalesce(building.getCount()) + _sumIntegerArray(_province.getBuildingInProgress(type));
                }

                final BuildBuildingDialog dialog = new BuildBuildingDialog();
                dialog.setActivity(_activity);
                dialog.setBuildingName(buildingName);
                dialog.setBuildingCount(buildingCount);
                dialog.setTotalLand(_province.getAcres());
                dialog.setBuildCost(_province.getBuildCost());
                dialog.setRazeCost(_province.getRazeCost());
                dialog.setBuildTime(_province.getBuildTime());

                dialog.setCallback(new BuildBuildingDialog.Callback() {
                    @Override
                    public void run(final String setValue, final Boolean isConstructionExpedited, final Boolean shouldUseBuildingCredits) {
                        final Integer quantity = Util.parseInt(setValue);
                        final Runnable onConfirmRunnable = new Runnable() {
                            @Override
                            public void run() {
                                _showLoadingScreen();
                                _session.buildBuilding(type, quantity, isConstructionExpedited, shouldUseBuildingCredits, new Session.Callback() {
                                    @Override
                                    public void run(Session.SessionResponse response) {
                                        _session.downloadBuildingsCouncil(new Session.Callback() {
                                            @Override
                                            public void run(Session.SessionResponse response) {
                                                // Building Costs need to be updated in order to update Building-Credits spent.
                                                _session.downloadBuildCosts(new Session.Callback() {
                                                    @Override
                                                    public void run(final Session.SessionResponse response) {
                                                        _session.downloadThrone(new Session.Callback() {
                                                            @Override
                                                            public void run(Session.SessionResponse response) {
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
                        };

                        dialog.dismiss();
                        if (quantity != 0) {
                            if (quantity > 0) {
                                onConfirmRunnable.run();
                            }
                            else {
                                Dialog.confirm("Raze Buildings", "Are you sure you want to raze "+ Math.abs(quantity) +" "+ buildingName +"?", onConfirmRunnable, null);
                            }
                        }
                    }
                });

                dialog.show(_activity.getFragmentManager(), "BUILD_BUILDING_CLICKED");
            }
        };

        View bindView = _activity.findViewById(viewId);
        if (bindView != null) {
            bindView.setOnClickListener(listener);
        }

        if (viewId2 != null) {
            View bindView2 = _activity.findViewById(viewId2);
            if (bindView2 != null) {
                bindView2.setOnClickListener(listener);
            }
        }
    }
    protected void _bindInputs() {
        _bindInput(Building.Type.HOMES, R.id.building_homes_count, R.id.building_homes_percent);
        _bindInput(Building.Type.FARMS, R.id.building_farms_count, R.id.building_farms_percent);
        _bindInput(Building.Type.BANKS, R.id.building_banks_count, R.id.building_banks_percent);
        _bindInput(Building.Type.DUNGEONS, R.id.building_dungeons_count, R.id.building_dungeons_percent);
        _bindInput(Building.Type.ARMORIES,R.id.building_armories_count, R.id.building_armories_percent);
        _bindInput(Building.Type.MILLS, R.id.building_mills_count, R.id.building_mills_percent);
        _bindInput(Building.Type.UNIVERSITIES, R.id.building_universities_count, R.id.building_universities_percent);
        _bindInput(Building.Type.LABORATORIES, R.id.building_laboratories_count, R.id.building_laboratories_percent);
        _bindInput(Building.Type.TRAINING_GROUNDS, R.id.building_training_grounds_count, R.id.building_training_grounds_percent);
        _bindInput(Building.Type.BARRACKS, R.id.building_barracks_count, R.id.building_barracks_percent);
        _bindInput(Building.Type.STABLES, R.id.building_stables_count, R.id.building_stables_percent);
        _bindInput(Building.Type.FORTS, R.id.building_forts_count, R.id.building_forts_percent);
        _bindInput(Building.Type.GUARD_STATIONS, R.id.building_guard_stations_count, R.id.building_guard_stations_percent);
        _bindInput(Building.Type.WATCHTOWERS, R.id.building_watch_towers_count, R.id.building_watch_towers_percent);
        _bindInput(Building.Type.HOSPITALS, R.id.building_hospitals_count, R.id.building_hospitals_percent);
        _bindInput(Building.Type.GUILDS, R.id.building_guilds_count, R.id.building_guilds_percent);
        _bindInput(Building.Type.THIEVES_DENS, R.id.building_thieves_dens_count, R.id.building_thieves_dens_percent);
        _bindInput(Building.Type.TOWERS, R.id.building_towers_count, R.id.building_towers_percent);
    }
}
