package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ProvinceIntelBundle extends Bundle {
    public static final String BUNDLE_TYPE = "PRVNCINTLBDL";

    public static class Keys {
        public static final String PROVINCE_NAME          = BUNDLE_TYPE +"_PROVINCE_NAME";
        // public static final String DEFENSE_HOME           = BUNDLE_TYPE +"_DEFENSE_HOME";
        // public static final String INTEL_AGE              = BUNDLE_TYPE +"_INTEL_AGE";
        // public static final String ARMY_ONE_OUT_UNTIL     = BUNDLE_TYPE +"_ARMY_ONE_OUT_UNTIL";      // Milliseconds from Epoch
        // public static final String ARMY_TWO_OUT_UNTIL     = BUNDLE_TYPE +"_ARMY_TWO_OUT_UNTIL";      // Milliseconds from Epoch
        // public static final String ARMY_THREE_OUT_UNTIL   = BUNDLE_TYPE +"_ARMY_THREE_OUT_UNTIL";    // Milliseconds from Epoch
        // public static final String ARMY_FOUR_OUT_UNTIL    = BUNDLE_TYPE +"_ARMY_FOUR_OUT_UNTIL";     // Milliseconds from Epoch
        // public static final String ARMY_FIVE_OUT_UNTIL    = BUNDLE_TYPE +"_ARMY_FIVE_OUT_UNTIL";     // Milliseconds from Epoch
        public static final String ACTIVE_SPELLS_BUNDLE   = BUNDLE_TYPE +"_ACTIVE_SPELLS_BUNDLE";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.PROVINCE_NAME);

    public ProvinceIntelBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
