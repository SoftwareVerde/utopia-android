package com.softwareverde.utopia.bundle;

import java.util.Arrays;
import java.util.List;

public class TradeSettingsBundle extends Bundle {
    public static final String BUNDLE_TYPE = "TDBALBDL";

    public static final String PERMIT_AID_VALUE = "permit_aid";
    public static final String BLOCKED_AID_VALUE = "block_aid";

    public static class Keys {
        public static final String TRADE_BALANCE  = BUNDLE_TYPE +"_TRADE_BALANCE";
        public static final String TAX_RATE       = BUNDLE_TYPE +"_TAX_RATE";
        public static final String AID_IS_BLOCKED = BUNDLE_TYPE +"_AID_IS_BLOCKED";
    }

    private List<String> _requiredKeys = Arrays.asList(
        Keys.TRADE_BALANCE, Keys.TAX_RATE, Keys.AID_IS_BLOCKED
    );

    public TradeSettingsBundle() {
        super();
        super._requiredKeys = this._requiredKeys = super._appendRequiredKeys(this._requiredKeys);
    }

    @Override
    public String getBundleType() { return BUNDLE_TYPE; }
}
