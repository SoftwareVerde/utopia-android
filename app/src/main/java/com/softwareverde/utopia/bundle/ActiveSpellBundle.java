package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ActiveSpellBundle extends Bundle {
    public static final String BUNDLE_TYPE = "ACTIVESPELLBDL";

    public static class Keys {
        public static final String SPELL_NAME             = BUNDLE_TYPE +"_SPELL_NAME";
        public static final String SPELL_EXPIRATION_TIME  = BUNDLE_TYPE +"_SPELL_EXPIRATION";
        public static final String SPELL_DESCRIPTION      = BUNDLE_TYPE +"_SPELL_DESCRIPTION";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.SPELL_NAME, Keys.SPELL_EXPIRATION_TIME, Keys.SPELL_DESCRIPTION);

    public ActiveSpellBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}