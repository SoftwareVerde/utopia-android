package com.softwareverde.utopia.news;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.Province;

public class NewsSpellEvent extends NewsEvent {
    public enum Spell {
        STORMS, DROUGHTS, VERMIN, GLUTTONY, EXPOSE_THIEVES, GREED, FOOLS_GOLD,
        PITFALLS, FIREBALL, CHASTITY, LIGHTNING_STRIKE, EXPLOSIONS,
        AMNESIA, NIGHTMARES, MYSTIC_VORTEX, METEOR_SHOWERS, TORNADOES,
        LAND_LUST, FAILED, UNKNOWN
    }

    public static NewsSpellEvent fromNewsEvent(final Province province, final NewsEvent newsEvent) {
        final NewsSpellEvent newsSpellEvent = new NewsSpellEvent(province, newsEvent.getDate(), newsEvent.getNews());

        if (newsSpellEvent.getSpell().equals(Spell.UNKNOWN)) {
            return null;
        }
        return newsSpellEvent;
    }

    private Spell _spell;
    private Integer _dayDuration = null;
    private Boolean _isAffliction = false;
    private Integer _peasants = null;
    private Integer _soldiers = null;
    private Integer _offensiveUnits = null;
    private Integer _defensiveUnits = null;
    private Integer _elites = null;
    private Integer _gold = null;
    private Integer _runes = null;
    private Boolean _wasReflected = false;
    private Integer _acres = null;

    private void _parseUnitsLost(final String news) {
        final Province.Race race = _province.getRace();

        final Integer peasantsLost          = _firstItemOrNull(Util.pregMatch(".*and kill.* ([0-9,]+) peasant.*", news));
        final Integer soldiersLost          = _firstItemOrNull(Util.pregMatch(".*and kill.* ([0-9,]+) soldier.*", news));
        final Integer offensiveUnitsLost    = _firstItemOrNull(Util.pregMatch(".*and kill.* ([0-9,]+) "+ Province.getOffensiveUnitKeyword(race) +".*", news));
        final Integer defensiveUnitsLost    = _firstItemOrNull(Util.pregMatch(".*and kill.* ([0-9,]+) "+ Province.getDefensiveUnitKeyword(race) +".*", news));
        final Integer elitesLost            = _firstItemOrNull(Util.pregMatch(".*and kill.* ([0-9,]+) "+ Province.getEliteKeyword(race) +".*", news));

        if (peasantsLost != null) {
            _peasants = Util.coalesce(_peasants) - peasantsLost;
        }

        if (soldiersLost != null) {
            _soldiers = Util.coalesce(_soldiers) - soldiersLost;
        }

        if (offensiveUnitsLost != null) {
            _offensiveUnits = Util.coalesce(_offensiveUnits) - offensiveUnitsLost;
        }

        if (defensiveUnitsLost != null) {
            _defensiveUnits = Util.coalesce(_defensiveUnits) - defensiveUnitsLost;
        }

        if (elitesLost != null) {
            _elites = Util.coalesce(_elites) - elitesLost;
        }
    }

