package com.softwareverde.utopia.news;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.Province;

public class NewsAttackEvent extends NewsEvent {
    public enum AttackType {
        AMBUSH, TRADITIONAL_MARCH, CONQUEST, PLUNDER, MASSACRE, RAZE, LEARN, FAILED, UNKNOWN
    }

    public static NewsAttackEvent fromNewsEvent(final Province province, final NewsEvent newsEvent) {
        final NewsAttackEvent newsAttackEvent = new NewsAttackEvent(province, newsEvent.getDate(), newsEvent.getNews());

        if (newsAttackEvent.getAttackType().equals(AttackType.UNKNOWN)) {
            return null;
        }
        return newsAttackEvent;
    }

    private AttackType _attackType = null;
    private Integer _acres = null;

    private Integer _peasants = null;
    private Integer _soldiers = null;
    private Integer _defensiveUnits = null;
    private Integer _offensiveUnits = null;
    private Integer _elites = null;
    private Integer _soldiersReawakened = null;
    private Boolean _contractedPlague = false;
    private Integer _gold = null;

    private Integer _buildings = null;
    private Integer _science = null;
    private Integer _food = null;
    private Integer _runes = null;

    private void _parseUnitsLost(final String news) {
        final Province.Race race = _province.getRace();

        final Integer soldiersLost = _firstItemOrNull(Util.pregMatch(".*We lost.* ([0-9,]+) soldier.*", news));
        final Integer offensiveUnitsLost = _firstItemOrNull(Util.pregMatch(".*We lost.* ([0-9,]+) "+ Province.getOffensiveUnitKeyword(race) +".*", news));
        final Integer defensiveUnitsLost = _firstItemOrNull(Util.pregMatch(".*We lost.* ([0-9,]+) "+ Province.getDefensiveUnitKeyword(race) +".*", news));
        final Integer elitesLost = _firstItemOrNull(Util.pregMatch(".*We lost.* ([0-9,]+) "+ Province.getEliteKeyword(race) +".*", news));

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
        if (_news.contains("It appears we have contracted The Plague!")) {
            _contractedPlague = true;
        }

        // Traditional March:
        if (_news.contains("came through and ravaged our lands! They captured")) {
            final Integer acresLost = _firstItemOrNull(Util.pregMatch("They captured ([0-9,]+) acres", _news));
            if (acresLost != null) {
                _acres = Util.coalesce(_acres) - acresLost;
            }

            _parseUnitsLost(_news);

            _attackType = AttackType.TRADITIONAL_MARCH;
            return;
        }

        // Conquest:
        if (_news.contains("came through and ravaged our lands! They were able to capture")) {
            final Integer acresLost= _firstItemOrNull(Util.pregMatch("They were able to capture ([0-9,]+) acres before we could turn them away", _news));
            if (acresLost != null) {
                _acres = Util.coalesce(_acres) - acresLost;
            }

            _parseUnitsLost(_news);

            _attackType = AttackType.CONQUEST;
            return;
        }

        // Plunder:
        if (_news.contains("came through and ravaged our lands! They looted")) {
            _gold = _firstItemOrNull(Util.pregMatch("They looted.* ([0-9,]+) gold coin", _news));
            _food = _firstItemOrNull(Util.pregMatch("They looted.* ([0-9,]+) bushel", _news));
            _runes = _firstItemOrNull(Util.pregMatch("They looted.* ([0-9,]+) rune", _news));

            _parseUnitsLost(_news);

            _attackType = AttackType.PLUNDER;
            return;
        }

        // Ambush:
        if (_news.contains("ambushed one of our armies")) {
            final Integer acresLost = _firstItemOrNull(Util.pregMatch("They recaptured ([0-9,]+) acres", _news));
            if (acresLost != null) {
                _acres = Util.coalesce(_acres) - acresLost;
            }

            _parseUnitsLost(_news);

            _attackType = AttackType.AMBUSH;
            return;
        }

        // Raze:
        if (_news.contains("Their armies destroyed")) {
            final Integer acresLost = _firstItemOrNull(Util.pregMatch("Their armies destroyed ([0-9,]+) acre. of our land!", _news));
            if (acresLost != null) {
                _acres = Util.coalesce(_acres) - acresLost;
            }

            _parseUnitsLost(_news);

            _attackType = AttackType.RAZE;
            return;
        }
        if (_news.contains("Their armies razed")) {
            final Integer buildingsLost = _firstItemOrNull(Util.pregMatch("Their armies razed ([0-9,]+) acre. of buildings!", _news));
            if (buildingsLost != null) {
                _buildings = Util.coalesce(_buildings) - buildingsLost;
            }

            _parseUnitsLost(_news);

            _attackType = AttackType.RAZE;
            return;
        }

        // Learn:
        if (_news.contains("books of knowledge in the Arts & Sciences!")) {
            final Integer scienceLost = _firstItemOrNull(Util.pregMatch("They stole ([0-9,]+) book. of knowledge in the Arts & Sciences!", _news));
            if (scienceLost != null) {
                _science = Util.coalesce(_science) - scienceLost;
            }

            _parseUnitsLost(_news);

            _attackType = AttackType.LEARN;
            return;
        }

        // Failed:
        if (_news.contains("attempted to attack us, but failed miserably")) {
            _parseUnitsLost(_news);

            _attackType = AttackType.FAILED;
            return;
        }

        // Massacre:
        // Forces from HitGuyUnderMe (1:7) came through and ravaged our lands! Their armies killed 1158 of our peasants, thieves, and wizards! We lost 49 soldiers, 423 Druids and 347 Beastmasters in this battle.
        if (_news.contains("Their armies killed ")) {
            final Integer peasantsThievesWizardsKilled = _firstItemOrNull(Util.pregMatch("Their armies killed ([0-9,]+) of our peasants, thieves, and wizards!", _news));
            if (peasantsThievesWizardsKilled != null) {
                _peasants = Util.coalesce(_peasants) - peasantsThievesWizardsKilled;
            }

            _parseUnitsLost(_news);

            _attackType = AttackType.MASSACRE;
            return;
        }

        _attackType = AttackType.UNKNOWN;
    }

