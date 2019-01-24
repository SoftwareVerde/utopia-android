package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ThieveryOperationBundle extends Bundle {
    public static final String BUNDLE_TYPE = "THIEVOPBDL";

    public static class Keys {
        public static final String TARGET_PROVINCE_NAME       = BUNDLE_TYPE +"_TARGET_PROVINCE_NAME";
        public static final String TARGET_KINGDOM             = BUNDLE_TYPE +"_TARGET_KINGDOM";
        public static final String TARGET_ISLAND              = BUNDLE_TYPE +"_TARGET_ISLAND";
        public static final String THIEVES_SENT               = BUNDLE_TYPE +"_QUANTITY";
        public static final String OPERATION_IDENTIFIER       = BUNDLE_TYPE +"_OPERATION";

        public static final String WAS_SUCCESS                = BUNDLE_TYPE +"_WAS_SUCCESS";
        public static final String THIEVES_LOST               = BUNDLE_TYPE +"_THIEVES_LOST";
        public static final String RESULT_TEXT                = BUNDLE_TYPE +"_RESULT_TEXT";

        public static final String TARGET_PROVINCE_BUNDLE     = BUNDLE_TYPE +"_TARGET_PROVINCE_BUNDLE";
        public static final String TARGET_SURVEY_BUNDLE       = BUNDLE_TYPE +"_TARGET_SURVEY_BUNDLE";
        public static final String TARGET_MILITARY_BUNDLE     = BUNDLE_TYPE +"_TARGET_MILITARY_BUNDLE";
        public static final String TARGET_INFILTRATE_BUNDLE   = BUNDLE_TYPE +"_TARGET_INFILTRATE_BUNDLE";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.WAS_SUCCESS, Keys.RESULT_TEXT, Keys.OPERATION_IDENTIFIER, Keys.TARGET_PROVINCE_NAME, Keys.TARGET_KINGDOM, Keys.TARGET_ISLAND);

    public ThieveryOperationBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
