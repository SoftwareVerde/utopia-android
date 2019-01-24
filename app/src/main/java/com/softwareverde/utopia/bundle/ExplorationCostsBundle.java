package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ExplorationCostsBundle extends Bundle {
    public static final String BUNDLE_TYPE = "XPLRCSTBDL";

    public static class Keys {
        public static final String SOLDIERS_PER_ACRE      = BUNDLE_TYPE +"_SOLDERS_PER_ACRE";
        public static final String GOLD_PER_ACRE          = BUNDLE_TYPE +"_GOLD_PER_ACRE";
        public static final String AVAILABLE_ACRES        = BUNDLE_TYPE +"_AVAILABLE_ACRES";
        public static final String CURRENTLY_EXPLORING    = BUNDLE_TYPE +"_CURRENTLY_EXPLORING";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.SOLDIERS_PER_ACRE, Keys.GOLD_PER_ACRE, Keys.AVAILABLE_ACRES);

    public ExplorationCostsBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
