package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class AvailableThieveryOperationsBundle extends Bundle {
    public static final String BUNDLE_TYPE = "AVLTHVYOPSBDL";

    public static class Keys {
        public static final String THIEVERY_OPERATION_BUNDLE  = BUNDLE_TYPE +"_THIEVERY_OPERATION_BUNDLE";
        public static final String PROVINCE_LIST_BUNDLE       = BUNDLE_TYPE +"_PROVINCE_LIST_BUNDLE";
    }

    private List<String> _requiredKeys = Arrays.asList();

    public AvailableThieveryOperationsBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
