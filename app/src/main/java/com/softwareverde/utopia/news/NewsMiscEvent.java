package com.softwareverde.utopia.news;

import com.softwareverde.util.StringUtil;
import com.softwareverde.utopia.Util;
import com.softwareverde.utopia.Province;

public class NewsMiscEvent extends NewsEvent {
    public enum MiscEventType {
        AID, GRANTED_ACRES, PLAGUE_START, PLAGUE_END, OVERPOPULATION, UNPAID_TROOPS, SHADOWLIGHT, NEW_SCIENTIST, UNKNOWN
    }

    public static NewsMiscEvent fromNewsEvent(final Province province, final NewsEvent newsEvent) {
        final NewsMiscEvent newsMiscEvent = new NewsMiscEvent(province, newsEvent.getDate(), newsEvent.getNews());

        if (newsMiscEvent.getMiscType().equals(MiscEventType.UNKNOWN)) {
            return null;
        }

        return newsMiscEvent;
    }

    private MiscEventType _miscEventType = null;
    private Integer _newAcres = null;
    private Integer _desertedMilitary = null;

    private Integer _goldReceived = null;
    private Integer _foodReceived = null;
    private Integer _runesReceived = null;
    private Integer _soldiersReceived = null;
    private Integer _aidTaxRate = null;

    private void _parse() {
        if (_news.contains("Our people decided to explore")) {
            _miscEventType = MiscEventType.GRANTED_ACRES;
            _newAcres = Util.parseInt(StringUtil.pregMatch(".*have settled ([0-9,]+) acres of new land.*", _news).get(0));
            return;
        }

        if (_news.contains("The plague has finally been swept away from our lands!")) {
            _miscEventType = MiscEventType.PLAGUE_END;
            return;
        }

        if (_news.contains("men deserted our military due to housing shortages")) {
            final Integer militaryLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) men deserted our military", _news));
            if (militaryLost != null) {
                _desertedMilitary = Util.coalesce(_desertedMilitary) - militaryLost;
            }

            _miscEventType = MiscEventType.OVERPOPULATION;
            return;
        }
        if (_news.contains("One man deserted our military due to housing shortages!")) {
            _desertedMilitary = Util.coalesce(_desertedMilitary) - 1;

            _miscEventType = MiscEventType.OVERPOPULATION;
            return;
        }

        if (_news.contains("We have received a shipment of")) {
            _goldReceived = _firstItemOrNull(StringUtil.pregMatch(".*shipment of.* ([0-9,]+) gold coin.*", _news));
            _foodReceived = _firstItemOrNull(StringUtil.pregMatch(".*shipment of.* ([0-9,]+) bushel.*", _news));
            _runesReceived = _firstItemOrNull(StringUtil.pregMatch(".*shipment of.* ([0-9,]+) rune.*", _news));
            _soldiersReceived = _firstItemOrNull(StringUtil.pregMatch(".*shipment of.* ([0-9,]+) soldier.*", _news));

            _aidTaxRate = _firstItemOrNull(StringUtil.pregMatch("Trade deficit taxes cost us ([0-9]+)% of the shipment", _news));

            _miscEventType = MiscEventType.AID;
            return;
        }
        if (_news.contains("An aid shipment was sent by") && _news.contains("but did not reach our lands")) {
            _aidTaxRate = _firstItemOrNull(StringUtil.pregMatch("Trade deficit taxes cost us ([0-9]+)% of the shipment", _news));

            _miscEventType = MiscEventType.AID;
            return;
        }

        if (_news.contains("It appears we have contracted The Plague!")) {
            _miscEventType = MiscEventType.PLAGUE_START;
            return;
        }

        if (_news.contains("We were not able to fully pay our military. This may hurt our military effectiveness.")) {
            _miscEventType = MiscEventType.UNPAID_TROOPS;
            return;
        }

        if (_news.contains("Shadowlight has revealed")) {
            _miscEventType = MiscEventType.SHADOWLIGHT;
            return;
        }

        if (_news.contains("A new scientist") && _news.contains("has emerged and has joined our academic ranks.")) {
            // A new scientist, Novice Bagi (Alchemy), has emerged and has joined our academic ranks.
            _miscEventType = MiscEventType.NEW_SCIENTIST;
        }

        _miscEventType = MiscEventType.UNKNOWN;
    }

    public NewsMiscEvent(Province province, String date, String news) {
        super(province, date, news);

        _parse();
    }

    @Override
    public EventType getType() {
        return EventType.MISC;
    }

    public MiscEventType getMiscType() {
        return _miscEventType;
    }

    @Override
    public String toString() {
        return super.toString()
            +" TYPE: "+ _miscEventType
            +" ACRES: "+ _newAcres
            +" DESERT: "+ _desertedMilitary
            +" GOLD: "+ _goldReceived
            +" FOOD: "+ _foodReceived
            +" RUNES: "+ _runesReceived
            +" SOLDIERS: "+ _soldiersReceived
        ;
    }

    @Override
    public Integer getNetAcres() { return Util.coalesce(_newAcres); }

    @Override
    public Integer getNetFood() { return Util.coalesce(_foodReceived); }

    @Override
    public Integer getNetGold() { return Util.coalesce(_goldReceived); }

    @Override
    public Integer getNetRunes() { return Util.coalesce(_runesReceived); }

    @Override
    public Integer getNetSoldiers() {
        final Double percentSoldiers = _province.getSoldiers() / (_province.getMilitaryPopulation() + 0.001D);
        final Integer estimatedSoldiersLost = (int) (Util.coalesce(_desertedMilitary) * percentSoldiers);
        return (_soldiersReceived == null ? estimatedSoldiersLost : _soldiersReceived - estimatedSoldiersLost);
    }

    @Override
    public Integer getNetDefensiveUnits() {
        final Double percentDefensiveUnits = _province.getDefensiveUnits() / (_province.getMilitaryPopulation() + 0.001D);
        return (int) (Util.coalesce(_desertedMilitary) * percentDefensiveUnits);
    }

    @Override
    public Integer getNetOffensiveUnits() {
        final Double percentOffensiveUnits = _province.getOffensiveUnits() / (_province.getMilitaryPopulation() + 0.001D);
        return (int) (Util.coalesce(_desertedMilitary) * percentOffensiveUnits);
    }

    @Override
    public Integer getNetElites() {
        final Double percentElites = _province.getElites() / (_province.getMilitaryPopulation() + 0.001D);
        return (int) (Util.coalesce(_desertedMilitary) * percentElites);
    }

    @Override
    public String getIconName() {
        switch (_miscEventType) {
            case AID:               return "Aid";
            case GRANTED_ACRES:     return "Granted Acres";
            case PLAGUE_START:      return "Plague Start";
            case PLAGUE_END:        return "Plague End";
            case OVERPOPULATION:    return "Overpopulation";
            case NEW_SCIENTIST:     return "New Scientist";
            default:                return null;
        }
    }

    @Override
    public Icon getIcon() {
        switch (_miscEventType) {
            case AID:               return Icon.FIRST_AID_PLUS;
            case GRANTED_ACRES:     return Icon.CROWN;
            case PLAGUE_START:      return Icon.BIO_SYMBOL;
            case PLAGUE_END:        return Icon.NO_BIO_SYMBOL;
            case OVERPOPULATION:    return Icon.NO_HOUSE;
            case NEW_SCIENTIST:     return Icon.SCIENCE;
            default:                return null;
        }
    }

    @Override
    public Boolean isAffliction() {
        return (_miscEventType.equals(MiscEventType.PLAGUE_END) || _miscEventType.equals(MiscEventType.PLAGUE_START));
    }
}
