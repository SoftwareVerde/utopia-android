package com.softwareverde.utopia.ui.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softwareverde.util.StringUtil;
import com.softwareverde.utopia.ActiveSpell;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.Spell;
import com.softwareverde.utopia.intelsync.VerdeIntelUtil;
import com.softwareverde.utopia.ui.UiTheme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Kingdom2Adapter extends BaseAdapter {
    private Activity _activity;
    private LayoutInflater _inflater;
    private ArrayList<Province> _dataSet = new ArrayList<Province>();
    private Session _session;
    private Integer _previouslySelectedIndex = -1;
    private List<ButtonCallback> _buttonCallbacks = new LinkedList<ButtonCallback>();

    public enum ButtonActionType {
        View,
        Thievery,
        Magic,
        Attack
    }
    public interface ButtonCallback {
        void onClick(ButtonActionType buttonActionType, Integer index);
    }

    public Kingdom2Adapter(Activity activity) {
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

    @Override
    public View getView(final int position, final View recycledView, final ViewGroup viewGroup) {
        // NOTE: The view can sometimes be recycled view from a different layout, so always inflate.
        final View view = _inflater.inflate(R.layout.kingdom2_item, viewGroup, false);

        final Province province = this.getItem(position);
        final UiTheme uiTheme = UiTheme.getThemeForRace(province.getRace());

        ((TextView) view.findViewById(R.id.kingdom_item_name)).setText(province.getName());
        ((TextView) view.findViewById(R.id.kingdom_item_acres)).setText(StringUtil.formatNumberString(province.getAcres()));
        ((TextView) view.findViewById(R.id.kingdom_item_networth)).setText(StringUtil.formatNumberString(province.getNetworth()));

        final VerdeIntelUtil.AvailableIntel availableIntel = _session.getVerdeIntelCountsForProvince(province.getName(), province.getKingdomIdentifier());
        final View intelCountView = view.findViewById(R.id.kingdom_item_verde_intel_count);
        if (availableIntel == null || availableIntel.getIntelCount() == 0) {
            intelCountView.setVisibility(View.INVISIBLE);
        }
        else {
            intelCountView.setVisibility(View.VISIBLE);
            final Integer backgroundColor;
            final Long intelAgeInMinutes = (System.currentTimeMillis() - availableIntel.getLastUpdateTime()) / 1000L / 60L;
            if (intelAgeInMinutes > 60L * 4) {
                backgroundColor = Color.parseColor("#AA0000");
            }
            else if (intelAgeInMinutes > 60L * 2) {
                backgroundColor = Color.parseColor("#CCCC00");
            }
            else {
                backgroundColor = Color.parseColor("#00AA00");
            }
            intelCountView.setBackgroundColor(backgroundColor);
        }

        final ImageView raceIconImageView = ((ImageView) view.findViewById(R.id.kingdom_item_race_icon));
        raceIconImageView.setImageResource(uiTheme.getIconId());

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
