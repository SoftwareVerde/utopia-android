package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class PrivateMessagesBundle extends Bundle {
    public static final String BUNDLE_TYPE = "PMSGSBDL";

    public static class Keys {
        public static final String PRIVATE_MESSAGES = BUNDLE_TYPE +"_PRIVATE_MESSAGES";
    }

    private List<String> _requiredKeys = Arrays.asList();

    public PrivateMessagesBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
