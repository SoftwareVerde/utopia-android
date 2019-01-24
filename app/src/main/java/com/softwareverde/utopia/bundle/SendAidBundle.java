package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class SendAidBundle extends Bundle {
    public static final String BUNDLE_TYPE = "SNDAIDBDL";

    public static class Keys {
        public static final String RESULT_TEXT                = BUNDLE_TYPE +"_RESULT_TEXT";
        public static final String TARGET_PROVINCE_NAME       = BUNDLE_TYPE +"_PROVINCE_NAME";
        public static final String TARGET_PROVINCE_KINGDOM    = BUNDLE_TYPE +"_PROVINCE_KINGDOM";
        public static final String TARGET_PROVINCE_ISLAND     = BUNDLE_TYPE +"_PROVINCE_ISLAND";
        public static final String ADDITIONAL_TRADE_BALANCE   = BUNDLE_TYPE +"_ADDITIONAL_TRADE_BALANCE";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.TARGET_PROVINCE_NAME, Keys.TARGET_PROVINCE_KINGDOM, Keys.TARGET_PROVINCE_ISLAND, Keys.RESULT_TEXT
    );

    public SendAidBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
