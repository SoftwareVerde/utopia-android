package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class SpellResultBundle extends Bundle {
    public static final String BUNDLE_TYPE = "SPLRSLTBDL";

    public static class Keys {
        public static final String WAS_SUCCESS            = BUNDLE_TYPE +"_WAS_SUCCESS";
        public static final String WIZARDS_LOST           = BUNDLE_TYPE +"_WIZARDS_LOST";
        public static final String RESULT_TEXT            = BUNDLE_TYPE +"_RESULT_TEXT";

        public static final String TARGET_PROVINCE_NAME   = BUNDLE_TYPE +"_TARGET_PROVINCE_NAME";
        public static final String TARGET_KINGDOM         = BUNDLE_TYPE +"_TARGET_KINGDOM";
        public static final String TARGET_ISLAND          = BUNDLE_TYPE +"_TARGET_ISLAND";
        public static final String SPELL_IDENTIFIER       = BUNDLE_TYPE +"_SPELL_IDENTIFIER";

        public static final String IS_DEFENSIVE_SPELL     = BUNDLE_TYPE +"_IS_DEFENSIVE_SPELL";

        public static final String SPELL_DURATION         = BUNDLE_TYPE +"_SPELL_DURATION";
        public static final String SPELL_EXPIRATION_TIME  = BUNDLE_TYPE +"_SPELL_EXPIRATION_TIME";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.WAS_SUCCESS, Keys.RESULT_TEXT, Keys.SPELL_IDENTIFIER, /* Keys.SPELL_DURATION, */
        /* Keys.SPELL_EXPIRATION_TIME, */ Keys.TARGET_PROVINCE_NAME, Keys.TARGET_KINGDOM, Keys.TARGET_ISLAND
    );

    public SpellResultBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
