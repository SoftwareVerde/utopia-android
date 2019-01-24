package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ArmyOffenseBundle extends Bundle {
    public static final String BUNDLE_TYPE = "ARMYOFFBDL";

    public static class Keys {
        public static final String TOTAL_OFFENSE = BUNDLE_TYPE +"_TOTAL_OFFENSE";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.TOTAL_OFFENSE);

    @Override
    public Boolean isValid() {
        return super._values.containsKey(Keys.TOTAL_OFFENSE);
    }

    public ArmyOffenseBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
