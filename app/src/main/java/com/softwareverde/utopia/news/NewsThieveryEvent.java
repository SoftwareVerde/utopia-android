package com.softwareverde.utopia.news;

import com.softwareverde.util.StringUtil;
import com.softwareverde.utopia.Util;
import com.softwareverde.utopia.Province;

public class NewsThieveryEvent extends NewsEvent {
    public enum ThieveryOperation {
        SABOTAGE_WIZARDS, ROB_GRANARIES, ROB_VAULTS, ROB_TOWERS,
        KIDNAP, ARSON, NIGHT_STRIKE, RIOTS, STEAL_HORSES, BRIBE_THIEVES,
        BRIBE_GENERALS, FREE_PRISONERS, ASSASSINATE_WIZARDS, PROPAGANDA,
        FAILED, UNKNOWN
    }

    public static NewsThieveryEvent fromNewsEvent(final Province province, final NewsEvent newsEvent) {
        final NewsThieveryEvent newsThieveryEvent = new NewsThieveryEvent(province, newsEvent.getDate(), newsEvent.getNews());

        if (newsThieveryEvent.getThieveryOperation().equals(ThieveryOperation.UNKNOWN)) {
            return null;
        }
        return newsThieveryEvent;
    }

    private ThieveryOperation _thieveryOperation = null;
    private Integer _dayDuration = null;
    private Boolean _isAffliction = false;

    private Integer _peasants = null;
    private Integer _military = null;
    private Integer _gold = null;
    private Integer _food = null;
    private Integer _runes = null;
    private Integer _horses = null;
    private Integer _wizards = null;
    private Integer _thieves = null;
    private Integer _soldiers = null;
    private Integer _specialists = null;
    private Integer _elites = null;
    private Integer _buildings = null;
    private Integer _prisoners = null;

    private void _parse() {
        _dayDuration = _firstItemOrNull(StringUtil.pregMatch("for ([0-9]+) day", _news));

        // Sabotage Wizards
        if (_news.contains("Our spellcasting ability has been sabotaged!")) {
            _thieveryOperation = ThieveryOperation.SABOTAGE_WIZARDS;
            _isAffliction = true;

            return;
        }

        // Rob Granaries
        if (_news.contains("bushels were stolen from our granaries")) {
            final Integer foodLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) bushel.* were stolen.*", _news));
            if (foodLost != null) {
                _food = Util.coalesce(_food) - foodLost;
            }

            _thieveryOperation = ThieveryOperation.ROB_GRANARIES;
            return;
        }
        if (_news.contains("Thieves attempted to steal from our granaries, but found them empty.")) {
            _food = Util.coalesce(_food);

            _thieveryOperation = ThieveryOperation.ROB_GRANARIES;
            return;
        }

        // Rob Vaults
        if (_news.contains("gold coins were stolen from our coffers") || _news.contains("Thieves attempted to steal from our vaults, but found them empty.") || _news.contains("gold coin were stolen from our coffers")) {
            final Integer goldLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) gold coin.* were stolen.*", _news));
            if (goldLost != null) {
                _gold = Util.coalesce(_gold) - goldLost;
            }
            else {
                _gold = Util.coalesce(_gold); // Set gold amount to zero if it hasn't been already... (i.e. unsuccessful robbery)
            }

