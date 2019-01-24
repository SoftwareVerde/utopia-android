package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ForumTopicBundle extends Bundle {
    public static final String BUNDLE_TYPE = "FORUMBUN";

    public static class Keys {
        public static final String TITLE        = BUNDLE_TYPE +"_TOPIC_NAME";
        public static final String CREATOR      = BUNDLE_TYPE +"_TOPIC_CREATOR";
        public static final String LAST_POST    = BUNDLE_TYPE +"_TOPIC_LAST_POST";
        public static final String POST_COUNT   = BUNDLE_TYPE +"_TOPIC_NUM_POSTS";
        public static final String ID           = BUNDLE_TYPE +"_TOPIC_ID";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.TITLE, Keys.CREATOR, Keys.LAST_POST, Keys.POST_COUNT
    );

    public ForumTopicBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
