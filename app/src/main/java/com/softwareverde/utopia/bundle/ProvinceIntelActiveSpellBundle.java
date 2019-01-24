package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ProvinceIntelActiveSpellBundle extends Bundle {
    public static final String BUNDLE_TYPE = "ACTSPLPRVNCINTLBDL";

    public static class Keys {
        public static final String PROVINCE_NAME      = BUNDLE_TYPE +"_PROVINCE_NAME";
        public static final String SPELL_NAME         = BUNDLE_TYPE +"_SPELL_NAME";
        public static final String SPELL_EXPIRATION   = BUNDLE_TYPE +"_SPELL_EXPIRATION";  // Milliseconds from Epoch
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.PROVINCE_NAME, Keys.SPELL_NAME, Keys.SPELL_EXPIRATION);

    public ProvinceIntelActiveSpellBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
