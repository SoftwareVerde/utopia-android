package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ChatroomBundle extends Bundle {
    public static final String BUNDLE_TYPE = "CHATRMBDL";

    public static class Keys {
        public static final String MESSAGES = BUNDLE_TYPE +"_MESSAGES";
    }

    private List<String> _requiredKeys = Arrays.asList();

    // NOTE: Chat private_messages cannot be parsed for resources...
    @Override
    public Boolean isValid() {
        return true;
    }

    public ChatroomBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
