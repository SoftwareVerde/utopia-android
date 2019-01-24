package com.softwareverde.utopia.ui;

import android.graphics.Color;

import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;

import java.util.Arrays;
import java.util.List;

public class UiTheme {
    public static UiTheme getThemeForRace(final Province.Race race) {
        return new UiTheme(race);
    }

    private static final List<Province.Race> _races = Arrays.asList(
        Province.Race.AVIAN,            Province.Race.DWARF,            Province.Race.ELF,
        Province.Race.FAERY,            Province.Race.HALFLING,         Province.Race.HUMAN,
        Province.Race.ORC,              Province.Race.UNDEAD,           Province.Race.DRYAD,
        Province.Race.DARK_ELF,         Province.Race.BOCAN
    );

    private static final List<Integer> _primaryColors = Arrays.asList(
        Color.parseColor("#000358"),    Color.parseColor("#606060"),    Color.parseColor("#136917"),
        Color.parseColor("#690B0B"),    Color.parseColor("#695713"),    Color.parseColor("#BA6A00"),
        Color.parseColor("#105B14"),    Color.parseColor("#4B2700"),    Color.parseColor("#3A583A"),
        Color.parseColor("#136917"),    Color.parseColor("#403A68")
    );

    private static final List<Integer> _secondaryColors = Arrays.asList(
        Color.parseColor("#00002E"),    Color.parseColor("#353535"),    Color.parseColor("#0E3109"),
        Color.parseColor("#310202"),    Color.parseColor("#312909"),    Color.parseColor("#834A00"),
        Color.parseColor("#0B2607"),    Color.parseColor("#291600"),    Color.parseColor("#2F412F"),
        Color.parseColor("#0E3109"),    Color.parseColor("#1F155E")
    );

    private static final List<Integer> _raceIconIds = Arrays.asList(
        R.drawable.portrait_avian,  R.drawable.portrait_dwarf,      R.drawable.portrait_elf,
        R.drawable.portrait_faery,  R.drawable.portrait_halfling,   R.drawable.portrait_human,
        R.drawable.portrait_orc,    R.drawable.portrait_undead,     R.drawable.portrait_dryad,
        R.drawable.dark_elf_icon,   R.drawable.bocan_icon
    );

    private final Province.Race _race;
    private final Integer _themeIndex;

    private UiTheme(final Province.Race race) {
        _race = race;
        _themeIndex = _races.indexOf(_race);
    }

    public Integer getIconId() {
        return _raceIconIds.get(_themeIndex);
    }

    public Integer getPrimaryColor() {
        return _primaryColors.get(_themeIndex);
    }

    public Integer getSecondaryColor() {
        return _secondaryColors.get(_themeIndex);
    }

}
