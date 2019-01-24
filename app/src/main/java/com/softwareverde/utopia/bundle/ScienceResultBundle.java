package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ScienceResultBundle extends Bundle {
    public static final String BUNDLE_TYPE = "SCIRESBUN";

    public static class Keys {
        public static final String WAS_SUCCESS  = BUNDLE_TYPE +"_WAS_SUCCESS";
        public static final String RESULT_TEXT  = BUNDLE_TYPE +"_RESULT_TEXT";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.WAS_SUCCESS, Keys.RESULT_TEXT);

    public ScienceResultBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
