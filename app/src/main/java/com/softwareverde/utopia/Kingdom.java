package com.softwareverde.utopia;

import com.softwareverde.utopia.bundle.Bundle;
import com.softwareverde.utopia.bundle.FundDragonInfoBundle;
import com.softwareverde.utopia.bundle.KingdomBundle;
import com.softwareverde.utopia.bundle.KingdomProvinceBundle;

import java.util.ArrayList;
import java.util.List;

public class Kingdom {
    public static class Identifier {
        private Integer _kingdomId;
        private Integer _islandId;

        public Integer getKingdomId() {
            return _kingdomId;
        }
        public Integer getIslandId() {
            return _islandId;
        }

        public Identifier(Integer kingdomId, Integer islandId) {
            this._kingdomId = kingdomId;
            this._islandId = islandId;
        }
        public Identifier(Identifier identifier) {
            this._kingdomId = identifier._kingdomId;
            this._islandId = identifier._islandId;
        }

        public void update(Identifier identifier) {
            this._kingdomId = identifier._kingdomId;
            this._islandId = identifier._islandId;
        }
        public void update(Integer kingdomId, Integer islandId) {
            this._kingdomId = kingdomId;
            this._islandId = islandId;
        }

        @Override
        public boolean equals(Object rhs) {
            if (rhs == null) {
                return false;
            }

            if (! (rhs instanceof Identifier)) {
                return false;
            }

            Identifier identifier = (Identifier) rhs;
            return (identifier._kingdomId.equals(this._kingdomId) && identifier._islandId.equals(this._islandId));
        }

        @Override
        public int hashCode() {
            int result = 1;
            result = result * 37 + _kingdomId.hashCode();
            result = result * 37 + _islandId.hashCode();
            return result;
        }

        public Boolean isValid() {
            return (this._islandId > 0 && _kingdomId > 0);
        }

        @Override
        public String toString() {
            return "("+ this._kingdomId +":"+ this._islandId +")";
        }
    }

    public static Kingdom fromBundle(final KingdomBundle bundle) {
        final Kingdom kingdom = new Kingdom();
        if (! bundle.isValid()) {
            return kingdom;
        }

        kingdom._name = bundle.get(KingdomBundle.Keys.NAME);
        kingdom._stance = bundle.get(KingdomBundle.Keys.STANCE);
        kingdom._averageOpponentSize = Util.parseInt(bundle.get(KingdomBundle.Keys.AVERAGE_OPPONENT_SIZE));
        kingdom._warsFought = Util.parseInt(bundle.get(KingdomBundle.Keys.WARS_FOUGHT));
        kingdom._warsWon = Util.parseInt(bundle.get(KingdomBundle.Keys.WARS_WON));
        kingdom._isAtWar = (Util.parseInt(bundle.get(KingdomBundle.Keys.IS_AT_WAR)) > 0);
        if (kingdom._isAtWar) {
            kingdom._warringKingdom = new Identifier(
                Util.parseInt(bundle.get(KingdomBundle.Keys.WARRING_KINGDOM_KINGDOM_ID)),
                Util.parseInt(bundle.get(KingdomBundle.Keys.WARRING_KINGDOM_ISLAND_ID))
            );
        }
        else {
            kingdom._warringKingdom = null;
        }
        kingdom._honor = Util.parseInt(bundle.get(KingdomBundle.Keys.HONOR));
        kingdom._attitudeTowardUs = bundle.get(KingdomBundle.Keys.ATTITUDE_TOWARD_US);
        kingdom._attitudeTowardThem = bundle.get(KingdomBundle.Keys.ATTITUDE_TOWARD_THEM);

        kingdom._identifier = new Identifier(
            Util.parseInt(bundle.get(KingdomBundle.Keys.KINGDOM_ID)),
            Util.parseInt(bundle.get(KingdomBundle.Keys.ISLAND_ID))
        );

        kingdom._provinces.clear();
        if (bundle.hasGroupKey(KingdomBundle.Keys.PROVINCES)) {
            List<Bundle> kingdomProvinceList = bundle.getGroup(KingdomBundle.Keys.PROVINCES);
            for (Integer i = 0; i < kingdomProvinceList.size(); i++) {
                KingdomProvinceBundle provinceBundle = (KingdomProvinceBundle) kingdomProvinceList.get(i);
                if (provinceBundle.isValid()) {
                    Province province = Province.fromKingdomBundle(provinceBundle);
                    province.setKingdomIdentifier(kingdom._identifier);
                    kingdom._provinces.add(province);
                }
            }
        }

        if (Util.parseInt(bundle.get(KingdomBundle.Keys.HAS_METER)) > 0) {
            kingdom._hostilityMeter = HostilityMeter.fromBundle(bundle);
        }

        return kingdom;
    }

    private Identifier _identifier;
    private List<Province> _provinces = new ArrayList<Province>();
    private String _name;
    private String _stance;
    private Integer _averageOpponentSize;
    private Integer _warsFought;
    private Integer _warsWon;
    private Boolean _isAtWar;
    private Identifier _warringKingdom;
    private Integer _honor;
    private String _attitudeTowardUs;
    private String _attitudeTowardThem;
    private Dragon _dragon;
    private Integer _dragonCostRemaining;
    private HostilityMeter _hostilityMeter = null;

    public List<Province> getProvinces() {
        return _provinces;
    }
    public Integer getNetworth() {
        Integer networth = 0;
        for (Province province : _provinces) {
            networth += province.getNetworth();
        }
        return networth;
    }
    public Integer getSize() {
        return _provinces.size();
    }
    public Integer getAcres() {
        Integer acres = 0;
        for (Province province : _provinces) {
            acres += province.getAcres();
        }
        return acres;
    }
    public Identifier getIdentifier() { return _identifier; }
    public Boolean isAtWar() { return _isAtWar; }
    public String getStance() { return _stance; }
    public String getName() { return _name; }
    public Identifier getWarringKingdomIdentifier() { return _warringKingdom; }
    public Boolean hasDragon() { return (_dragon != null && _dragon.isValid()); }
    public Dragon getDragon() { return _dragon; }

    public void setDragonType(Dragon.Type type) {
        if (_dragon == null && type == null) { return; }

        if (_dragon == null || ! _dragon.getType().equals(type)) {
            _dragon = new Dragon(type, this);
        }
        else {
            _dragon = null;
        }
    }
    public void setDragon(Dragon dragon) {
        if (dragon == null || dragon.isValid()) {
            _dragon = dragon;
        }
    }

    public void update(FundDragonInfoBundle bundle) {
        if (bundle.isValid()) {
            _dragonCostRemaining = Util.parseInt(bundle.get(FundDragonInfoBundle.Keys.COST_REMAINING));
        }
    }
    public Integer getDragonCostRemaining() { return _dragonCostRemaining; }

    public String getRelation() { return _attitudeTowardThem; }
    public String getEnemyRelation() { return _attitudeTowardUs; }
    public Boolean hasHostilityMeter() { return (_hostilityMeter != null); }
    public HostilityMeter getHostilityMeter() { return _hostilityMeter; }
}
