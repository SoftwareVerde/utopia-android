package com.softwareverde.utopia;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.bundle.DraftRateBundle;

public class DraftRate {
    public static final DraftRate defaultDraftRate = new DraftRate();

    private Boolean _isSelected = false;
    private String _identifier = "NONE";
    private String _name = "None";
    private Float _rate = 0.0f;
    private Integer _rateCost = 0;

    private DraftRate() { }

    public static DraftRate fromBundle(DraftRateBundle bundle) {
        DraftRate draftRate = new DraftRate();
        if (bundle.isValid()) {
            draftRate._identifier = bundle.get(DraftRateBundle.Keys.IDENTIFIER);
            draftRate._name = bundle.get(DraftRateBundle.Keys.NAME);
            draftRate._rate = Util.parseFloat(bundle.get(DraftRateBundle.Keys.RATE)) / 100.0f;
            draftRate._rateCost = Util.parseInt(bundle.get(DraftRateBundle.Keys.RATE_COST));
            draftRate._isSelected = (Util.parseInt(bundle.get(DraftRateBundle.Keys.IS_SELECTED)) > 0);
        }
        return draftRate;
    }

    public Boolean isSelected() { return _isSelected; }
    public String getIdentifier() { return _identifier; }
    public String getName() { return _name; }
    public Float getRate() { return _rate; }
    public Integer getRateCost() { return _rateCost; }
    public Integer getCost(Integer totalPeasants) {
        return (int) Math.ceil(_rateCost * totalPeasants * _rateCost);
    }
}