    private void _parse() {
        _dayDuration = _firstItemOrNull(Util.pregMatch("for ([0-9]+) day", _news));

        if (_news.contains("attempted against us was reflected upon the sender")) {
            final String spell = Util.pregMatch("A ([a-zA-Z ]+) Spell.*", _news).get(0);

            switch (spell) {
                case "Storms":              { _spell = Spell.STORMS; }              break;
                case "Droughts":            { _spell = Spell.DROUGHTS; }            break;
                case "Vermin":              { _spell = Spell.VERMIN; }              break;
                case "Glutony":             { _spell = Spell.GLUTTONY; }             break;
                case "Expose Thieves":      { _spell = Spell.EXPOSE_THIEVES; }      break;
                case "Greed":               { _spell = Spell.GREED; }               break;
                case "Fools Gold":          { _spell = Spell.FOOLS_GOLD; }          break;
                case "Pitfalls":            { _spell = Spell.PITFALLS; }            break;
                case "Fireball":            { _spell = Spell.FIREBALL; }            break;
                case "Chastity":            { _spell = Spell.CHASTITY; }            break;
                case "Lightning Strike":    { _spell = Spell.LIGHTNING_STRIKE; }    break;
                case "Explosions":          { _spell = Spell.EXPLOSIONS; }          break;
                case "Amnesia":             { _spell = Spell.AMNESIA; }             break;
                case "Nightmares":          { _spell = Spell.NIGHTMARES; }          break;
                case "Mystic Vortex":       { _spell = Spell.MYSTIC_VORTEX; }       break;
                case "Meteor Showers":      { _spell = Spell.METEOR_SHOWERS; }      break;
                case "Tornadoes":           { _spell = Spell.TORNADOES; }           break;
                case "Land Lust":           { _spell = Spell.LAND_LUST; }           break;
                default:                    { _spell = Spell.UNKNOWN; }             break;
            }

            // _isAffliction = true;
            _wasReflected = true;

            return;
        }

        // Storms
        if (_news.contains("Storms are ravaging our lands!")) {
            _spell = Spell.STORMS;
            _isAffliction = true;
            return;
        }

        // Droughts
        if (_news.contains("Droughts are reducing our daily harvests")) {
            _spell = Spell.DROUGHTS;
            _isAffliction = true;
            return;
        }

        // Vermin
        if (_news.contains("Vermin have been discovered")) {
            _spell = Spell.VERMIN;
            _isAffliction = true;
            return;
        }

        // Gluttony
        if (_news.contains("A fit of gluttony has descended upon our people")) {
            _spell = Spell.GLUTTONY;
            _isAffliction = true;
            return;
        }

        // Expose Thieves
        if (_news.contains("Many of our thieves have been exposed by magic! They will be less effective until they recover.")) {
            _spell = Spell.EXPOSE_THIEVES;
            _isAffliction = true;
            return;
        }

        // Greed
        if (_news.contains("Enemies have convinced our soldiers to demand more money")) {
            _spell = Spell.GREED;
            _isAffliction = true;
            return;
        }

        // Fools Gold
        if (_news.contains("gold coins have been turned into worthless lead")) {
            _spell = Spell.FOOLS_GOLD;

            final Integer goldLost = _firstItemOrNull(Util.pregMatch("([0-9,]+) gold coins have been", _news));
            if (goldLost != null) {
                _gold = Util.coalesce(_gold) - goldLost;
            }

            return;
        }

        // Pitfalls
        if (_news.contains("Pitfalls are haunting our lands")) {
            _spell = Spell.PITFALLS;
            _isAffliction = true;
            return;
        }

        // Fireball
        if (_news.contains("A massive fireball crashed into our lands")) {
            _spell = Spell.FIREBALL;

            final Integer peasantsLost = _firstItemOrNull(Util.pregMatch("A massive fireball crashed into our lands and killed ([0-9,]+) peasants!", _news));
            if (peasantsLost != null) {
                _peasants = Util.coalesce(_peasants) - peasantsLost;
            }

            return;
        }

        // Chastity
        if (_news.contains("vow of chastity")) {
            _spell = Spell.CHASTITY;
            _isAffliction = true;
            return;
        }

        // Lightning Strike
        if (_news.contains("A sudden lightning storm struck ")) {
            _spell = Spell.LIGHTNING_STRIKE;

            final Integer runesLost = _firstItemOrNull(Util.pregMatch("destroyed ([0-9,]+) runes", _news));
            if (runesLost != null) {
                _runes = Util.coalesce(_runes) - runesLost;
            }

            return;
        }

        // Explosions
        if (_news.contains("Explosions are hampering aid")) {
            _spell = Spell.EXPLOSIONS;
            _isAffliction = true;
            return;
        }

        // Amnesia
        if (_news.contains("Amnesia swept over our province and caused us to temporarily")) {
            _spell = Spell.AMNESIA;
            return;
        }

        // Nightmares
        if (_news.contains("of our men from our armies and thieves' guild turned up unfit")) {
            _spell = Spell.NIGHTMARES;
            return;
        }

        // Mystic Vortex
        if (_news.contains("A magic vortex rendered many of our spells inactive")) {
            _spell = Spell.MYSTIC_VORTEX;
            _isAffliction = true;
            return;
        }
        // A magic vortex encircled our lands, and rendered 3 of our spells (Nature's Blessing, Pitfalls and Greed) inactive!
        if (_news.contains("A magic vortex encircled our lands")) {
            _spell = Spell.MYSTIC_VORTEX;
            _isAffliction = true;
            return;
        }

        // Meteor Showers
        if (_news.contains("Meteors rain across")) {
            _parseUnitsLost(_news);

            _spell = Spell.METEOR_SHOWERS;

            if (_dayDuration != null) {
                _isAffliction = true;
            }

            return;
        }

        // Tornadoes
        if (_news.contains("Tornadoes scour the lands")) {
            _spell = Spell.TORNADOES;
            return;
        }

        // Land Lust
        if (_news.contains("acre. of land have disappeared from our control")) {
            final Integer acresLost = _firstItemOrNull(Util.pregMatch("([0-9,]+) acre. of land", _news));
            if (acresLost != null) {
                _acres = Util.coalesce(_acres) - acresLost;
            }

            _spell = Spell.LAND_LUST;
            return;
        }

        // Failed Spell
        if (_news.contains("Our mages noticed a possible spell attempt by")) {
            _spell = Spell.FAILED;
            _isAffliction = true;
            return;
        }

        _spell = Spell.UNKNOWN;
    }

