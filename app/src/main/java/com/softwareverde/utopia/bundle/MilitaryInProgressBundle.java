package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class MilitaryInProgressBundle extends Bundle {
    public static final String BUNDLE_TYPE = "MILPROGRESSBUN";

    public static class Keys {
        public static final String TICK_00 = BUNDLE_TYPE +"_TICK_00";
        public static final String TICK_01 = BUNDLE_TYPE +"_TICK_01";
        public static final String TICK_02 = BUNDLE_TYPE +"_TICK_02";
        public static final String TICK_03 = BUNDLE_TYPE +"_TICK_03";
        public static final String TICK_04 = BUNDLE_TYPE +"_TICK_04";
        public static final String TICK_05 = BUNDLE_TYPE +"_TICK_05";
        public static final String TICK_06 = BUNDLE_TYPE +"_TICK_06";
        public static final String TICK_07 = BUNDLE_TYPE +"_TICK_07";
        public static final String TICK_08 = BUNDLE_TYPE +"_TICK_08";
        public static final String TICK_09 = BUNDLE_TYPE +"_TICK_09";
        public static final String TICK_10 = BUNDLE_TYPE +"_TICK_10";
        public static final String TICK_11 = BUNDLE_TYPE +"_TICK_11";
        public static final String TICK_12 = BUNDLE_TYPE +"_TICK_12";
        public static final String TICK_13 = BUNDLE_TYPE +"_TICK_13";
        public static final String TICK_14 = BUNDLE_TYPE +"_TICK_14";
        public static final String TICK_15 = BUNDLE_TYPE +"_TICK_15";
        public static final String TICK_16 = BUNDLE_TYPE +"_TICK_16";
        public static final String TICK_17 = BUNDLE_TYPE +"_TICK_17";
        public static final String TICK_18 = BUNDLE_TYPE +"_TICK_18";
        public static final String TICK_19 = BUNDLE_TYPE +"_TICK_19";
        public static final String TICK_20 = BUNDLE_TYPE +"_TICK_20";
        public static final String TICK_21 = BUNDLE_TYPE +"_TICK_21";
        public static final String TICK_22 = BUNDLE_TYPE +"_TICK_22";
        public static final String TICK_23 = BUNDLE_TYPE +"_TICK_23";
    }

    public static final List<String> ORDERED_TICK_KEYS = Arrays.asList(
        Keys.TICK_00, Keys.TICK_01, Keys.TICK_02, Keys.TICK_03, Keys.TICK_04, Keys.TICK_05,
        Keys.TICK_06, Keys.TICK_07, Keys.TICK_08, Keys.TICK_09, Keys.TICK_10, Keys.TICK_11,
        Keys.TICK_12, Keys.TICK_13, Keys.TICK_14, Keys.TICK_15, Keys.TICK_16, Keys.TICK_17,
        Keys.TICK_18, Keys.TICK_19, Keys.TICK_20, Keys.TICK_21, Keys.TICK_22, Keys.TICK_23
    );

    private List<String> _requiredKeys = Arrays.asList(
        Keys.TICK_00, Keys.TICK_01, Keys.TICK_02, Keys.TICK_03, Keys.TICK_04, Keys.TICK_05,
        Keys.TICK_06, Keys.TICK_07, Keys.TICK_08, Keys.TICK_09, Keys.TICK_10, Keys.TICK_11,
        Keys.TICK_12, Keys.TICK_13, Keys.TICK_14, Keys.TICK_15, Keys.TICK_16, Keys.TICK_17,
        Keys.TICK_18, Keys.TICK_19, Keys.TICK_20, Keys.TICK_21, Keys.TICK_22, Keys.TICK_23
    );

    public MilitaryInProgressBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
