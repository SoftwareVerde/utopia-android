package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class NewspaperBundle extends Bundle {
    public static final String BUNDLE_TYPE = "NEWSPPRBUN";

    public static class Keys {
        public static final String NEWS_BUNDLE = BUNDLE_TYPE +"_NEWS_BUNDLE";
    }

    private List<String> _requiredKeys = Arrays.asList(); // Not guaranteed to have news items...

    public NewspaperBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
