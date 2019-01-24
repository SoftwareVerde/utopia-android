package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class FundDragonInfoBundle extends Bundle {
    public static final String BUNDLE_TYPE = "FNDDRGNBDL";

    public static class Keys {
        public static final String COST_REMAINING = BUNDLE_TYPE +"_COST_REMINAING";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.COST_REMAINING);

    public FundDragonInfoBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
