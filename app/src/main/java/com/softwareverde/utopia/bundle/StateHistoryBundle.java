package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class StateHistoryBundle extends Bundle {
    public static final String BUNDLE_TYPE = "STHISTBUN";

    public static class Keys {
        public static final String TICK           = BUNDLE_TYPE +"_TICK";
        public static final String REVENUE        = BUNDLE_TYPE +"_REVENUE";
        public static final String MILITARY_WAGES = BUNDLE_TYPE +"_MIL_WAGES";
        public static final String DRAFT_COST     = BUNDLE_TYPE +"_DRAFT_COST";
        // public static final String SCIENCE_COST   = BUNDLE_TYPE +"_SCIENCE_COST";
        public static final String PEASANTS_DELTA = BUNDLE_TYPE +"_PEASANTS_DELTA";
        public static final String FOOD_PRODUCED  = BUNDLE_TYPE +"_FOOD_PRODUCED";
        public static final String FOOD_REQUIRED  = BUNDLE_TYPE +"_FOOD_REQUIRED";
        public static final String FOOD_DECAYED   = BUNDLE_TYPE +"_FOOD_DECAYED";
        public static final String RUNES_PRODUCED = BUNDLE_TYPE +"_RUNES_PRODUCED";
        public static final String RUNES_DECAYED  = BUNDLE_TYPE +"_RUNES_DECAYED";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.REVENUE, Keys.MILITARY_WAGES, Keys.DRAFT_COST, /*Keys.SCIENCE_COST,*/ Keys.PEASANTS_DELTA,
        Keys.FOOD_PRODUCED, Keys.FOOD_REQUIRED, Keys.FOOD_DECAYED, Keys.RUNES_PRODUCED, Keys.RUNES_DECAYED
    );

    public StateHistoryBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
