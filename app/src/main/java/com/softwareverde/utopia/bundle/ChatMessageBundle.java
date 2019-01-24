package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ChatMessageBundle extends Bundle {
    public static final String BUNDLE_TYPE = "CHATMSGBDL";

    public static class Keys {
        public static final String ID             = BUNDLE_TYPE +"_ID";
        public static final String TIMESTAMP      = BUNDLE_TYPE +"_TIMESTAMP";
        public static final String DISPLAY_NAME   = BUNDLE_TYPE +"_DISPLAY_NAME";
        public static final String MESSAGE        = BUNDLE_TYPE +"_MESSAGE";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.ID, Keys.TIMESTAMP, Keys.DISPLAY_NAME, Keys.MESSAGE);

    // NOTE: Chat private_messages cannot be parsed for resources...
    @Override
    public Boolean isValid() {
        return true;
    }

    public ChatMessageBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
