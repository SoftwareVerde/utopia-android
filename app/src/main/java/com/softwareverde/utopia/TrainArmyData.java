package com.softwareverde.utopia;

public class TrainArmyData {
    private Province.Race _race = null;
    private DraftRate _draftRate = DraftRate.defaultDraftRate;
    private Integer _draftTarget = 75;
    private Integer _wageRate = 200;
    private Integer _soldierCount = 0;
    private Integer _offensiveUnitCount = 0;
    private Integer _defensiveUnitCount = 0;
    private Integer _eliteCount = 0;
    private Integer _thiefCount = 0;

    public Province.Race getRace() { return _race; }
    public DraftRate getDraftRate() { return _draftRate; }
    public Integer getDraftTarget() { return _draftTarget; }
    public Integer getWageRate() { return _wageRate; }
    public Integer getSoldierCount() { return _soldierCount; }
    public Integer getOffensiveUnitCount() { return _offensiveUnitCount; }
    public Integer getDefensiveUnitCount() { return _defensiveUnitCount; }
    public Integer getEliteCount() { return _eliteCount; }
    public Integer getThiefCount() { return _thiefCount; }

    public TrainArmyData(Province.Race race) {
        _race = race;
    }

    public void setDraftRate(DraftRate draftRate) { _draftRate = draftRate; }
    public void setDraftTarget(Integer draftTarget) {
        if (draftTarget > 100) {
            draftTarget = 100;
        }
        if (draftTarget < 0) {
            draftTarget = 0;
        }

        _draftTarget = draftTarget;
    }
    public void setWageRate(Integer wageRate) {
        if (wageRate > 200) {
            wageRate = 200;
        }
        if (wageRate < 50) {
            wageRate = 50;
        }

        _wageRate = wageRate;
    }

    public void setSoldierCount(Integer count) { _soldierCount = count; }
    public void setOffensiveUnitCount(Integer count) { _offensiveUnitCount = count; }
    public void setDefensiveUnitCount(Integer count) { _defensiveUnitCount = count; }
    public void setEliteCount(Integer count) { _eliteCount = count; }
    public void setThiefCount(Integer count) { _thiefCount = count; }
}
