package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class MilitaryBundle extends Bundle {
    public static final String BUNDLE_TYPE = "MILBUN";

    public static class Keys {
        public static final String OFFENSIVE_EFFECTIVENESS    = BUNDLE_TYPE +"_OFFENSIVE_EFFECTIVENESS";
        public static final String OFFENSE_AT_HOME            = BUNDLE_TYPE +"_OFFENSE_AT_HOME";
        public static final String DEFENSIVE_EFFECTIVENESS    = BUNDLE_TYPE +"_DEFENSIVE_EFFECTIVENESS";
        public static final String DEFENSE_AT_HOME            = BUNDLE_TYPE +"_DEFENSE_AT_HOME";

        public static final String GENERALS_HOME              = BUNDLE_TYPE +"_GENERALS_HOME";
        public static final String SOLDIERS_HOME              = BUNDLE_TYPE +"_SOLDIERS_HOME";
        public static final String OFFENSIVE_UNITS_HOME       = BUNDLE_TYPE +"_OFFENSE_UNITS_HOME";
        public static final String DEFENSIVE_UNITS_HOME       = BUNDLE_TYPE +"_DEFENSE_UNITS_HOME";
        public static final String ELITE_UNITS_HOME           = BUNDLE_TYPE +"_ELITES_UNITS_HOME";
        public static final String HORSES_HOME                = BUNDLE_TYPE +"_HORSES_HOME";

        public static final String OFFENSIVE_UNITS_PROGRESS   = BUNDLE_TYPE +"_OFFENSE_UNITS_PROGRESS";
        public static final String DEFENSIVE_UNITS_PROGRESS   = BUNDLE_TYPE +"_DEFENSE_UNITS_PROGRESS";
        public static final String ELITE_UNITS_PROGRESS       = BUNDLE_TYPE +"_ELITES_UNITS_PROGRESS";
        public static final String THIEVES_PROGRESS           = BUNDLE_TYPE +"_THIEVES_PROGRESS";

        public static final String DEPLOYED_ARMIES            = BUNDLE_TYPE +"_DEPLOYED_ARMIES";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.OFFENSIVE_EFFECTIVENESS, Keys.OFFENSE_AT_HOME, Keys.DEFENSIVE_EFFECTIVENESS,
        Keys.DEFENSE_AT_HOME, Keys.GENERALS_HOME, Keys.SOLDIERS_HOME, Keys.OFFENSIVE_UNITS_HOME,
        Keys.DEFENSIVE_UNITS_HOME, Keys.ELITE_UNITS_HOME, Keys.HORSES_HOME,

        Keys.OFFENSIVE_UNITS_PROGRESS, Keys.DEFENSIVE_UNITS_PROGRESS, Keys.ELITE_UNITS_PROGRESS,
        Keys.THIEVES_PROGRESS

        // Keys.DEPLOYED_ARMIES
    );

    public MilitaryBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
