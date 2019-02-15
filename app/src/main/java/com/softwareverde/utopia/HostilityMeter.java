package com.softwareverde.utopia;

import com.softwareverde.utopia.bundle.KingdomBundle;

public class HostilityMeter {
    public enum HostilityLevel {
        NORMAL, UNFRIENDLY, HOSTILE, WAR
    }

    private static Integer getMinValueForHostilityLevel(final HostilityLevel hostilityLevel) {
        switch (hostilityLevel) {
            case NORMAL:        return 0;
            case UNFRIENDLY:    return 50;
            case HOSTILE:       return 150;
            case WAR:           return 300;
        }
        return null;
    }

    private static Integer getMaxValueForHostilityLevel(final HostilityLevel hostilityLevel) {
        switch (hostilityLevel) {
            case NORMAL:        return 49;
            case UNFRIENDLY:    return 149;
            case HOSTILE:       return 299;
            case WAR:           return 400;
        }
        return null;
    }

    private static String getColorForHostilityLevel(final HostilityLevel hostilityLevel) {
        switch (hostilityLevel) {
            case NORMAL:        return "#01DF01";
            case UNFRIENDLY:    return "#FFBF00";
            case HOSTILE:       return "#FF4000";
            case WAR:           return "#FF0000";
        }
        return null;
    }

    private static String getNameForHostilityLevel(final HostilityLevel hostilityLevel) {
        switch (hostilityLevel) {
            case NORMAL:        return "Normal";
            case UNFRIENDLY:    return "Unfriendly";
            case HOSTILE:       return "Hostile";
            case WAR:           return "War";
        }
        return null;
    }

    public static HostilityLevel getHostilityLevelForValue(final Integer value) {
        final Integer normalMaxValue = getMaxValueForHostilityLevel(HostilityLevel.NORMAL);
        final Integer unfriendlyMaxValue = getMaxValueForHostilityLevel(HostilityLevel.UNFRIENDLY);
        final Integer hostileMaxValue = getMaxValueForHostilityLevel(HostilityLevel.HOSTILE);
        final Integer warMaxValue = getMaxValueForHostilityLevel(HostilityLevel.WAR);

        if (value < normalMaxValue) { return HostilityLevel.NORMAL; }
        else if (value < unfriendlyMaxValue) { return HostilityLevel.UNFRIENDLY; }
        else if (value < hostileMaxValue) { return HostilityLevel.HOSTILE; }
        else if (value <= warMaxValue) { return HostilityLevel.WAR; }
        else { return null; }
    }

    public static HostilityMeter fromBundle(final KingdomBundle bundle) {
        if (! bundle.isValid()) { return null; }

        final Boolean bundleHasMeter = (Util.parseInt(bundle.get(KingdomBundle.Keys.HAS_METER)) > 0);
        if (! bundleHasMeter) { return null; }

        final Integer ourValue = Util.parseInt(bundle.get(KingdomBundle.Keys.METER_VALUE));
        final Integer theirValue = Util.parseInt(bundle.get(KingdomBundle.Keys.ENEMY_METER_VALUE));

        return new HostilityMeter(ourValue, theirValue);
    }

    private Integer _meterValue;
    private Integer _enemyMeterValue;

    private HostilityLevel _hostilityLevel;
    private HostilityLevel _enemyHostilityLevel;

    private HostilityMeter(final Integer ourValue, final Integer theirValue) {
        _meterValue = ourValue;
        _enemyMeterValue = theirValue;

        _hostilityLevel = getHostilityLevelForValue(_meterValue);
        _enemyHostilityLevel = getHostilityLevelForValue(_enemyMeterValue);
    }

    public HostilityLevel getHostilityLevel() {
        return getHostilityLevelForValue(_meterValue);
    }

    public HostilityLevel getEnemyHostilityLevel() {
        return getHostilityLevelForValue(_enemyMeterValue);
    }

    public Float getPercentageWithinHostilityLevel() {
        return ((float) _meterValue - getMinValueForHostilityLevel(_hostilityLevel)) / getMaxValueForHostilityLevel(_hostilityLevel);
    }

    public Float getEnemyPercentageWithinHostilityLevel() {
        return ((float) _enemyMeterValue - getMinValueForHostilityLevel(_enemyHostilityLevel)) / getMaxValueForHostilityLevel(_enemyHostilityLevel);
    }

    public String getHostilityColor() { return getColorForHostilityLevel(_hostilityLevel); }
    public String getEnemyHostilityColor() { return getColorForHostilityLevel(_enemyHostilityLevel); }
}
