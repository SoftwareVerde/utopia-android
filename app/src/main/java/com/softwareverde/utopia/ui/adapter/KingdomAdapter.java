package com.softwareverde.utopia.ui.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.softwareverde.util.StringUtil;
import com.softwareverde.utopia.Util;
import com.softwareverde.utopia.ActiveSpell;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.Spell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class KingdomAdapter extends BaseAdapter {
    Activity _activity;
    LayoutInflater _inflater;
    ArrayList<Province> _dataSet = new ArrayList<Province>();
    Session _session;
    Integer _previouslySelectedIndex = -1;
    List<ButtonCallback> _buttonCallbacks = new LinkedList<ButtonCallback>();

    public enum ButtonActionType {
        View,
        Thievery,
        Magic,
        Attack
    }
    public interface ButtonCallback {
        void onClick(ButtonActionType buttonActionType, Integer index);
    }

    public KingdomAdapter(Activity activity) {
        _activity = activity;
        _inflater = LayoutInflater.from(_activity.getApplicationContext());
        _session = Session.getInstance();
    }

    private void _sortDataSet() {
        Collections.sort(_dataSet, new Comparator<Province>() {
            public int compare(Province lhs, Province rhs) {
                return rhs.getNetworth().compareTo(lhs.getNetworth());
            }
        });
    }

    @Override
    public int getCount() {
        return _dataSet.size();
    }

    @Override
    public Province getItem(int position) {
        return _dataSet.get(position);
    }

    private View.OnClickListener _createButtonListener(final View view, final ButtonActionType buttonActionType, final Integer index) {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View buttonView) {
                view.findViewById(R.id.kingdom_item_view_button).setBackgroundColor(Color.BLACK);
                view.findViewById(R.id.kingdom_item_thievery_button).setBackgroundColor(Color.BLACK);
                view.findViewById(R.id.kingdom_item_magic_button).setBackgroundColor(Color.BLACK);
                view.findViewById(R.id.kingdom_item_attack_button).setBackgroundColor(Color.BLACK);

                buttonView.setBackgroundColor(Color.DKGRAY);

                (new Thread() {
                    @Override
                    public void run() {
                        for (ButtonCallback buttonCallback : _buttonCallbacks) {
                            buttonCallback.onClick(buttonActionType, index);
                        }
                    }
                }).start();
            }
        };
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = _inflater.inflate(R.layout.kingdom_item, viewGroup, false);
        }

        Province province = this.getItem(position);


        Button button;

        button = (Button) view.findViewById(R.id.kingdom_item_view_button);
        button.setOnClickListener(_createButtonListener(view, ButtonActionType.View, position));

        button = (Button) view.findViewById(R.id.kingdom_item_thievery_button);
        button.setOnClickListener(_createButtonListener(view, ButtonActionType.Thievery, position));

        button = (Button) view.findViewById(R.id.kingdom_item_magic_button);
        button.setOnClickListener(_createButtonListener(view, ButtonActionType.Magic, position));

        button = (Button) view.findViewById(R.id.kingdom_item_attack_button);
        button.setOnClickListener(_createButtonListener(view, ButtonActionType.Attack, position));

        ((TextView) view.findViewById(R.id.kingdom_item_name)).setText(province.getName());
        ((TextView) view.findViewById(R.id.kingdom_item_acres)).setText(StringUtil.formatNumberString(province.getAcres()));
        ((TextView) view.findViewById(R.id.kingdom_item_networth)).setText(StringUtil.formatNumberString(province.getNetworth()));
        ((TextView) view.findViewById(R.id.kingdom_item_race)).setText(Province.getStringForRace(province.getRace()));

        final List<ActiveSpell> activeSpells = province.getActiveSpells();
        final List<String> activeSpellNames = new ArrayList<String>();
        for (final ActiveSpell activeSpell : activeSpells) {
            activeSpellNames.add(activeSpell.getSpellName());
        }
        view.findViewById(R.id.kingdom_item_intel_storms).setVisibility(activeSpellNames.contains(Spell.SpellNames.STORMS) ? View.VISIBLE : View.INVISIBLE);
        view.findViewById(R.id.kingdom_item_intel_drought).setVisibility(activeSpellNames.contains(Spell.SpellNames.DROUGHT) ? View.VISIBLE : View.INVISIBLE);
        // view.findViewById(R.id.kingdom_item_intel_vermin).setVisibility(activeSpellNames.contains(Spell.SpellNames.VERMIN) ? View.VISIBLE : View.INVISIBLE);
        view.findViewById(R.id.kingdom_item_intel_gluttony).setVisibility(activeSpellNames.contains(Spell.SpellNames.GLUTTONY) ? View.VISIBLE : View.INVISIBLE);
        view.findViewById(R.id.kingdom_item_intel_greed).setVisibility(activeSpellNames.contains(Spell.SpellNames.GREED) ? View.VISIBLE : View.INVISIBLE);
        view.findViewById(R.id.kingdom_item_intel_pitfalls).setVisibility(activeSpellNames.contains(Spell.SpellNames.PITFALLS) ? View.VISIBLE : View.INVISIBLE);
        view.findViewById(R.id.kingdom_item_intel_chastity).setVisibility(activeSpellNames.contains(Spell.SpellNames.CHASTITY) ? View.VISIBLE : View.INVISIBLE);
        view.findViewById(R.id.kingdom_item_intel_explosions).setVisibility(activeSpellNames.contains(Spell.SpellNames.EXPLOSIONS) ? View.VISIBLE : View.INVISIBLE);
        view.findViewById(R.id.kingdom_item_intel_meteor_showers).setVisibility(activeSpellNames.contains(Spell.SpellNames.METEOR_SHOWERS) ? View.VISIBLE : View.INVISIBLE);
        view.findViewById(R.id.kingdom_item_intel_riots).setVisibility(activeSpellNames.contains(Spell.SpellNames.RIOTS) ? View.VISIBLE : View.INVISIBLE);
        view.findViewById(R.id.kingdom_item_intel_blizzard).setVisibility(activeSpellNames.contains(Spell.SpellNames.BLIZZARD) ? View.VISIBLE : View.INVISIBLE);

        Integer defenseHome = province.getTotalDefenseAtHome();
        TextView defenseHomeView = (TextView) view.findViewById(R.id.kingdom_item_intel_defense_home);
        if (defenseHome != null && defenseHome > 0) {
            if (defenseHome < 100000) {
                defenseHomeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            }
            else {
                defenseHomeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            }
            defenseHomeView.setText((defenseHome / 1000) +"k");

            defenseHomeView.setVisibility(View.VISIBLE);
        }
        else {
            defenseHomeView.setVisibility(View.INVISIBLE);
        }

        if (position == _previouslySelectedIndex) {
            view.findViewById(R.id.kingdom_item_button_container).setVisibility(View.VISIBLE);
        }
        else {
            view.findViewById(R.id.kingdom_item_button_container).setVisibility(View.GONE);
        }

        return view;
    }

    public void selectIndex(Integer index) {
        _previouslySelectedIndex = index;
        this.notifyDataSetChanged();
    }
    public Integer getSelectedIndex() {
        return _previouslySelectedIndex;
    }
    public void deselectIndex() {
        _previouslySelectedIndex = -1;
        this.notifyDataSetChanged();
    }

    public void addButtonListener(ButtonCallback buttonCallback) {
        _buttonCallbacks.add(buttonCallback);
    }
    public void clearButtonListeners() {
        _buttonCallbacks.clear();
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

    public void add(Province province) {
        _dataSet.add(province);

        _sortDataSet();

        this.notifyDataSetChanged();
    }

    public void addAll(List<Province> provinceList) {
        for (Province item : provinceList) {
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
