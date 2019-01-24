package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class ForumTopicPostBundle extends Bundle {
    public static final String BUNDLE_TYPE = "FORMTPCPSTBUN";

    public static class Keys {
        public static final String SEQUENCE_NUMBER  = BUNDLE_TYPE +"_SEQUENCE_NUMBE";
        public static final String POST_DATE        = BUNDLE_TYPE +"_POST_DATE";
        public static final String POSTER           = BUNDLE_TYPE +"_POSTER";
        public static final String CONTENT          = BUNDLE_TYPE +"_CONTENT";
        public static final String TOPIC_ID         = BUNDLE_TYPE +"_TOPIC_ID";
    }

    private List<String> _requiredKeys = Arrays.asList(
            Keys.SEQUENCE_NUMBER, Keys.POST_DATE, Keys.POSTER, Keys.CONTENT, Keys.TOPIC_ID
    );

    public ForumTopicPostBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
