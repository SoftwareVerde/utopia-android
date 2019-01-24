package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class StateCouncilBundle extends Bundle {
    public static final String BUNDLE_TYPE = "STCNLBUN";

    public static class Keys {
        public static final String TICK                   = BUNDLE_TYPE +"_TICK";
        public static final String MAX_POPULATION         = BUNDLE_TYPE +"_MAX_POPULATION";
        public static final String UNEMPLOYED_PEASANTS    = BUNDLE_TYPE +"_UNEMPLOYED_PEASANTS";
        public static final String UNFILLED_JOBS          = BUNDLE_TYPE +"_UNFILLED_JOBS";
        public static final String EMPLOYMENT_PERCENT     = BUNDLE_TYPE +"_EMPLOYMENT_PERCENT";
        public static final String INCOME                 = BUNDLE_TYPE +"_INCOME";
        public static final String MILITARY_WAGES         = BUNDLE_TYPE +"_MIL_WAGES";
        public static final String HONOR                  = BUNDLE_TYPE +"_HONOR";
        public static final String STATE_HISTORY_BUNDLE   = BUNDLE_TYPE +"_HISTORY_BUNDLE";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.TICK, Keys.MAX_POPULATION, Keys.UNEMPLOYED_PEASANTS, Keys.UNFILLED_JOBS, Keys.EMPLOYMENT_PERCENT, Keys.INCOME, Keys.MILITARY_WAGES, Keys.HONOR);

    public StateCouncilBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