    public NewsAttackEvent(Province province, String date, String news) {
        super(province, date, news);

        _parse();
    }

    @Override
    public EventType getType() {
        return EventType.ATTACK;
    }

    @Override
    public String toString() {
        return super.toString()
            +" TYPE: "+ _attackType
            +" ACRES:"+ _acres
            +" SOLDIERS:"+ _soldiers
            +" DEFUNITS:"+ _defensiveUnits
            +" OFFUNITS:"+ _offensiveUnits
            +" ELITES:"+ _elites
            +" PEASANTS:"+ _peasants
            +" PLAGUE:"+ _contractedPlague
            +" GOLD:"+ _gold
            +" FOOD:"+ _food
            +" RUNES:"+ _runes
        ;
    }

    public AttackType getAttackType() {
        return _attackType;
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
        switch (_attackType) {
            case AMBUSH:            return "Ambush";
            case TRADITIONAL_MARCH: return "Traditional March";
            case CONQUEST:          return "Conquest";
            case PLUNDER:           return "Plunder";
            case MASSACRE:          return "Massacre";
            case RAZE:              return "Raze";
            default:                return null;
        }
    }

    @Override
    public Icon getIcon() {
        switch (_attackType) {
            case AMBUSH:            return Icon.CROSSED_SWORDS;
            case TRADITIONAL_MARCH: return Icon.TOWER_AND_SWORDS;
            case CONQUEST:          return Icon.ROMAN_HELMET;
            case PLUNDER:           return Icon.SWORDS_AND_GOLD;
            case MASSACRE:          return Icon.SWORDS_AND_BLOOD;
            case RAZE:              return Icon.TOWER_AND_FIRE;
            default:                return null;
        }
    }
}
