package com.softwareverde.utopia.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softwareverde.utopia.Building;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.ThieveryOperation;

import java.util.ArrayList;
import java.util.List;

public class ThieveryOperationAdapter extends BaseAdapter {
    private Activity _activity;
    private LayoutInflater _inflater;
    private ArrayList<ThieveryOperation> _dataSet = new ArrayList<ThieveryOperation>();

    private Building.Type _targetBuilding = null;
    private Runnable _onTargetBuildingClicked = null;


    public ThieveryOperationAdapter(final Activity activity) {
        _activity = activity;
        _inflater = LayoutInflater.from(_activity.getApplicationContext());
    }

    public void setTargetBuilding(final Building.Type building) {
        _targetBuilding = building;
        this.notifyDataSetChanged();
    }

    public void setOnTargetBuildingClicked(final Runnable onClickCallback) {
        _onTargetBuildingClicked = onClickCallback;
    }

    public Building.Type getTargetBuilding() {
        return _targetBuilding;
    }

    private void _sortDataSet() { }

    @Override
    public int getCount() {
        return _dataSet.size();
    }

    @Override
    public ThieveryOperation getItem(int position) {
        return _dataSet.get(position);
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        if (view == null) {
            view = _inflater.inflate(R.layout.thievery_operation_item, viewGroup, false);
        }

        final ThieveryOperation thieveryOperation = this.getItem(position);

        ((TextView) view.findViewById(R.id.thievery_operation_item_name)).setText(thieveryOperation.getName());

        final View buildingTarget = view.findViewById(R.id.thievery_operation_item_target_building_container);
        if (thieveryOperation.getIdentifier().equals(ThieveryOperation.Identifiers.GREATER_ARSON)) {
            buildingTarget.setVisibility(View.VISIBLE);

            final TextView buildingTargetText = (TextView) view.findViewById(R.id.thievery_operation_item_target_building);
            if (_targetBuilding != null) {
                buildingTargetText.setText(Building.getBuildingName(_targetBuilding));
            }
            else {
                buildingTargetText.setText("Random");
            }

            buildingTarget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_onTargetBuildingClicked != null) {
                        _onTargetBuildingClicked.run();
                    }
                }
            });
        }
        else {
            buildingTarget.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return (_dataSet.size() == 0);
    }

    public void add(ThieveryOperation thieveryOperation) {
        _dataSet.add(thieveryOperation);

        _sortDataSet();

        this.notifyDataSetChanged();
    }

    public void addAll(List<ThieveryOperation> thieveryOperationList) {
        for (ThieveryOperation item : thieveryOperationList) {
            _dataSet.add(item);
        }

        _sortDataSet();

        this.notifyDataSetChanged();
    }

    public void clear() {
        _dataSet.clear();
        this.notifyDataSetChanged();
    }
}
