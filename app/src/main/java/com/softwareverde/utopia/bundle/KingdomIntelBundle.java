package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class KingdomIntelBundle extends Bundle {
    public static final String BUNDLE_TYPE = "KNGDMINTLBDL";

    public static class Keys {
        public static final String PROVINCES = BUNDLE_TYPE +"_PROVINCES";
    }

    private List<String> _requiredKeys = Arrays.asList();

    public KingdomIntelBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
