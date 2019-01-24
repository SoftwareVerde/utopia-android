package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class DeployedArmyBundle extends Bundle {
    public static final String BUNDLE_TYPE = "DEPARMY";

    public static class Keys {
        public static final String GENERALS           = BUNDLE_TYPE +"_GENERALS";
        public static final String SOLDIERS           = BUNDLE_TYPE +"_SOLDIERS";
        public static final String OFFENSIVE_UNITS    = BUNDLE_TYPE +"_OFFENSE_UNITS";
        public static final String DEFENSIVE_UNITS    = BUNDLE_TYPE +"_DEFENSE_UNITS";
        public static final String ELITE_UNITS        = BUNDLE_TYPE +"_ELITES_UNITS";
        public static final String HORSES             = BUNDLE_TYPE +"_HORSES";
        public static final String CAPTURED_LAND      = BUNDLE_TYPE +"_CAPTURED_LAND";
        public static final String RETURN_TIME        = BUNDLE_TYPE +"_RETURN_TIME";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.GENERALS, Keys.SOLDIERS, Keys.OFFENSIVE_UNITS, Keys.DEFENSIVE_UNITS, Keys.ELITE_UNITS, Keys.HORSES, Keys.CAPTURED_LAND);

    public DeployedArmyBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
