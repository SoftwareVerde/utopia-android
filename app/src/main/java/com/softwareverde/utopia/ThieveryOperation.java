package com.softwareverde.utopia;

import com.softwareverde.utopia.bundle.AvailableThieveryOperationBundle;

public class ThieveryOperation {
    private String _identifier;
    private String _name;

    public static class Identifiers {
        public static final String INFILTRATE = "INFILTRATE";
        public static final String SNATCH_NEWS = "SNATCH_NEWS";
        public static final String SPY_ON_MILITARY = "SPY_ON_MILITARY";
        public static final String SPY_ON_SCIENCE = "SPY_ON_SCIENCES";
        public static final String SPY_ON_THRONE = "SPY_ON_THRONE";
        public static final String SURVEY = "SURVEY";
        public static final String GREATER_ARSON = "GREATER_ARSON";
    }

    public static ThieveryOperation fromBundle(AvailableThieveryOperationBundle bundle) {
        String identifier = bundle.get(AvailableThieveryOperationBundle.Keys.IDENTIFIER);
        String name = bundle.get(AvailableThieveryOperationBundle.Keys.NAME);

        return new ThieveryOperation(identifier, name);
    }

    public ThieveryOperation(String identifier, String name) {
        _identifier = identifier;
        _name = name;
    }

    public String getIdentifier() {
        return _identifier;
    }
    public String getName() {
        return _name;
    }
}
