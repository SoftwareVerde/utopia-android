package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class AvailableSpellsBundle extends Bundle {
    public static final String BUNDLE_TYPE = "AVLSPLBDL";

    public static class Keys {
        public static final String SPELL_LIST_BUNDLE = BUNDLE_TYPE +"_SPELL_BUNDLE";
        public static final String PROVINCE_LIST_BUNDLE = BUNDLE_TYPE +"_PROVINCE_LIST_BUNDLE";
    }

    private List<String> _requiredKeys = Arrays.asList();

    public AvailableSpellsBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
