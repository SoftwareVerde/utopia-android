package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class BuildingBundle extends Bundle {
    public static final String BUNDLE_TYPE = "BUILDINGBDL";

    public static class Keys {
        public static final String NAME           = BUNDLE_TYPE +"_NAME";
        public static final String COUNT          = BUNDLE_TYPE +"_COUNT";
        public static final String PERCENT        = BUNDLE_TYPE +"_PERCENT";
        public static final String EFFECT         = BUNDLE_TYPE +"_EFFECT";
        public static final String IN_PROGRESS    = BUNDLE_TYPE +"_IN_PROGRESS";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.NAME, Keys.COUNT, Keys.PERCENT, Keys.EFFECT);

    public BuildingBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
