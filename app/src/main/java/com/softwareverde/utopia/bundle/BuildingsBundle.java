package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class BuildingsBundle extends Bundle {
    public static final String BUNDLE_TYPE = "BUILDINGSBDL";

    public static class Keys {
        public static final String BARREN             = BUNDLE_TYPE +"_BARREN";
        public static final String HOMES              = BUNDLE_TYPE +"_HOMES";
        public static final String FARMS              = BUNDLE_TYPE +"_FARMS";
        public static final String MILLS              = BUNDLE_TYPE +"_MILLS";
        public static final String BANKS              = BUNDLE_TYPE +"_BANKS";
        public static final String TRAINING_GROUNDS   = BUNDLE_TYPE +"_TRAINING_GROUNDS";
        public static final String ARMORIES           = BUNDLE_TYPE +"_ARMORIES";
        public static final String BARRACKS           = BUNDLE_TYPE +"_BARRACKS";
        public static final String FORTS              = BUNDLE_TYPE +"_FORTS";
        public static final String GUARD_STATIONS     = BUNDLE_TYPE +"_GUARD_STATIONS";
        public static final String HOSPITALS          = BUNDLE_TYPE +"_HOSPITALS";
        public static final String GUILDS             = BUNDLE_TYPE +"_GUILDS";
        public static final String TOWERS             = BUNDLE_TYPE +"_TOWERS";
        public static final String THIEVES_DENS       = BUNDLE_TYPE +"_THIEVES_DENS";
        public static final String WATCH_TOWERS       = BUNDLE_TYPE +"_WATCH_TOWERS";
        public static final String LABORATORIES       = BUNDLE_TYPE +"_LABORATORIES";
        public static final String UNIVERSITIES       = BUNDLE_TYPE +"_UNIVERSITIES";
        public static final String STABLES            = BUNDLE_TYPE +"_STABLES";
        public static final String DUNGEONS           = BUNDLE_TYPE +"_DUNGEONS";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.BARREN, Keys.HOMES, Keys.FARMS, Keys.MILLS, Keys.BANKS,
        Keys.TRAINING_GROUNDS, Keys.ARMORIES, Keys.BARRACKS, Keys.FORTS,
        Keys.GUARD_STATIONS, Keys.HOSPITALS, Keys.GUILDS, Keys.TOWERS,
        Keys.THIEVES_DENS, Keys.WATCH_TOWERS, Keys.LABORATORIES, Keys.UNIVERSITIES,
        /* Keys.STABLES, */ Keys.DUNGEONS
    );

    public BuildingsBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
