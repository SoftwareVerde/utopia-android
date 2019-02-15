package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softwareverde.util.StringUtil;
import com.softwareverde.utopia.Util;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.Building;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.ui.widget.InProgressWidget;

import java.util.HashMap;
import java.util.Map;

public class BuildingsCouncilFragment extends Fragment {
    protected Activity _activity;
    protected Session _session;
    protected View _view;
    protected Province _province;
    protected Map<Building.Type, InProgressWidget> _inProgressWidgets = new HashMap<Building.Type, InProgressWidget>();

    public BuildingsCouncilFragment() { }

    public void setProvince(Province province) {
        _setProvince(province);
    }
    private void _setProvince(Province province) {
        _province = province;

        if (_session != null && _province != null) {
            _province = _session.loadProvinceFromStore(_province);
        }
        _drawData();
    }

    protected Integer _sumIntegerArray(Integer[] integerArray) {
        Integer sum = 0;
        for (Integer i=0; i<integerArray.length; i++) {
            sum += integerArray[i];
        }
        return sum;
    }

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

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.buildings_council, container, false);
        _view = rootView;

        _view.findViewById(R.id.building_province_name).setVisibility(View.GONE);

        _inProgressWidgets.put(Building.Type.BARREN, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_barren_progress_container)));
        _inProgressWidgets.put(Building.Type.HOMES, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_homes_progress_container)));
        _inProgressWidgets.put(Building.Type.FARMS, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_farms_progress_container)));
        _inProgressWidgets.put(Building.Type.BANKS, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_banks_progress_container)));
        _inProgressWidgets.put(Building.Type.DUNGEONS, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_dungeons_progress_container)));
        _inProgressWidgets.put(Building.Type.ARMORIES, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_armories_progress_container)));
        _inProgressWidgets.put(Building.Type.MILLS, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_mills_progress_container)));
        _inProgressWidgets.put(Building.Type.UNIVERSITIES, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_universities_progress_container)));
        _inProgressWidgets.put(Building.Type.LABORATORIES, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_laboratories_progress_container)));
        _inProgressWidgets.put(Building.Type.TRAINING_GROUNDS, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_training_grounds_progress_container)));
        _inProgressWidgets.put(Building.Type.BARRACKS, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_barracks_progress_container)));
        _inProgressWidgets.put(Building.Type.STABLES, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_stables_progress_container)));
        _inProgressWidgets.put(Building.Type.FORTS, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_forts_progress_container)));
        _inProgressWidgets.put(Building.Type.GUARD_STATIONS, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_guard_stations_progress_container)));
        _inProgressWidgets.put(Building.Type.WATCHTOWERS, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_watch_towers_progress_container)));
        _inProgressWidgets.put(Building.Type.HOSPITALS, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_hospitals_progress_container)));
        _inProgressWidgets.put(Building.Type.GUILDS, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_guilds_progress_container)));
        _inProgressWidgets.put(Building.Type.THIEVES_DENS, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_thieves_dens_progress_container)));
        _inProgressWidgets.put(Building.Type.TOWERS, InProgressWidget.newInstance(inflater, (ViewGroup) _view.findViewById(R.id.building_towers_progress_container)));

        _drawData();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
        _session = Session.getInstance();
    }

    protected void _drawInProgress(Integer buildingCount, Integer totalLand, Integer buildingCountId, Integer buildingPercentId) {
        String buildingCountString = "";
        if (buildingCount > 0) {
            buildingCountString = "+"+ buildingCount.toString();
        }
        ((TextView) _view.findViewById(buildingCountId)).setText(buildingCountString);

        String buildingPercentString = "";
        if (buildingCount > 0) {
            buildingPercentString = String.format("+%.1f%%", ((float) buildingCount / totalLand * 100.0f));
        }
        ((TextView) _view.findViewById(buildingPercentId)).setText(buildingPercentString);
    }

    protected void _drawBuilding(final Building.Type buildingType, final Integer buildingCountViewId, final Integer buildingPercentViewId, final Integer buildingProgressCountViewId, final Integer buildingProgressPercentViewId) {
        final Integer totalLand = _province.getAcres();

        final Integer[] countInProgress = _province.getBuildingInProgress(buildingType);

        final Building building = _province.getBuilding(buildingType);
        if (building == null) { return; }
        ((TextView) _view.findViewById(buildingCountViewId)).setText(StringUtil.formatNumberString(building.getCount()));
        ((TextView) _view.findViewById(buildingPercentViewId)).setText(String.format("%.1f%%", (building.getPercent() * 100.0f)));

        if (buildingProgressCountViewId != null && buildingProgressPercentViewId != null) {
            _drawInProgress(_sumIntegerArray(countInProgress), totalLand, buildingProgressCountViewId, buildingProgressPercentViewId);
        }

        _inProgressWidgets.get(buildingType).setInProgress(countInProgress);
    }

    protected void _drawData() {
        if (_view == null) { return; }
        if (_province == null) { return; }

        Integer totalLand = _province.getAcres();
        ((TextView) _view.findViewById(R.id.building_total_land)).setText(StringUtil.formatNumberString(totalLand));

        Float buildingEfficiency = _province.getBuildingEfficiency();
        if (buildingEfficiency != null) {
            ((TextView) _view.findViewById(R.id.building_building_efficiency)).setText(String.format("%.1f%%", buildingEfficiency * 100.0f));
        }

        ((TextView) _view.findViewById(R.id.building_current_money)).setText(StringUtil.formatNumberString(_province.getMoney()));
        ((TextView) _view.findViewById(R.id.building_building_credits)).setText(StringUtil.formatNumberString(_province.getBuildCredits()));

        ((TextView) _view.findViewById(R.id.building_build_cost)).setText(StringUtil.formatNumberString(_province.getBuildCost()));
        ((TextView) _view.findViewById(R.id.building_raze_cost)).setText(StringUtil.formatNumberString(_province.getRazeCost()));

        _drawBuilding(Building.Type.BARREN, R.id.building_barren_count, R.id.building_barren_percent, null, null);
        _drawBuilding(Building.Type.HOMES, R.id.building_homes_count, R.id.building_homes_percent, R.id.building_homes_progress_count, R.id.building_homes_progress_percent);
        _drawBuilding(Building.Type.FARMS, R.id.building_farms_count, R.id.building_farms_percent, R.id.building_farms_progress_count, R.id.building_farms_progress_percent);
        _drawBuilding(Building.Type.BANKS, R.id.building_banks_count, R.id.building_banks_percent, R.id.building_banks_progress_count, R.id.building_banks_progress_percent);
        _drawBuilding(Building.Type.DUNGEONS, R.id.building_dungeons_count, R.id.building_dungeons_percent, R.id.building_dungeons_progress_count, R.id.building_dungeons_progress_percent);
        _drawBuilding(Building.Type.ARMORIES, R.id.building_armories_count, R.id.building_armories_percent, R.id.building_armories_progress_count, R.id.building_armories_progress_percent);
        _drawBuilding(Building.Type.MILLS, R.id.building_mills_count, R.id.building_mills_percent, R.id.building_mills_progress_count, R.id.building_mills_progress_percent);
        _drawBuilding(Building.Type.UNIVERSITIES, R.id.building_universities_count, R.id.building_universities_percent, R.id.building_universities_progress_count, R.id.building_universities_progress_percent);
        _drawBuilding(Building.Type.LABORATORIES, R.id.building_laboratories_count, R.id.building_laboratories_percent, R.id.building_laboratories_progress_count, R.id.building_laboratories_progress_percent);
        _drawBuilding(Building.Type.TRAINING_GROUNDS, R.id.building_training_grounds_count, R.id.building_training_grounds_percent, R.id.building_training_grounds_progress_count, R.id.building_training_grounds_progress_percent);
        _drawBuilding(Building.Type.BARRACKS, R.id.building_barracks_count, R.id.building_barracks_percent, R.id.building_barracks_progress_count, R.id.building_barracks_progress_percent);
        _drawBuilding(Building.Type.STABLES, R.id.building_stables_count, R.id.building_stables_percent, R.id.building_stables_progress_count, R.id.building_stables_progress_percent);
        _drawBuilding(Building.Type.FORTS, R.id.building_forts_count, R.id.building_forts_percent, R.id.building_forts_progress_count, R.id.building_forts_progress_percent);
        _drawBuilding(Building.Type.GUARD_STATIONS, R.id.building_guard_stations_count, R.id.building_guard_stations_percent, R.id.building_guard_stations_progress_count, R.id.building_guard_stations_progress_percent);
        _drawBuilding(Building.Type.WATCHTOWERS, R.id.building_watch_towers_count, R.id.building_watch_towers_percent, R.id.building_watch_towers_progress_count, R.id.building_watch_towers_progress_percent);
        _drawBuilding(Building.Type.HOSPITALS, R.id.building_hospitals_count, R.id.building_hospitals_percent, R.id.building_hospitals_progress_count, R.id.building_hospitals_progress_percent);
        _drawBuilding(Building.Type.GUILDS, R.id.building_guilds_count, R.id.building_guilds_percent, R.id.building_guilds_progress_count, R.id.building_guilds_progress_percent);
        _drawBuilding(Building.Type.THIEVES_DENS, R.id.building_thieves_dens_count, R.id.building_thieves_dens_percent, R.id.building_thieves_dens_progress_count, R.id.building_thieves_dens_progress_percent);
        _drawBuilding(Building.Type.TOWERS, R.id.building_towers_count, R.id.building_towers_percent, R.id.building_towers_progress_count, R.id.building_towers_progress_percent);
    }
}