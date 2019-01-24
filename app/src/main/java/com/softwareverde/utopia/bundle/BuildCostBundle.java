package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class BuildCostBundle extends Bundle {
    public static final String BUNDLE_TYPE = "BLDCSTBDL";

    public static class Keys {
        public static final String CONSTRUCTION_COST  = BUNDLE_TYPE +"_CONSTRUCTION_COST";
        public static final String RAZE_COST          = BUNDLE_TYPE +"_RAZE_COST";
        public static final String FREE_CREDITS       = BUNDLE_TYPE +"_FREE_CREDITS";
        public static final String CONSTRUCTION_TIME  = BUNDLE_TYPE +"_BUILD_TIME";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.CONSTRUCTION_COST, Keys.RAZE_COST, Keys.FREE_CREDITS, Keys.CONSTRUCTION_TIME);

    public BuildCostBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
