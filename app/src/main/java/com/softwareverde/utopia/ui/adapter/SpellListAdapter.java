package com.softwareverde.utopia.ui.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.Spell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SpellListAdapter extends BaseAdapter {
    Activity _activity;
    LayoutInflater _inflater;
    ArrayList<Spell> _dataSet = new ArrayList<Spell>();
    Session _session;
    Province _targetProvince;

    public SpellListAdapter(Activity activity, Province targetProvince) {
        _activity = activity;
        _inflater = LayoutInflater.from(_activity.getApplicationContext());
        _session = Session.getInstance();

        if (targetProvince != null) {
            _targetProvince = targetProvince;
        }
        else {
            _targetProvince = _session.getProvince();
        }
    }

    private void _sortDataSet() {
        Collections.sort(_dataSet, new Comparator<Spell>() {
            public int compare(Spell lhs, Spell rhs) {
                if (lhs.getRuneCost() == null && rhs.getRuneCost() == null) { return 0; }
                else if (lhs.getRuneCost() == null) { return -1; }
                else if (rhs.getRuneCost() == null) { return 1; }

                return lhs.getRuneCost().compareTo(rhs.getRuneCost());
            }
        });
    }

    @Override
    public int getCount() {
        return _dataSet.size();
    }

    @Override
    public Spell getItem(int position) {
        return _dataSet.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = _inflater.inflate(R.layout.spell_item, viewGroup, false);
        }

        Spell spell = this.getItem(position);

        ((TextView) view.findViewById(R.id.spell_item_name)).setText(spell.getName());

        final Integer runeCost = spell.getRuneCost();
        final String runeCostString = (runeCost == null ? "" : Util.formatNumberString(runeCost));
        ((TextView) view.findViewById(R.id.spell_item_rune_cost)).setText(runeCostString);

        TextView spellItemDurationText = (TextView) view.findViewById(R.id.spell_item_duration);
        Integer spellDuration = _targetProvince.getSpellDuration(spell.getName());
        if (spellDuration > 0) {
            Integer hours = (int) (spellDuration / 60.0f / 60.0f);
            Integer minutes = (int) (((spellDuration / 60.0f / 60.0f) - hours) * 60.0f);
            spellItemDurationText.setText(String.format("%dh %dm", hours, minutes));
            if (hours > 0) {
                spellItemDurationText.setTextColor(Color.parseColor("#AAFFAA"));
            }
            else {
                spellItemDurationText.setTextColor(Color.parseColor("#FFAAAA"));
            }
        }
        else {
            spellItemDurationText.setText("-");
            spellItemDurationText.setTextColor(Color.parseColor("#505050"));
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

    public void add(Spell spellItem) {
        _dataSet.add(spellItem);

        _sortDataSet();

        this.notifyDataSetChanged();
    }

    public void addAll(List<Spell> spellList) {
        for (Spell item : spellList) {
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
