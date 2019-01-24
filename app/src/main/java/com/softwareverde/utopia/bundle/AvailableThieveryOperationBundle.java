package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class AvailableThieveryOperationBundle extends Bundle {
    public static final String BUNDLE_TYPE = "AVLTHVYOPBDL";

    public static class Keys {
        public static final String NAME       = BUNDLE_TYPE +"_NAME";
        public static final String IDENTIFIER = BUNDLE_TYPE +"_IDENTIFIER";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.NAME, Keys.IDENTIFIER);

    public AvailableThieveryOperationBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
