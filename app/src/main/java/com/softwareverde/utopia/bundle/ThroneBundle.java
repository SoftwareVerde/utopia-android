package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ThroneBundle extends Bundle {
    public static final String BUNDLE_TYPE = "THRONEBDL";

    public static class Keys {
        public static final String PROVINCE_NAME          = BUNDLE_TYPE +"_PROVINCE_NAME";
        public static final String KINGDOM                = BUNDLE_TYPE +"_KINGDOM";
        public static final String ISLAND                 = BUNDLE_TYPE +"_ISLAND";

        public static final String LAND                   = BUNDLE_TYPE +"_LAND";
        public static final String RACE                   = BUNDLE_TYPE +"_RACE";
        public static final String PERSONALITY            = BUNDLE_TYPE +"_PERSONALITY";
        public static final String RULER_NAME             = BUNDLE_TYPE +"_RULER_NAME";
        public static final String TITLE                  = BUNDLE_TYPE +"_TITLE";
        public static final String NETWORTH               = BUNDLE_TYPE +"_NETWORTH";

        public static final String FOOD                   = BUNDLE_TYPE +"_FOOD";
        public static final String GOLD                   = BUNDLE_TYPE +"_MONEY";
        public static final String RUNES                  = BUNDLE_TYPE +"_RUNES";
        public static final String PEASANTS               = BUNDLE_TYPE +"_PEASANTS";

        public static final String BUILDING_EFFICIENCY    = BUNDLE_TYPE +"_BUILDING_EFFICIENCY";
        public static final String TRADE_BALANCE          = BUNDLE_TYPE +"_TRADE_BALANCE";
        public static final String SOLDIERS               = BUNDLE_TYPE +"_SOLDIERS";
        public static final String OFFENSIVE_UNITS        = BUNDLE_TYPE +"_OFFENSIVE_UNITS";
        public static final String DEFENSIVE_UNITS        = BUNDLE_TYPE +"_DEFENSIVE_UNITS";
        public static final String ELITES                 = BUNDLE_TYPE +"_ELITES";
        public static final String THIEVES                = BUNDLE_TYPE +"_THIEVES";
        public static final String WIZARDS                = BUNDLE_TYPE +"_WIZARDS";
        public static final String HORSES                 = BUNDLE_TYPE +"_HORSES";
        public static final String PRISONERS              = BUNDLE_TYPE +"_PRISONERS";
        public static final String OFFENSIVE_POINTS       = BUNDLE_TYPE +"_OFFENSIVE_POINTS";
        public static final String DEFENSIVE_POINTS       = BUNDLE_TYPE +"_DEFENSIVE_POINTS";
        public static final String STEALTH                = BUNDLE_TYPE +"_STEALTH";
        public static final String MANA                   = BUNDLE_TYPE +"_MANA";
        public static final String ROYAL_COMMANDS         = BUNDLE_TYPE +"_ROYAL_COMMANDS";

        public static final String DRAGON_TYPE            = BUNDLE_TYPE +"_DRAGON_TYPE";
        public static final String HAS_PLAGUE             = BUNDLE_TYPE +"_HAS_PLAGUE";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.PROVINCE_NAME, Keys.KINGDOM, Keys.ISLAND, Keys.RACE, Keys.RULER_NAME,
        Keys.BUILDING_EFFICIENCY, Keys.TRADE_BALANCE, Keys.SOLDIERS, Keys.OFFENSIVE_UNITS,
        Keys.DEFENSIVE_UNITS, Keys.ELITES, Keys.HORSES, Keys.PRISONERS, Keys.OFFENSIVE_POINTS,
        Keys.DEFENSIVE_POINTS
        // Keys.THIEVES, Keys.WIZARDS, Keys.STEALTH, Keys.MANA, Keys.ROYAL_COMMANDS
    );

    public ThroneBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
