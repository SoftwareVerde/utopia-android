package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ForumTopicPostsBundle extends Bundle {
    public static final String BUNDLE_TYPE = "FORUMTPICBUN";

    public static class Keys {
        public static final String TOPIC_ID = BUNDLE_TYPE +"_TOPIC_ID";
        public static final String POSTS    = BUNDLE_TYPE +"_POSTS";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.TOPIC_ID
    );

    public ForumTopicPostsBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
