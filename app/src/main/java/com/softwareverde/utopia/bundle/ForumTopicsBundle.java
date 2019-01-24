package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ForumTopicsBundle extends Bundle {
    public static final String BUNDLE_TYPE = "FORUMBUN";

    public static class Keys {
        public static final String TOPICS = BUNDLE_TYPE +"_TOPICS";
    }

    private List<String> _requiredKeys = Arrays.asList();

    public ForumTopicsBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
