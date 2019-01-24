package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class DraftRateBundle extends Bundle {
    public static final String BUNDLE_TYPE = "DRAFTRATEBUN";

    public static class Keys {
        public static final String IDENTIFIER     = BUNDLE_TYPE +"_IDENTIFIER";
        public static final String NAME           = BUNDLE_TYPE +"_NAME";
        public static final String RATE           = BUNDLE_TYPE +"_RATE";
        public static final String RATE_COST      = BUNDLE_TYPE +"_RATE_COST";
        public static final String IS_SELECTED    = BUNDLE_TYPE +"_IS_SELECTED";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.IDENTIFIER, Keys.NAME, Keys.RATE, Keys.RATE_COST, Keys.IS_SELECTED);

    public DraftRateBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
