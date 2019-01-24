package com.softwareverde.utopia.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softwareverde.utopia.R;

import java.util.ArrayList;

public class DrawerItemAdapter extends BaseAdapter {
    Activity _activity;
    LayoutInflater _inflater;
    ArrayList<DrawerItem> _dataSet = new ArrayList<DrawerItem>();

    public DrawerItemAdapter(Activity activity) {
        _activity = activity;
        _inflater = LayoutInflater.from(_activity.getApplicationContext());
    }

    private Integer _getIndexFromPosition(int position) {
        int enabledCount = 0;
        for (DrawerItem drawerItem : _dataSet) {
            if (enabledCount == position) return enabledCount;

            if (drawerItem.isEnabled()) enabledCount += 1;
        }

        return null;
    }

    @Override
    public int getCount() {
        int count = 0;
        for (DrawerItem drawerItem : _dataSet) {
            if (drawerItem.isEnabled()) count += 1;
        }
        return count;
    }

    @Override
    public DrawerItem getItem(int position) {
        return _getItem(position);
    }

    private DrawerItem _getItem(int position) {
        Integer index = _getIndexFromPosition(position);
        if (index != null && index >= 0 && index < _dataSet.size()) {
            return _dataSet.get(index);
        }

        return null;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // NOTE: It is possible for the view to be not a navigationItem, thus it is inflated every time.
        view = _inflater.inflate(R.layout.navigation_item, viewGroup, false);

        DrawerItem drawerItem = this.getItem(position);

        final TextView titleView = (TextView) view.findViewById(R.id.drawer_item_title);
        if (titleView != null) {
            titleView.setText(drawerItem.getTitle());
        }

        return view;
    }

    @Override
    public long getItemId(int position) {
        DrawerItem drawerItem = _getItem(position);
        if (drawerItem != null) {
            return drawerItem.getId();
        }

        return -1L;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return (_dataSet.size() == 0);
    }

    public void add(DrawerItem drawerItem) {
        _dataSet.add(drawerItem);

        this.notifyDataSetChanged();
    }
}
