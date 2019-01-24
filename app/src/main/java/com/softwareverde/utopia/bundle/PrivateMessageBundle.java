package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class PrivateMessageBundle extends Bundle {
    public static final String BUNDLE_TYPE = "PMSGBDL";

    public static class Keys {
        public static final String ID                         = BUNDLE_TYPE +"_MESSAGE_ID";
        public static final String TITLE                      = BUNDLE_TYPE +"_TITLE";
        public static final String SENDER                     = BUNDLE_TYPE +"_PROVINCE_NAME_AND";
        public static final String SENDER_PROVINCE_KINGDOM    = BUNDLE_TYPE +"_PROVINCE_KINGDOM";
        public static final String SENDER_PROVINCE_ISLAND     = BUNDLE_TYPE +"_PROVINCE_ISLAND";
        public static final String UTOPIAN_DATE               = BUNDLE_TYPE +"_UTOPIAN_DATE";
        public static final String CONTENT                    = BUNDLE_TYPE +"_CONTENT";
        public static final String REAL_DATE                  = BUNDLE_TYPE +"_ACTUAL_DATE";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.ID, Keys.TITLE, Keys.SENDER, Keys.SENDER_PROVINCE_KINGDOM, Keys.SENDER_PROVINCE_ISLAND, Keys.UTOPIAN_DATE
    );

    public PrivateMessageBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
