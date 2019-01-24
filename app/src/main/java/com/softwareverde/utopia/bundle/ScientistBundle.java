package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ScientistBundle extends Bundle {
    public static final String BUNDLE_TYPE = "SCIENTISTBDL";

    public static class Keys {
        public static final String SCIENTIST_NAME               = BUNDLE_TYPE +"_SCIENTIST_NAME";
        public static final String FORM_NAME                    = BUNDLE_TYPE +"_FORM_NAME";
        public static final String SCIENTIST_LEVEL              = BUNDLE_TYPE +"_SCIENTIST_LEVEL";
        public static final String TICKS_UNTIL_ADVANCEMENT      = BUNDLE_TYPE +"_TICKS_UNTIL_ADVANCEMENT";
        public static final String CURRENT_ASSIGNMENT           = BUNDLE_TYPE +"_CURRENT_ASSIGNMENT";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.SCIENTIST_NAME, Keys.SCIENTIST_LEVEL, Keys.TICKS_UNTIL_ADVANCEMENT, Keys.CURRENT_ASSIGNMENT, Keys.FORM_NAME
    );

    public ScientistBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
