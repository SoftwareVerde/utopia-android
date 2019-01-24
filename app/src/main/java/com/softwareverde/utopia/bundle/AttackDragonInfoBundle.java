package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class AttackDragonInfoBundle extends Bundle {
    public static final String BUNDLE_TYPE = "ATKDRGNBDL";

    public static class Keys {
        public static final String HEALTH_REMAINING = BUNDLE_TYPE +"_HEALTH_REMINAING";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.HEALTH_REMAINING);

    public AttackDragonInfoBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
