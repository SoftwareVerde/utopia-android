package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class MilitarySettingsBundle extends Bundle {
    public static final String BUNDLE_TYPE = "MILSETBUN";

    public static class Keys {
        public static final String DRAFT_RATES            = BUNDLE_TYPE +"_DRAFT_RATES";
        public static final String DRAFT_TARGET           = BUNDLE_TYPE +"_DRAFT_TARGET";
        public static final String WAGE_RATE              = BUNDLE_TYPE +"_WAGE_RATE";

        public static final String OFFENSIVE_UNIT_COST    = BUNDLE_TYPE +"_OFFENSIVE_UNIT_COST";
        public static final String DEFENSIVE_UNIT_COST    = BUNDLE_TYPE +"_DEFENSIVE_UNIT_COST";
        public static final String ELITE_COST             = BUNDLE_TYPE +"_ELITE_COST";
        public static final String THIEF_COST             = BUNDLE_TYPE +"_THIEF_COST";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.DRAFT_RATES, Keys.DRAFT_TARGET, Keys.WAGE_RATE, Keys.OFFENSIVE_UNIT_COST, Keys.DEFENSIVE_UNIT_COST, Keys.THIEF_COST);

    public MilitarySettingsBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
