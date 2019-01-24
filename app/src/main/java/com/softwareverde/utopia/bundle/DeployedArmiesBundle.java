package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class DeployedArmiesBundle extends Bundle {
    public static final String BUNDLE_TYPE = "DEPARMIESBDL";

    public static class Keys {
        public static final String DEPLOYED_ARMIES_GROUP = BUNDLE_TYPE +"_DEPLOYED_ARMIES_GROUP";
    }

    private List<String> _requiredKeys = Arrays.asList();

    public DeployedArmiesBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}

