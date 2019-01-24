package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ScienceBundle extends Bundle {
    public static final String BUNDLE_TYPE = "SCIBUN";

    public static class Keys {
        public static final String ALCHEMY_SCIENTIST_COUNT      = BUNDLE_TYPE +"_ALCHEMY_SCIENTIST_COUNT";
        public static final String TOOL_SCIENTIST_COUNT         = BUNDLE_TYPE +"_TOOL_SCIENTIST_COUNT";
        public static final String HOUSING_SCIENTIST_COUNT      = BUNDLE_TYPE +"_HOUSING_SCIENTIST_COUNT";
        public static final String FOOD_SCIENTIST_COUNT         = BUNDLE_TYPE +"_FOOD_SCIENTIST_COUNT";
        public static final String MILITARY_SCIENTIST_COUNT     = BUNDLE_TYPE +"_MILITARY_SCIENTIST_COUNT";
        public static final String CRIME_SCIENTIST_COUNT        = BUNDLE_TYPE +"_CRIME_SCIENTIST_COUNT";
        public static final String CHANNELING_SCIENTIST_COUNT   = BUNDLE_TYPE +"_CHANNELING_SCIENTIST_COUNT";

        public static final String ALCHEMY_EFFECT               = BUNDLE_TYPE +"_ALCHEMY_EFFECT";
        public static final String TOOL_EFFECT                  = BUNDLE_TYPE +"_TOOL_EFFECT";
        public static final String HOUSING_EFFECT               = BUNDLE_TYPE +"_HOUSING_EFFECT";
        public static final String FOOD_EFFECT                  = BUNDLE_TYPE +"_FOOD_EFFECT";
        public static final String MILITARY_EFFECT              = BUNDLE_TYPE +"_MILITARY_EFFECT";
        public static final String CRIME_EFFECT                 = BUNDLE_TYPE +"_CRIME_EFFECT";
        public static final String CHANNELING_EFFECT            = BUNDLE_TYPE +"_CHANNELING_EFFECT";

        public static final String SCIENTISTS_GROUP             = BUNDLE_TYPE +"_SCIENTISTS_GROUP";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.ALCHEMY_SCIENTIST_COUNT, Keys.TOOL_SCIENTIST_COUNT, Keys.HOUSING_SCIENTIST_COUNT,
        Keys.FOOD_SCIENTIST_COUNT, Keys.MILITARY_SCIENTIST_COUNT, Keys.CRIME_SCIENTIST_COUNT,
        Keys.CHANNELING_SCIENTIST_COUNT,

        Keys.ALCHEMY_EFFECT, Keys.TOOL_EFFECT, Keys.HOUSING_EFFECT, Keys.FOOD_EFFECT,
        Keys.MILITARY_EFFECT, Keys.CRIME_EFFECT, Keys.CHANNELING_EFFECT

        // Keys.SCIENTISTS_GROUP
    );

    public ScienceBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
