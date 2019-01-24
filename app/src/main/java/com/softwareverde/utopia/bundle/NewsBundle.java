package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class NewsBundle extends Bundle {
    public static final String BUNDLE_TYPE = "NEWSBUN";

    public static class Keys {
        public static final String NEWS = BUNDLE_TYPE +"_NEWS";
        public static final String DATE = BUNDLE_TYPE +"_DATE";
        public static final String TYPE = BUNDLE_TYPE +"_TYPE";
    }

    private List<String> _requiredKeys = Arrays.asList(Keys.NEWS, Keys.DATE);

    public NewsBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
