package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class SpellBundle extends Bundle {
    public static final String BUNDLE_TYPE = "SPELLBDL";

    public static class Keys {
        public static final String SPELL_NAME = BUNDLE_TYPE +"_SPELL_NAME";
        public static final String SPELL_COST = BUNDLE_TYPE +"_SPELL_COST";
        public static final String SPELL_IDENTIFIER = BUNDLE_TYPE +"_SPELL_IDENTIFIER";
        public static final String SPELL_TYPE = BUNDLE_TYPE +"_SPELL_TYPE";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.SPELL_NAME, Keys.SPELL_COST, Keys.SPELL_IDENTIFIER, Keys.SPELL_TYPE);

    public SpellBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
