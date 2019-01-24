package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class InfiltrateThievesBundle extends Bundle {
    public static final String BUNDLE_TYPE = "INFILTHVBDL";

    public static class Keys {
        public static final String THIEVES    = BUNDLE_TYPE +"_THIEVES";
        public static final String CONFIDENCE = BUNDLE_TYPE +"_CONFIDENCE";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.THIEVES, Keys.CONFIDENCE);

    public InfiltrateThievesBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
