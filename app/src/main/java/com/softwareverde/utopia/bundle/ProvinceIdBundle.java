package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ProvinceIdBundle extends Bundle {
    public static final String BUNDLE_TYPE = "PRVIDBDL";

    public static class Keys {
        public static final String NAME = BUNDLE_TYPE +"_NAME";
        public static final String UTOPIA_ID = BUNDLE_TYPE +"_UTOPIA_ID";
        public static final String KINGDOM = BUNDLE_TYPE +"_KINGDOM";
        public static final String ISLAND = BUNDLE_TYPE +"_ISLAND";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.NAME, Keys.UTOPIA_ID, Keys.KINGDOM, Keys.ISLAND);

    public ProvinceIdBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