    public NewsSpellEvent(Province province, String date, String news) {
        super(province, date, news);

        _parse();
    }

    @Override
    public EventType getType() {
        return EventType.MAGIC_OP;
    }

    public Spell getSpell() {
        return _spell;
    }

    @Override
    public String toString() {
        return super.toString()
            +" SPELL:"+ _spell
            +" DURATION:"+ _dayDuration
            +" PEASANTS:"+ _peasants
            +" SOLDIERS:"+ _soldiers
            +" DEFUNITS:"+ _defensiveUnits
            +" OFFUNITS:"+ _offensiveUnits
            +" ELITES:"+ _elites
            +" PEASANTS:"+ _peasants
            +" RUNES:"+ _runes
            +" GOLD:"+ _gold
            +" REFLECTED:"+ _wasReflected
        ;
    }


    @Override
    public Integer getNetAcres() { return Util.coalesce(_acres); }

    @Override
    public Integer getNetGold() { return Util.coalesce(_gold); }

    @Override
    public Integer getNetRunes() { return Util.coalesce(_runes); }

    @Override
    public Integer getNetPeasants() { return Util.coalesce(_peasants); }

    @Override
    public Integer getNetSoldiers() { return Util.coalesce(_soldiers); }

    @Override
    public Integer getNetDefensiveUnits() { return Util.coalesce(_defensiveUnits); }

    @Override
    public Integer getNetOffensiveUnits() { return Util.coalesce(_offensiveUnits); }

    @Override
    public Integer getNetElites() { return Util.coalesce(_elites); }

    @Override
    public String getIconName() {
        switch (_spell) {
            case STORMS:            return "Storms";
            case DROUGHTS:          return "Droughts";
            case VERMIN:            return "Vermin";
            case GLUTTONY:          return "Gluttony";
            case EXPOSE_THIEVES:    return "Expose Thieves";
            case GREED:             return "Greed";
            case FOOLS_GOLD:        return "Fools Gold";
            case PITFALLS:          return "Pitfalls";
            case FIREBALL:          return "Fireball";
            case CHASTITY:          return "Chastity";
            case LIGHTNING_STRIKE:  return "Lightning Strike";
            case EXPLOSIONS:        return "Explosions";
            case AMNESIA:           return "Amnesia";
            case NIGHTMARES:        return "Nightmares";
            case MYSTIC_VORTEX:     return "Mystic Vortex";
            case METEOR_SHOWERS:    return "Meteor Showers";
            case TORNADOES:         return "Tornadoes";
            case LAND_LUST:         return "Land Lust";
            default:                return null;
        }
    }

    @Override
    public Icon getIcon() {
        switch (_spell) {
            case STORMS:            return Icon.TORNADO;
            case DROUGHTS:          return Icon.DESERT;
            case VERMIN:            return Icon.GREEN_RAT;
            case GLUTTONY:          return Icon.BREAD;
            case EXPOSE_THIEVES:    return Icon.SPOTLIGHT;
            case GREED:             return Icon.GOLD_COINS;
            case FOOLS_GOLD:        return Icon.FOOLS_GOLD;
            case PITFALLS:          return Icon.PIT;
            case FIREBALL:          return Icon.FIREBALL;
            case CHASTITY:          return Icon.NUN;
            case LIGHTNING_STRIKE:  return Icon.LIGHTNING_BOLT;
            case EXPLOSIONS:        return Icon.EXPLOSIONS;
            case AMNESIA:           return Icon.QUESTIONMARK_HEAD;
            case NIGHTMARES:        return Icon.NIGHTMARES;
            case MYSTIC_VORTEX:     return Icon.VORTEX;
            case METEOR_SHOWERS:    return Icon.METEOR;
            case TORNADOES:         return Icon.TORNADO2;
            case LAND_LUST:         return Icon.KISS;
            default:                return null;
        }
    }

    @Override
    public Integer getDuration() { return _dayDuration; }

    @Override
    public Boolean isAffliction() { return _isAffliction; }
}
