package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ProvinceIdsBundle extends Bundle {
    public static final String BUNDLE_TYPE = "PROVINCEIDSBDL";

    public static class Keys {
        public static final String PROVINCE_LIST_BUNDLE = BUNDLE_TYPE +"_ID_LIST";
    }

    private List<String> _requiredKeys = Arrays.asList();

    public ProvinceIdsBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
