package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class KingdomProvinceBundle extends Bundle {
    public static final String BUNDLE_TYPE = "KNGDMPVNCBDL";

    public static class Keys {
        public static final String NAME       = BUNDLE_TYPE +"_NAME";
        public static final String ACRES      = BUNDLE_TYPE +"_ACRES";
        public static final String RACE       = BUNDLE_TYPE +"_RACE";
        public static final String NETWORTH   = BUNDLE_TYPE +"_NETWORTH";
        public static final String NWPA       = BUNDLE_TYPE +"_NWPA";
        public static final String TITLE      = BUNDLE_TYPE +"_TITLE";
        public static final String IS_MONARCH = BUNDLE_TYPE +"_IS_MONARCH";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.NAME, Keys.ACRES, Keys.RACE, Keys.NETWORTH, Keys.NWPA, Keys.TITLE, Keys.IS_MONARCH);

    public KingdomProvinceBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
