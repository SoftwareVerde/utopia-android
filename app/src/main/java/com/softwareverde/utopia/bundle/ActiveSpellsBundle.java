package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ActiveSpellsBundle extends Bundle {
    public static final String BUNDLE_TYPE = "ACTIVESPELLSBDL";

    public static class Keys {
        public static final String ACTIVE_SPELLS = BUNDLE_TYPE +"_ACTIVE_SPELLS";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.ACTIVE_SPELLS);

    public ActiveSpellsBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