            _thieveryOperation = ThieveryOperation.ROB_VAULTS;
            return;
        }

        // Rob Towers
        if (_news.contains("runes of our runes were stolen")) {
            final Integer runesLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) rune.* were stolen.*", _news));
            if (runesLost != null) {
                _runes = Util.coalesce(_runes) - runesLost;
            }

            _thieveryOperation = ThieveryOperation.ROB_TOWERS;
            return;
        }

        // Kidnap
        if (_news.contains("peasants were kidnapped") || _news.contains("peasant were kidnapped")) {
            final Integer peasantsLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) peasant", _news));
            if (peasantsLost != null) {
                _peasants = Util.coalesce(_peasants) - peasantsLost;
            }

            _thieveryOperation = ThieveryOperation.KIDNAP;
            return;
        }

        // Arson
        if (_news.contains("buildings burned down!")) {
            final Integer buildingsLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) acres of", _news));
            if (buildingsLost != null) {
                _buildings = Util.coalesce(_buildings) - buildingsLost;
            }

            _thieveryOperation = ThieveryOperation.ARSON;
            return;
        }
        if (_news.contains("Thieves attempted to burn")) {
            _thieveryOperation = ThieveryOperation.ARSON;
            return;
        }

        // Night Strike
        if (_news.contains("our troops were found dead today")) {
            _thieveryOperation = ThieveryOperation.NIGHT_STRIKE;

            final Integer militaryLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) of our troop.* found dead today", _news));
            if (militaryLost != null) {
                _military = Util.coalesce(_military) - militaryLost;
            }

            return;
        }

        // Riots
        if (_news.contains("Rioting has started")) {
            _thieveryOperation = ThieveryOperation.RIOTS;
            _isAffliction = true;
            return;
        }

        // Steal Horses
        if (_news.contains("horse") && _news.contains("stolen")) {
            _thieveryOperation = ThieveryOperation.STEAL_HORSES;

            _horses = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) horse.* been stolen!", _news));
            return;
        }

        // TODO: BRIBE THIEVES

        // Bribe Generals
        if (_news.contains("We have discovered a turncoat general leading our military. He has been executed for treason!")) {
            _thieveryOperation = ThieveryOperation.BRIBE_GENERALS;
            _isAffliction = true;
            return;
        }

        // FREE PRISONERS
        if (_news.contains("prisoners escaped our dungeons!")) {
            _thieveryOperation = ThieveryOperation.FREE_PRISONERS;

            final Integer prisonersLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) prisoner.*escaped", _news));
            if (prisonersLost != null) {
                _prisoners = Util.coalesce(_prisoners) - prisonersLost;
            }

            return;
        }

        // Assassinate Wizards
        if (_news.contains("wizard") && _news.contains("were assassinated!")) {
            final Integer wizardsLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) wizard.* assassinated!", _news));
            if (wizardsLost != null) {
                _wizards = Util.coalesce(_wizards) - wizardsLost;
            }

            _thieveryOperation = ThieveryOperation.ASSASSINATE_WIZARDS;
            return;
        }

        // Propaganda
        if (_news.contains("abandoned us hoping for a better life!")) {
            _thieveryOperation = ThieveryOperation.PROPAGANDA;

            final Integer soldiersLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) soldier. abandoned us hoping for a better life!", _news));
            if (soldiersLost != null) {
                _soldiers = Util.coalesce(_soldiers) - soldiersLost;
            }

            final Integer specialistsLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) of our specialist troops abandoned us hoping for a better life!", _news));
            if (specialistsLost != null) {
                _specialists = Util.coalesce(_specialists) - specialistsLost;
            }

            final String pluralEliteName = Province.getEliteKeyword(_province.getRace());
            final String singularEliteName = pluralEliteName.substring(0, pluralEliteName.length() - 1);
            final Integer elitesLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) "+ singularEliteName +". abandoned us hoping for a better life!", _news));
            if (elitesLost != null) {
                _elites = Util.coalesce(_elites) - elitesLost;
            }

            final Integer thievesLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) thieve. abandoned us hoping for a better life!", _news));
            if (thievesLost != null) {
                _thieves = Util.coalesce(_thieves) - thievesLost;
            }

            final Integer wizardsLost = _firstItemOrNull(StringUtil.pregMatch("([0-9,]+) wizard. of our wizards abandoned us hoping for a better life!", _news));
            if (wizardsLost != null) {
                _wizards = Util.coalesce(_wizards) - wizardsLost;
            }

            return;
        }
        if (_news.contains("have been found with enemy propaganda, but so far none have defected.")) {
            _thieveryOperation = ThieveryOperation.PROPAGANDA;
            return;
        }
        if (_news.contains("Enemies attempted to spread propaganda among our soldiers, but failed to convert any of them.")) {
            _thieveryOperation = ThieveryOperation.PROPAGANDA;
            return;
        }

        // Failed Operation
        if (_news.contains("We have found thieves")) {
            _thieveryOperation = ThieveryOperation.FAILED;
            _isAffliction = true;
            return;
        }

        _thieveryOperation = ThieveryOperation.UNKNOWN;
    }

    public NewsThieveryEvent(Province province, String date, String news) {
        super(province, date, news);

        _parse();
    }

    @Override
    public EventType getType() {
        return EventType.THIEVERY_OPERATION;
    }

    public ThieveryOperation getThieveryOperation() {
        return _thieveryOperation;
    }

    @Override
    public String toString() {
        return super.toString()
            + " OPERATION:"+ _thieveryOperation
            + " GOLD:"+ _gold
            + " FOOD:"+ _food
            + " RUNES:"+ _runes
            + " DURATION:"+ _dayDuration
        ;
    }

    @Override
    public Integer getNetGold() { return Util.coalesce(_gold); }

    @Override
    public Integer getNetFood() { return Util.coalesce(_food); }

    @Override
    public Integer getNetRunes() { return Util.coalesce(_runes); }

    @Override
    public Integer getNetHorses() { return Util.coalesce(_horses); }

    @Override
    public Integer getNetPeasants() { return Util.coalesce(_peasants); }

    @Override
    public Integer getNetSoldiers() {
        return Util.coalesce(_military) * Util.coalesce(_province.getSoldiersHome()) / Util.coalesce(_province.getMilitaryPopulation(), 1);
    }

    @Override
    public Integer getNetDefensiveUnits() {
        return Util.coalesce(_military) * Util.coalesce(_province.getDefensiveUnits()) / Util.coalesce(_province.getMilitaryPopulation(), 1);
    }

    @Override
    public Integer getNetOffensiveUnits() {
        return Util.coalesce(_military) * Util.coalesce(_province.getOffensiveUnitsHome()) / Util.coalesce(_province.getMilitaryPopulation(), 1);
    }

    @Override
    public Integer getNetElites() {
        return Util.coalesce(_military) * Util.coalesce(_province.getElitesHome()) / Util.coalesce(_province.getMilitaryPopulation(), 1);
    }

    @Override
    public Integer getNetThieves() {
        return Util.coalesce(_military) * Util.coalesce(_province.getThieves()) / Util.coalesce(_province.getMilitaryPopulation(), 1);
    }

    @Override
    public String getIconName() {
        switch (_thieveryOperation) {
            case SABOTAGE_WIZARDS:      return "Sabotage Wizards";
            case ROB_GRANARIES:         return "Rob Granaries";
            case ROB_VAULTS:            return "Rob Vaults";
            case ROB_TOWERS:            return "Rob Towers";
            case KIDNAP:                return "Kidnap";
            case ARSON:                 return "Arson";
            case NIGHT_STRIKE:          return "Night Strike";
            case RIOTS:                 return "Riots";
            case STEAL_HORSES:          return "Steal Horses";
            case BRIBE_THIEVES:         return "Bribe Thieves";
            case BRIBE_GENERALS:        return "Bribe Generals";
            case FREE_PRISONERS:        return "Free Prisoners";
            case ASSASSINATE_WIZARDS:   return "Assassinate Wizards";
            case PROPAGANDA:            return "Propaganda";
            case FAILED:                return null;
            default:                    return null;
        }
    }

    @Override
    public Icon getIcon() {
        switch (_thieveryOperation) {
            case SABOTAGE_WIZARDS:      return Icon.WIZARD_TOWER_EXPLOSION;
            case ROB_GRANARIES:         return Icon.THIEF_SILO;
            case ROB_VAULTS:            return Icon.THIEF_BANK;
            case ROB_TOWERS:            return Icon.THIEF_TOWER;
            case KIDNAP:                return Icon.KIDNAP_MASK;
            case ARSON:                 return Icon.HOUSE_FIRE;
            case NIGHT_STRIKE:          return Icon.ROGUE;
            case RIOTS:                 return Icon.FIST;
            case STEAL_HORSES:          return Icon.HORSE;
            case BRIBE_THIEVES:         return Icon.THIEF_BRIBERY;
            case BRIBE_GENERALS:        return Icon.THIEF_BRIBERY_GENERAL;
            case FREE_PRISONERS:        return Icon.GET_OUT_OF_JAIL;
            case ASSASSINATE_WIZARDS:   return Icon.WIZARD_KNIFE;
            case PROPAGANDA:            return Icon.PROPAGANDA;
            case FAILED:                return null;
            default:                    return null;
        }
    }

    @Override
    public Integer getDuration() { return _dayDuration; }

    @Override
    public Boolean isAffliction() { return _isAffliction; }
}
