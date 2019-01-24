package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class KingdomBundle extends Bundle {
    public static final String BUNDLE_TYPE = "KNGDMBDL";

    public static class Keys {
        public static final String KINGDOM_ID                 = BUNDLE_TYPE +"_KINGDOM_ID";
        public static final String ISLAND_ID                  = BUNDLE_TYPE +"_ISLAND_ID";
        public static final String NAME                       = BUNDLE_TYPE +"_NAME";
        public static final String IS_AT_WAR                  = BUNDLE_TYPE +"_IS_AT_WAR";
        public static final String WARRING_KINGDOM_KINGDOM_ID = BUNDLE_TYPE +"_IS_AT_WAR_KINGDOM_KINGDOM_ID";
        public static final String WARRING_KINGDOM_ISLAND_ID  = BUNDLE_TYPE +"_IS_AT_WAR_KINGDOM_ISLAND_ID";
        public static final String STANCE                     = BUNDLE_TYPE +"_STANCE";
        public static final String AVERAGE_OPPONENT_SIZE      = BUNDLE_TYPE +"_AVERAGE_OPPONENT_SIZE";
        public static final String WARS_WON                   = BUNDLE_TYPE +"_WARS_WON";
        public static final String WARS_FOUGHT                = BUNDLE_TYPE +"_WARS_FOUGHT";
        public static final String WAR_SCORE                  = BUNDLE_TYPE +"_WAR_SCORE";
        public static final String HONOR                      = BUNDLE_TYPE +"_HONOR";
        public static final String ATTITUDE_TOWARD_US         = BUNDLE_TYPE +"_ATTITUDE_TOWARD_US";
        public static final String ATTITUDE_TOWARD_THEM       = BUNDLE_TYPE +"_ATTITUDE_TOWARD_THEM";
        public static final String PROVINCES                  = BUNDLE_TYPE +"_PROVINCES";

        public static final String HAS_METER                  = BUNDLE_TYPE +"_HAS_METER";
        public static final String METER_VALUE                = BUNDLE_TYPE +"_METER_VALUE";
        public static final String ENEMY_METER_VALUE          = BUNDLE_TYPE +"_ENEMY_METER_VALUE";
        public static final String HOSTILITY_POINTS           = BUNDLE_TYPE +"_HOSTILITY_POINTS";
        public static final String ENEMY_HOSTILITY_POINTS     = BUNDLE_TYPE +"_ENEMY_HOSTILITY_POINTS";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.KINGDOM_ID, Keys.ISLAND_ID, Keys.NAME, Keys.IS_AT_WAR, Keys.STANCE,
        Keys.AVERAGE_OPPONENT_SIZE, Keys.WARS_WON, Keys.WARS_FOUGHT, Keys.HONOR,
        Keys.HAS_METER
        // Keys.ATTITUDE_TOWARD_US, Keys.ATTITUDE_TOWARD_THEM
    );

    public KingdomBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
