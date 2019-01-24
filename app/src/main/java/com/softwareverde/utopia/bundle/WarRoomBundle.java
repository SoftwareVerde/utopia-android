package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class WarRoomBundle extends Bundle {
    public static final String BUNDLE_TYPE = "WARRMBDL";

    public static class Keys {
        public static final String OFFENSE_MODIFIER       = BUNDLE_TYPE +"_OFFENSE_MODIFIER";
        public static final String MERCENARY_COST         = BUNDLE_TYPE +"_MERC_COST";
        public static final String MERCENARY_RATE         = BUNDLE_TYPE +"_MERC_RATE";
        public static final String DEPLOYABLE_GENERALS    = BUNDLE_TYPE +"_DEPLOYABLE_GENS";
        public static final String MIN_CONQUEST_NW        = BUNDLE_TYPE +"_MIN_CONQUEST_NW";
        public static final String MAX_CONQUEST_NW        = BUNDLE_TYPE +"_MAX_CONQUEST_NW";
        public static final String ATTACK_TIME_PARAMS     = BUNDLE_TYPE +"_ATTACK_TIME_PARAMS";

        public static final String PROVINCE_LIST_BUNDLE = BUNDLE_TYPE +"_PROVINCE_LIST_BUNDLE";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.OFFENSE_MODIFIER, Keys.MERCENARY_COST, Keys.MERCENARY_RATE, Keys.DEPLOYABLE_GENERALS
        // Keys.MIN_CONQUEST_NW, Keys.MAX_CONQUEST_NW // NOTE: Conquest may not be available. (i.e. war)
    );

    public WarRoomBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}