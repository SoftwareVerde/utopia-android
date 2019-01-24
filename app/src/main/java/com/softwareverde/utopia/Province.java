package com.softwareverde.utopia;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.bundle.ActiveSpellBundle;
import com.softwareverde.utopia.bundle.ActiveSpellsBundle;
import com.softwareverde.utopia.bundle.BuildCostBundle;
import com.softwareverde.utopia.bundle.BuildingBundle;
import com.softwareverde.utopia.bundle.BuildingInProgressBundle;
import com.softwareverde.utopia.bundle.BuildingsBundle;
import com.softwareverde.utopia.bundle.Bundle;
import com.softwareverde.utopia.bundle.DeployedArmiesBundle;
import com.softwareverde.utopia.bundle.DeployedArmyBundle;
import com.softwareverde.utopia.bundle.DraftRateBundle;
import com.softwareverde.utopia.bundle.ExplorationCostsBundle;
import com.softwareverde.utopia.bundle.InfiltrateThievesBundle;
import com.softwareverde.utopia.bundle.KingdomProvinceBundle;
import com.softwareverde.utopia.bundle.MilitaryBundle;
import com.softwareverde.utopia.bundle.MilitaryInProgressBundle;
import com.softwareverde.utopia.bundle.MilitarySettingsBundle;
import com.softwareverde.utopia.bundle.ProvinceIdBundle;
import com.softwareverde.utopia.bundle.ProvinceIntelActiveSpellBundle;
import com.softwareverde.utopia.bundle.ProvinceIntelBundle;
import com.softwareverde.utopia.bundle.ScienceBundle;
import com.softwareverde.utopia.bundle.SpellResultBundle;
import com.softwareverde.utopia.bundle.StateCouncilBundle;
import com.softwareverde.utopia.bundle.ThieveryOperationBundle;
import com.softwareverde.utopia.bundle.ThroneBundle;
import com.softwareverde.utopia.bundle.TradeSettingsBundle;
import com.softwareverde.utopia.parser.UtopiaParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Province {
    public enum Race {
        AVIAN,
        DWARF,
        ELF,
        FAERY,
        HALFLING,
        HUMAN,
        ORC,
        UNDEAD,
        DRYAD,
        DARK_ELF,
        BOCAN
    }

    public static class DeployedArmy {
        public static DeployedArmy fromBundle(final DeployedArmyBundle bundle) {
            final DeployedArmy deployedArmy = new DeployedArmy();

            deployedArmy._generals = Util.parseInt(bundle.get(DeployedArmyBundle.Keys.GENERALS));
            deployedArmy._soldiers = Util.parseInt(bundle.get(DeployedArmyBundle.Keys.SOLDIERS));
            deployedArmy._defensiveUnits = Util.parseInt(bundle.get(DeployedArmyBundle.Keys.DEFENSIVE_UNITS));
            deployedArmy._offensiveUnits = Util.parseInt(bundle.get(DeployedArmyBundle.Keys.OFFENSIVE_UNITS));
            deployedArmy._elites = Util.parseInt(bundle.get(DeployedArmyBundle.Keys.ELITE_UNITS));
            deployedArmy._horses = Util.parseInt(bundle.get(DeployedArmyBundle.Keys.HORSES));
            deployedArmy._acres = Util.parseInt(bundle.get(DeployedArmyBundle.Keys.CAPTURED_LAND));
            deployedArmy._returnTimeFromEpoch = Util.parseLong(bundle.get(DeployedArmyBundle.Keys.RETURN_TIME));

            return deployedArmy;
        }

        public static DeployedArmy fromBundle(final EspionageArmyIntel espionageArmyIntel) {
            final DeployedArmy deployedArmy = new DeployedArmy();

            deployedArmy._returnTimeFromEpoch = espionageArmyIntel._invalidationTime;
            deployedArmy._isFromEspionage = true;

            return deployedArmy;
        }

        private Integer _generals;
        private Integer _soldiers;
        private Integer _defensiveUnits;
        private Integer _offensiveUnits;
        private Integer _elites;
        private Integer _horses;
        private Integer _acres;
        private Long _returnTimeFromEpoch;
        private Boolean _isFromEspionage = false;

        public Integer getGenerals()        { return _generals; }
        public Integer getSoldiers()        { return _soldiers; }
        public Integer getDefensiveUnits()  { return _defensiveUnits; }
        public Integer getOffensiveUnits()  { return _offensiveUnits; }
        public Integer getElites()          { return _elites; }
        public Integer getHorses()          { return _horses; }
        public Integer getAcres()           { return _acres; }
        public Integer getReturnTime() {
            return (int) (_returnTimeFromEpoch - (System.currentTimeMillis() / 1000L));
        }
        public Long getReturnTimeFromEpoch() {
            return _returnTimeFromEpoch;
        }

        private DeployedArmy() { }

        public Boolean isExpired() {
            return (_returnTimeFromEpoch <= (System.currentTimeMillis() / 1000L));
        }

        public Boolean isFromEspionage() {
            return _isFromEspionage;
        }
    }

    private static class EspionageArmyIntel {
        public enum IntelAge {
            FRESH, STALE, ROTTEN
        }
        public static IntelAge intelAgeFromString(String string) {
            switch (string.toUpperCase()) {
                case "FRESH": { return IntelAge.FRESH; }
                case "STALE": { return IntelAge.STALE; }
                case "ROTTEN": { return IntelAge.ROTTEN; }
                default: { return null; }
            }
        }

        private Long _sourcedTime;
        private IntelAge _intelAge;
        private Integer _defenseWithArmyHome;
        private Integer _defenseWithArmyAway;
        private Long _invalidationTime;

        public static EspionageArmyIntel armyAway(Integer defenseWithArmyAway, IntelAge intelAge, Long sourcedTime) {
            EspionageArmyIntel espionageArmyIntel = new EspionageArmyIntel();
            espionageArmyIntel._defenseWithArmyAway = defenseWithArmyAway;
            espionageArmyIntel._defenseWithArmyHome = null;
            espionageArmyIntel._intelAge = intelAge;
            espionageArmyIntel._sourcedTime = sourcedTime;
            return espionageArmyIntel;
        }
        public static EspionageArmyIntel armyHome(Integer defenseWithArmyHome, IntelAge intelAge, Long sourcedTime) {
            EspionageArmyIntel espionageArmyIntel = new EspionageArmyIntel();
            espionageArmyIntel._defenseWithArmyAway = null;
            espionageArmyIntel._defenseWithArmyHome = defenseWithArmyHome;
            espionageArmyIntel._intelAge = intelAge;
            espionageArmyIntel._sourcedTime = sourcedTime;
            return espionageArmyIntel;
        }

        public Boolean isExpired() {
            // At-Home intel doesn't really expire...
            if (_defenseWithArmyHome != null) {
                return false;
            }

            // Intel is only expired if _invalidationTime is set, and is in the past.
            if (_invalidationTime != null && _invalidationTime < (System.currentTimeMillis() / 1000L)) {
                return true;
            }

            return false;
        }
        public Boolean isDefenseWithArmyHome() {
            return (_defenseWithArmyHome != null);
        }

        public Long getSourcedTime() { return _sourcedTime; }
        public IntelAge getIntelAge() { return _intelAge; }
        public Integer getDefense() { return (_defenseWithArmyHome != null ? _defenseWithArmyHome : _defenseWithArmyAway); }
        public void setExpirationTime(Long invalidationTime) { _invalidationTime = invalidationTime; }
    }

    public static Race getRaceForString(String raceString) {
        if (raceString.toUpperCase().equals("AVIAN")) return Race.AVIAN;
        if (raceString.toUpperCase().equals("DWARF")) return Race.DWARF;
        if (raceString.toUpperCase().equals("ELF")) return Race.ELF;
        if (raceString.toUpperCase().equals("FAERY")) return Race.FAERY;
        if (raceString.toUpperCase().equals("HALFLING")) return Race.HALFLING;
        if (raceString.toUpperCase().equals("HUMAN")) return Race.HUMAN;
        if (raceString.toUpperCase().equals("ORC")) return Race.ORC;
        if (raceString.toUpperCase().equals("UNDEAD")) return Race.UNDEAD;
        if (raceString.toUpperCase().equals("DRYAD")) return Race.DRYAD;
        if (raceString.toUpperCase().equals("DARK ELF")) return Race.DARK_ELF;
        if (raceString.toUpperCase().equals("BOCAN")) return Race.BOCAN;

        return null;
    }
    public static String getStringForRace(Race race) {
        if (race == Race.AVIAN) return "Avian";
        if (race == Race.DWARF) return "Dwarf";
        if (race == Race.ELF) return "Elf";
        if (race == Race.FAERY) return "Faery";
        if (race == Race.HALFLING) return "Halfling";
        if (race == Race.HUMAN) return "Human";
        if (race == Race.ORC) return "Orc";
        if (race == Race.UNDEAD) return "Undead";
        if (race == Race.DRYAD) return "Dryad";
        if (race == Race.DARK_ELF) return "Dark Elf";
        if (race == Race.BOCAN) return "Bocan";

        return "";
    }

    public static Province fromProvinceIdBundle(ProvinceIdBundle bundle) {
        Province province = new Province();
        if (bundle.isValid()) {
            province.setName(bundle.get(ProvinceIdBundle.Keys.NAME));

            final Integer utopiaId = Util.parseInt(bundle.get(ProvinceIdBundle.Keys.UTOPIA_ID));
            if (utopiaId > 0) {
                province.setUtopiaId(utopiaId);
            }
            province.setKingdomIdentifier(new Kingdom.Identifier(Util.parseInt(bundle.get(ProvinceIdBundle.Keys.KINGDOM)), Util.parseInt(bundle.get(ProvinceIdBundle.Keys.ISLAND))));
        }
        return province;
    }

    private Integer _sumIntegerArray(Integer[] integerArray) {
        Integer sum = 0;
        for (Integer i=0; i<integerArray.length; i++) {
            sum += integerArray[i];
        }
        return sum;
    }

    public static String getOffensiveUnitKeyword(Race race) {
        if (race.equals(Race.UNDEAD)) {
            return "Skeletons";
        }
        else if (race.equals(Race.ORC)) {
            return "Goblins";
        }
        else if (race.equals(Race.FAERY)) {
            return "Magicians";
        }
        else if (race.equals(Race.AVIAN)) {
            return "Griffins";
        }
        else if (race.equals(Race.DWARF)) {
            return "Warriors";
        }
        else if (race.equals(Race.ELF)) {
            return "Rangers";
        }
        else if (race.equals(Race.HALFLING)) {
            return "Strongarms";
        }
        else if (race.equals(Race.HUMAN)) {
            return "Swordsmen";
        }
        else if (race.equals(Race.DRYAD)) {
            return "Huldras";
        }
        else if (race.equals(Race.DARK_ELF)) {
            return "Night Rangers";
        }
        else if (race.equals(Race.BOCAN)) {
            return "Marauders";
        }

        return "";
    }
    public static String getDefensiveUnitKeyword(Race race) {
        if (race.equals(Race.UNDEAD)) {
            return "Zombies";
        }
        else if (race.equals(Race.ORC)) {
            return "Trolls";
        }
        else if (race.equals(Race.FAERY)) {
            return "Druids";
        }
        else if (race.equals(Race.AVIAN)) {
            return "Harpies";
        }
        else if (race.equals(Race.DWARF)) {
            return "Axemen";
        }
        else if (race.equals(Race.ELF)) {
            return "Archers";
        }
        else if (race.equals(Race.HALFLING)) {
            return "Slingers";
        }
        else if (race.equals(Race.HUMAN)) {
            return "Archers";
        }
        else if (race.equals(Race.DRYAD)) {
            return "Nymphs";
        }
        else if (race.equals(Race.DARK_ELF)) {
            return "Druids";
        }
        else if (race.equals(Race.BOCAN)) {
            return "Imps";
        }

        return "";
    }
    public static String getEliteKeyword(Race race) {
        if (race.equals(Race.UNDEAD)) {
            return "Ghouls";
        }
        else if (race.equals(Race.ORC)) {
            return "Ogres";
        }
        else if (race.equals(Race.FAERY)) {
            return "Beastmasters";
        }
        else if (race.equals(Race.AVIAN)) {
            return "Drakes";
        }
        else if (race.equals(Race.DWARF)) {
            return "Berserkers";
        }
        else if (race.equals(Race.ELF)) {
            return "Elf Lords";
        }
        else if (race.equals(Race.HALFLING)) {
            return "Brutes";
        }
        else if (race.equals(Race.HUMAN)) {
            return "Knights";
        }
        else if (race.equals(Race.DRYAD)) {
            return "Will O' The Wisps";
        }
        else if (race.equals(Race.DARK_ELF)) {
            return "Drows";
        }
        else if (race.equals(Race.BOCAN)) {
            return "Tricksters";
        }

        return "";
    }

    private Integer _id;
    private Integer _utopiaId;
    private Kingdom.Identifier _kingdomIdentifier;

    private String _title;
    private String _name;
    private Race _race;
    private String _rulerName;

    private String _royalCommands = "";

    private Integer _acres;
    private Integer _food;

    private Integer _money;
    private Integer _runes;
    private Integer _stealth;
    private Integer _mana;

    private Integer _peasants;
    private Integer _prisoners;

    private Integer _soldiers;
    private Integer _defensiveUnits;
    private Integer _offensiveUnits;
    private Integer _elites;
    private Integer _horses;

    private Integer _defensivePoints;
    private Integer _offensivePoints;

    private Integer _thieves;
    private Integer _wizards;

    private Float _buildingEfficiency;

    private Building _barrenLand;
    private Building _homes;
    private Building _farms;
    private Building _mills;
    private Building _banks;
    private Building _trainingGrounds;
    private Building _armories;
    private Building _barracks;
    private Building _forts;
    private Building _guardStations;
    private Building _hospitals;
    private Building _guilds;
    private Building _towers;
    private Building _thievesDens;
    private Building _watchTowers;
    private Building _laboratories;
    private Building _universities;
    private Building _stables;
    private Building _dungeons;

    private Integer[] _homesInProgress = Util.initializeIntegerArray(24);
    private Integer[] _farmsInProgress = Util.initializeIntegerArray(24);
    private Integer[] _millsInProgress = Util.initializeIntegerArray(24);
    private Integer[] _banksInProgress = Util.initializeIntegerArray(24);
    private Integer[] _trainingGroundsInProgress = Util.initializeIntegerArray(24);
    private Integer[] _armoriesInProgress = Util.initializeIntegerArray(24);
    private Integer[] _barracksInProgress = Util.initializeIntegerArray(24);
    private Integer[] _fortsInProgress = Util.initializeIntegerArray(24);
    private Integer[] _guardStationsInProgress = Util.initializeIntegerArray(24);
    private Integer[] _hospitalsInProgress = Util.initializeIntegerArray(24);
    private Integer[] _guildsInProgress = Util.initializeIntegerArray(24);
    private Integer[] _towersInProgress = Util.initializeIntegerArray(24);
    private Integer[] _thievesDensInProgress = Util.initializeIntegerArray(24);
    private Integer[] _watchTowersInProgress = Util.initializeIntegerArray(24);
    private Integer[] _laboratoriesInProgress = Util.initializeIntegerArray(24);
    private Integer[] _universitiesInProgress = Util.initializeIntegerArray(24);
    private Integer[] _stablesInProgress = Util.initializeIntegerArray(24);
    private Integer[] _dungeonsInProgress = Util.initializeIntegerArray(24);

    private Integer[] _defensiveUnitsInProgress = Util.initializeIntegerArray(24);
    private Integer[] _offensiveUnitsInProgress = Util.initializeIntegerArray(24);
    private Integer[] _elitesInProgress = Util.initializeIntegerArray(24);
    private Integer[] _thievesInProgress = Util.initializeIntegerArray(24);

    private List<DeployedArmy> _deployedArmies = new LinkedList<DeployedArmy>();
    private List<EspionageArmyIntel> _espionageArmyIntel = new LinkedList<EspionageArmyIntel>();

    private Map<String, ActiveSpell> _activeSpells = new HashMap<String, ActiveSpell>();
    private DraftRate _draftRate = null;
    private Integer _draftTarget = 75;
    private Integer _wageRate = 50;

    private Integer _maxPopulation;
    private Integer _unemployedPeasants;
    private Integer _unfilledJobs;
    private Float _employmentPercent;
    private Integer _income;
    private Integer _militaryWages;
    private Integer _networth;
    private Integer _honor;

    private Integer _mercenaryCost;
    private Float _mercenaryRate;   // Mercenaries per Soldier
    private Float _offenseModifier; // NOTE: "precalculated_modifier" from send_armies. Do not store in database.
    private Integer _minConquestNetworth;
    private Integer _maxConquestNetworth;

    private Integer _tradeBalance;
    private Float _aidTaxRate;
    private Boolean _allowsIncomingAid;

    private Integer _buildCost;
    private Integer _razeCost;
    private Integer _buildCredits;
    private Integer _buildTime;

    private Integer _offenseUnitCost;
    private Integer _defenseUnitCost;
    private Integer _eliteCost;
    private Integer _thiefCost;

    private Integer _explorationSoldiersCost;
    private Integer _explorationGoldCost;
    private Integer _explorationAcresAvailable;
    private Integer _explorationAcresInProgress;

    private Science _science;

    private Boolean _hasPlague = false;

    public Boolean isValid() {
        return ((_kingdomIdentifier != null && _kingdomIdentifier.isValid()) && (_race != null) && (_name != null));
    }

    public Boolean isIdentifiable() {
        return ( (_kingdomIdentifier != null && _kingdomIdentifier.isValid()) && (_name != null && (! _name.isEmpty())) );
    }

    public void update(final Bundle bundle) {
        switch (bundle.getBundleType()) {
            case BuildCostBundle.BUNDLE_TYPE:           { _applyBuildCostBundle((BuildCostBundle) bundle); } break;
            case ThroneBundle.BUNDLE_TYPE:              { _applyThroneBundle((ThroneBundle) bundle); } break;
            case BuildingsBundle.BUNDLE_TYPE:           { _applyBuildingsBundle((BuildingsBundle) bundle); } break;
            case ExplorationCostsBundle.BUNDLE_TYPE:    { _applyExplorationCostsBundle((ExplorationCostsBundle) bundle); } break;
            case InfiltrateThievesBundle.BUNDLE_TYPE:   { _applyInfiltrateThievesBundle((InfiltrateThievesBundle) bundle); } break;
            case MilitaryBundle.BUNDLE_TYPE:            { _applyMilitaryBundle((MilitaryBundle) bundle); } break;
            case MilitarySettingsBundle.BUNDLE_TYPE:    { _applyMilitarySettingsBundle((MilitarySettingsBundle) bundle); } break;
            case ProvinceIntelBundle.BUNDLE_TYPE:       { _applyProvinceIntelBundle((ProvinceIntelBundle) bundle); } break;
            case ScienceBundle.BUNDLE_TYPE:             { _applyScienceBundle((ScienceBundle) bundle); } break;
            case TradeSettingsBundle.BUNDLE_TYPE:       { _applyTradeSettingsBundle((TradeSettingsBundle) bundle); } break;
            case StateCouncilBundle.BUNDLE_TYPE:        { _applyStateCouncilBundle((StateCouncilBundle) bundle); } break;
            case SpellResultBundle.BUNDLE_TYPE:         { _applySpellResultBundle((SpellResultBundle) bundle); } break;
            case ActiveSpellsBundle.BUNDLE_TYPE: {
                final List<ActiveSpell> activeSpells = new LinkedList<ActiveSpell>();
                for (final Bundle groupedBundle : bundle.getGroup(ActiveSpellsBundle.Keys.ACTIVE_SPELLS)) {
                    activeSpells.add(ActiveSpell.fromBundle((ActiveSpellBundle) groupedBundle));
                }
                _updateActiveSpells(activeSpells);
            } break;
            case DeployedArmiesBundle.BUNDLE_TYPE: {
                final List<DeployedArmy> deployedArmyBundles = new LinkedList<DeployedArmy>();
                if (bundle.hasGroupKey(DeployedArmiesBundle.Keys.DEPLOYED_ARMIES_GROUP)) {
                    for (final Bundle subBundle : bundle.getGroup(DeployedArmiesBundle.Keys.DEPLOYED_ARMIES_GROUP)) {
                        deployedArmyBundles.add(DeployedArmy.fromBundle((DeployedArmyBundle) subBundle));
                    }
                    _updateDeployedArmies(deployedArmyBundles);
                }
            } break;
            case ThieveryOperationBundle.BUNDLE_TYPE: {
                if (Util.parseInt(bundle.get(ThieveryOperationBundle.Keys.WAS_SUCCESS)) > 0) {
                    final String[] possibleSubBundleKeys = new String[]{
                        ThieveryOperationBundle.Keys.TARGET_INFILTRATE_BUNDLE, ThieveryOperationBundle.Keys.TARGET_MILITARY_BUNDLE,
                        ThieveryOperationBundle.Keys.TARGET_PROVINCE_BUNDLE, ThieveryOperationBundle.Keys.TARGET_SURVEY_BUNDLE
                    };

                    for (final String subBundleKey : possibleSubBundleKeys) {
                        if (bundle.hasBundleKey(subBundleKey)) {
                            final Bundle subBundle = bundle.getBundle(subBundleKey);
                            this.update(subBundle);
                        }
                    }
                }
            } break;
            default: {
                System.out.println("Cannot update province with Bundle Type: "+ bundle.getBundleType());
            } break;
        }
    }

    private void _applyThroneBundle(final ThroneBundle bundle) {
        if (bundle.hasKey(ThroneBundle.Keys.RACE)) {
            _race = getRaceForString(bundle.get(ThroneBundle.Keys.RACE));
        }
        if (bundle.hasKey(ThroneBundle.Keys.BUILDING_EFFICIENCY)) {
            _buildingEfficiency = Util.parseInt(bundle.get(ThroneBundle.Keys.BUILDING_EFFICIENCY)) / 100.0f;
        }

        if (bundle.hasKey(ThroneBundle.Keys.KINGDOM) && bundle.hasKey(ThroneBundle.Keys.ISLAND)) {
            _kingdomIdentifier = new Kingdom.Identifier(Util.parseInt(bundle.get(ThroneBundle.Keys.KINGDOM)), Util.parseInt(bundle.get(ThroneBundle.Keys.ISLAND)));
        }

        if (bundle.hasKey(ThroneBundle.Keys.PROVINCE_NAME)) {
            _name = bundle.get(ThroneBundle.Keys.PROVINCE_NAME);
        }

        if (bundle.hasKey(ThroneBundle.Keys.RULER_NAME)) {
            _rulerName = bundle.get(ThroneBundle.Keys.RULER_NAME);
        }

        if (bundle.hasKey(ThroneBundle.Keys.LAND)) {
            _acres = Util.parseInt(bundle.get(ThroneBundle.Keys.LAND));
        }
        if (bundle.hasKey(ThroneBundle.Keys.FOOD)) {
            _food = Util.parseInt(bundle.get(ThroneBundle.Keys.FOOD));
        }
        if (bundle.hasKey(ThroneBundle.Keys.GOLD)) {
            _money = Util.parseInt(bundle.get(ThroneBundle.Keys.GOLD));
        }
        if (bundle.hasKey(ThroneBundle.Keys.RUNES)) {
            _runes = Util.parseInt(bundle.get(ThroneBundle.Keys.RUNES));
        }
        if (bundle.hasKey(ThroneBundle.Keys.TRADE_BALANCE)) {
            _tradeBalance = Util.parseInt(bundle.get(ThroneBundle.Keys.TRADE_BALANCE));
        }
        if (bundle.hasKey(ThroneBundle.Keys.PEASANTS)) {
            _peasants = Util.parseInt(bundle.get(ThroneBundle.Keys.PEASANTS));
        }
        if (bundle.hasKey(ThroneBundle.Keys.SOLDIERS)) {
            _soldiers = Util.parseInt(bundle.get(ThroneBundle.Keys.SOLDIERS));
        }
        if (bundle.hasKey(ThroneBundle.Keys.DEFENSIVE_UNITS)) {
            _defensiveUnits = Util.parseInt(bundle.get(ThroneBundle.Keys.DEFENSIVE_UNITS));
        }
        if (bundle.hasKey(ThroneBundle.Keys.OFFENSIVE_UNITS)) {
            _offensiveUnits = Util.parseInt(bundle.get(ThroneBundle.Keys.OFFENSIVE_UNITS));
        }
        if (bundle.hasKey(ThroneBundle.Keys.ELITES)) {
            _elites = Util.parseInt(bundle.get(ThroneBundle.Keys.ELITES));
        }
        if (bundle.hasKey(ThroneBundle.Keys.HORSES)) {
            _horses = Util.parseInt(bundle.get(ThroneBundle.Keys.HORSES));
        }
        if (bundle.hasKey(ThroneBundle.Keys.PRISONERS)) {
            _prisoners = Util.parseInt(bundle.get(ThroneBundle.Keys.PRISONERS));
        }
        if (bundle.hasKey(ThroneBundle.Keys.THIEVES)) {
            String thieveCount = bundle.get(ThroneBundle.Keys.THIEVES);
            if (thieveCount.length() > 0) {
                _thieves = Util.parseInt(thieveCount);
            }
        }
        if (bundle.hasKey(ThroneBundle.Keys.WIZARDS)) {
            String wizardCount = bundle.get(ThroneBundle.Keys.WIZARDS);
            if (wizardCount.length() > 0) {
                _wizards = Util.parseInt(wizardCount);
            }
        }

        if (bundle.hasKey(ThroneBundle.Keys.DEFENSIVE_POINTS)) {
            // _defenseIntel.set(IntelSource.Sources.THRONE, Util.parseInt(bundle.get(UtopiaParser.ThroneBundle.Keys.DEFENSIVE_POINTS), System.currentTimeMillis() / 1000L));
            _defensivePoints = Util.parseInt(bundle.get(ThroneBundle.Keys.DEFENSIVE_POINTS));
        }
        if (bundle.hasKey(ThroneBundle.Keys.OFFENSIVE_POINTS)) {
            // _offenseIntel.set(IntelSource.Sources.THRONE, Util.parseInt(bundle.get(UtopiaParser.ThroneBundle.Keys.OFFENSIVE_POINTS), System.currentTimeMillis() / 1000L));
            _offensivePoints = Util.parseInt(bundle.get(ThroneBundle.Keys.OFFENSIVE_POINTS));
        }

        if (bundle.hasKey(ThroneBundle.Keys.TITLE)) {
            _title = bundle.get(ThroneBundle.Keys.TITLE);
        }
        if (bundle.hasKey(ThroneBundle.Keys.NETWORTH)) {
            _networth = Util.parseInt(bundle.get(ThroneBundle.Keys.NETWORTH));
        }

        if (bundle.hasKey(ThroneBundle.Keys.STEALTH)) {
            String stealth = bundle.get(ThroneBundle.Keys.STEALTH);
            if (stealth.length() > 0) {
                _stealth = Util.parseInt(stealth);
            }
        }
        if (bundle.hasKey(ThroneBundle.Keys.MANA)) {
            String mana  = bundle.get(ThroneBundle.Keys.MANA);
            if (mana.length() > 0) {
                _mana = Util.parseInt(mana);
            }
        }

        if (bundle.hasKey(ThroneBundle.Keys.ROYAL_COMMANDS)) {
            _royalCommands = bundle.get(ThroneBundle.Keys.ROYAL_COMMANDS);
        }

        if (bundle.hasKey(ThroneBundle.Keys.HAS_PLAGUE)) {
            _hasPlague = (Util.parseInt(bundle.get(ThroneBundle.Keys.HAS_PLAGUE)) > 0);
        }
    }

    private Building _createBuildingFromBundleEntry(final BuildingsBundle bundle, final String buildingBundleKey, final Building defaultValue) {
        if (! bundle.hasBundleKey(buildingBundleKey)) { return defaultValue; }

        final BuildingBundle buildingBundle = (BuildingBundle) bundle.getBundle(buildingBundleKey);
        if (! buildingBundle.isValid()) { return defaultValue; }

        return new Building(
            Building.getBuildingType(buildingBundle.get(BuildingBundle.Keys.NAME)),
            Util.parseInt(buildingBundle.get(BuildingBundle.Keys.COUNT)),
            Util.parseFloat(buildingBundle.get(BuildingBundle.Keys.PERCENT)),
            buildingBundle.get(BuildingBundle.Keys.EFFECT)
        );
    }
    // Updates the contents of inProgressArray to be the content of the in-progress for that buildingKey in the BuildingBundle.
    //  If the size of the bundle is not the correct size, the array is not updated.
    private void _updateBuildingInProgress(final BuildingsBundle buildingsBundle, final String buildingKey, final Integer[] inProgressArray) {
        if (! buildingsBundle.isValid()) { return; }

        if (! buildingsBundle.hasBundleKey(buildingKey)) { return; }
        final BuildingBundle buildingBundle = (BuildingBundle) buildingsBundle.getBundle(buildingKey);
        if (! buildingBundle.hasBundleKey(BuildingBundle.Keys.IN_PROGRESS)) { return; }

        final BuildingInProgressBundle buildingInProgressBundle = (BuildingInProgressBundle) buildingBundle.getBundle(BuildingBundle.Keys.IN_PROGRESS);
        if (! buildingInProgressBundle.isValid()) { return; }

        Integer i = 0;
        for (final String tickKey : BuildingInProgressBundle.ORDERED_TICK_KEYS) {
            inProgressArray[i] = Util.parseInt(buildingInProgressBundle.get(tickKey));
            i += 1;
        }
    }

    private void _applyBuildingsBundle(final BuildingsBundle bundle) {
        _barrenLand         = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.BARREN, _barrenLand);
        _homes              = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.HOMES, _homes);
        _farms              = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.FARMS, _farms);
        _mills              = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.MILLS, _mills);
        _banks              = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.BANKS, _banks);
        _trainingGrounds    = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.TRAINING_GROUNDS, _trainingGrounds);
        _armories           = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.ARMORIES, _armories);
        _barracks           = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.BARRACKS, _barracks);
        _forts              = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.FORTS, _forts);
        _guardStations      = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.GUARD_STATIONS, _guardStations);
        _hospitals          = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.HOSPITALS, _hospitals);
        _guilds             = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.GUILDS, _guilds);
        _towers             = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.TOWERS, _towers);
        _thievesDens        = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.THIEVES_DENS, _thievesDens);
        _watchTowers        = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.WATCH_TOWERS, _watchTowers);
        _laboratories = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.LABORATORIES, _laboratories);
        _universities = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.UNIVERSITIES, _universities);
        _stables            = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.STABLES, _stables);
        _dungeons           = _createBuildingFromBundleEntry(bundle, BuildingsBundle.Keys.DUNGEONS, _dungeons);

        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.HOMES,              _homesInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.FARMS,              _farmsInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.MILLS,              _millsInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.BANKS,              _banksInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.TRAINING_GROUNDS,   _trainingGroundsInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.ARMORIES,           _armoriesInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.BARRACKS,           _barracksInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.FORTS,              _fortsInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.GUARD_STATIONS,     _guardStationsInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.HOSPITALS,          _hospitalsInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.GUILDS,             _guildsInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.TOWERS,             _towersInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.THIEVES_DENS,       _thievesDensInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.WATCH_TOWERS,       _watchTowersInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.LABORATORIES, _laboratoriesInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.UNIVERSITIES, _universitiesInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.STABLES,            _stablesInProgress);
        _updateBuildingInProgress(bundle, BuildingsBundle.Keys.DUNGEONS,           _dungeonsInProgress);

    }

    private void _applyMilitaryBundle(final MilitaryBundle bundle) {
        if (! bundle.isValid()) {
            System.out.println("NOTICE: Ignoring malformed military bundle.");
            return;
        }

        MilitaryInProgressBundle unitsInProgress = null;

        if (bundle.hasBundleKey(MilitaryBundle.Keys.DEFENSIVE_UNITS_PROGRESS)) { // Defensive Units In Progress
            unitsInProgress = (MilitaryInProgressBundle) bundle.getBundle(MilitaryBundle.Keys.DEFENSIVE_UNITS_PROGRESS);
            Integer i = 0;
            for (final String inProgressBundleKey : MilitaryInProgressBundle.ORDERED_TICK_KEYS) {
                _defensiveUnitsInProgress[i] = Util.parseInt(unitsInProgress.get(inProgressBundleKey));
                i += 1;
            }
        }

        { // Offensive Units In Progress
            unitsInProgress = (MilitaryInProgressBundle) bundle.getBundle(MilitaryBundle.Keys.OFFENSIVE_UNITS_PROGRESS);
            Integer i = 0;
            for (final String inProgressBundleKey : MilitaryInProgressBundle.ORDERED_TICK_KEYS) {
                _offensiveUnitsInProgress[i] = Util.parseInt(unitsInProgress.get(inProgressBundleKey));
                i += 1;
            }
        }

        { // Elites In Progress
            unitsInProgress = (MilitaryInProgressBundle) bundle.getBundle(MilitaryBundle.Keys.ELITE_UNITS_PROGRESS);
            Integer i = 0;
            for (final String inProgressBundleKey : MilitaryInProgressBundle.ORDERED_TICK_KEYS) {
                _elitesInProgress[i] = Util.parseInt(unitsInProgress.get(inProgressBundleKey));
                i += 1;
            }
        }

        { // Thieves In Progress
            unitsInProgress = (MilitaryInProgressBundle) bundle.getBundle(MilitaryBundle.Keys.THIEVES_PROGRESS);
            Integer i = 0;
            for (final String inProgressBundleKey : MilitaryInProgressBundle.ORDERED_TICK_KEYS) {
                _thievesInProgress[i] = Util.parseInt(unitsInProgress.get(inProgressBundleKey));
                i += 1;
            }
        }

        Integer totalSoldiers = Util.parseInt(bundle.get(MilitaryBundle.Keys.SOLDIERS_HOME));
        Integer totalOffensiveUnits = Util.parseInt(bundle.get(MilitaryBundle.Keys.OFFENSIVE_UNITS_HOME));
        Integer totalDefensiveUnits = Util.parseInt(bundle.get(MilitaryBundle.Keys.DEFENSIVE_UNITS_HOME));
        Integer totalElites = Util.parseInt(bundle.get(MilitaryBundle.Keys.ELITE_UNITS_HOME));
        Integer totalHorses = Util.parseInt(bundle.get(MilitaryBundle.Keys.HORSES_HOME));

        Boolean defenseAtHomeIsWithArmyHome = true;
        if (bundle.hasGroupKey(MilitaryBundle.Keys.DEPLOYED_ARMIES)) {
            defenseAtHomeIsWithArmyHome = false;
            final List<DeployedArmy> deployedArmies = new LinkedList<DeployedArmy>();
            for (final Bundle subBundle : bundle.getGroup(MilitaryBundle.Keys.DEPLOYED_ARMIES)) {
                deployedArmies.add(DeployedArmy.fromBundle((DeployedArmyBundle) subBundle));
            }
            _updateDeployedArmies(deployedArmies);

            for (final DeployedArmy deployedArmy : deployedArmies) {
                totalSoldiers += deployedArmy.getSoldiers();
                totalOffensiveUnits += deployedArmy.getOffensiveUnits();
                totalDefensiveUnits += deployedArmy.getDefensiveUnits();
                totalElites += deployedArmy.getElites();
                totalHorses += deployedArmy.getHorses();
            }
        }

        _soldiers = totalSoldiers;
        _offensiveUnits = totalOffensiveUnits;
        _defensiveUnits = totalDefensiveUnits;
        _elites = totalElites;
        _horses = totalHorses;

        // Only store defensivePoints/Army-Intel if the army is home (as it is as good as a SoT).
        if (defenseAtHomeIsWithArmyHome) {
            //  If the army is away, rely on Kingdom Espionage for this value.
            if (bundle.hasKey(MilitaryBundle.Keys.DEFENSE_AT_HOME)) {
                _defensivePoints = Util.parseInt(bundle.get(MilitaryBundle.Keys.DEFENSE_AT_HOME));
            }
        }

        if (bundle.hasKey(MilitaryBundle.Keys.OFFENSE_AT_HOME)) {
            // TODO: Store this with information on when it expires...
            // _offensivePoints = Util.parseInt(bundle.get(UtopiaParser.MilitaryBundle.Keys.OFFENSE_AT_HOME));
        }
    }

    private void _applyInfiltrateThievesBundle(final InfiltrateThievesBundle infiltrateThievesBundle) {
        if (! infiltrateThievesBundle.isValid()) {
            return;
        }

        final Integer thievesCount = Util.parseInt(infiltrateThievesBundle.get(InfiltrateThievesBundle.Keys.THIEVES));
        _thieves = thievesCount;
    }

    private void _applyBuildCostBundle(final BuildCostBundle buildCostBundle) {
        if (! buildCostBundle.isValid()) {
            return;
        }

        _buildCost = Util.parseInt(buildCostBundle.get(BuildCostBundle.Keys.CONSTRUCTION_COST));
        _razeCost = Util.parseInt(buildCostBundle.get(BuildCostBundle.Keys.RAZE_COST));
        _buildCredits = Util.parseInt(buildCostBundle.get(BuildCostBundle.Keys.FREE_CREDITS));
        _buildTime = Util.parseInt(buildCostBundle.get(BuildCostBundle.Keys.CONSTRUCTION_TIME));
    }

    private void _applyExplorationCostsBundle(final ExplorationCostsBundle explorationCostsBundle) {
        if (! explorationCostsBundle.isValid()) {
            return;
        }

        _explorationSoldiersCost = Util.parseInt(explorationCostsBundle.get(ExplorationCostsBundle.Keys.SOLDIERS_PER_ACRE));
        _explorationGoldCost = Util.parseInt(explorationCostsBundle.get(ExplorationCostsBundle.Keys.GOLD_PER_ACRE));
        _explorationAcresAvailable = Util.parseInt(explorationCostsBundle.get(ExplorationCostsBundle.Keys.AVAILABLE_ACRES));
        _explorationAcresInProgress = Util.parseInt(explorationCostsBundle.get(ExplorationCostsBundle.Keys.CURRENTLY_EXPLORING));
    }

    private void _updateDeployedArmies(List<DeployedArmy> deployedArmies) {
        synchronized (_deployedArmies) {
            _deployedArmies.clear();
            for (DeployedArmy deployedArmy : deployedArmies) {
                _deployedArmies.add(deployedArmy);
            }
        }
    }

    private void _applyMilitarySettingsBundle(final MilitarySettingsBundle bundle) {
        if (bundle.hasGroupKey(MilitarySettingsBundle.Keys.DRAFT_RATES)) {
            List<Bundle> draftRateBundles = bundle.getGroup(MilitarySettingsBundle.Keys.DRAFT_RATES);
            for (Bundle draftRateBundle : draftRateBundles) {
                DraftRate draftRate = DraftRate.fromBundle((DraftRateBundle) draftRateBundle);
                if (draftRate.isSelected()) {
                    _draftRate = draftRate;
                    break;
                }
            }
        }

        if (bundle.hasKey(MilitarySettingsBundle.Keys.DRAFT_TARGET)) {
            _draftTarget = Util.parseInt(bundle.get(MilitarySettingsBundle.Keys.DRAFT_TARGET));
        }

        if (bundle.hasKey(MilitarySettingsBundle.Keys.WAGE_RATE)) {
            _wageRate = Util.parseInt(bundle.get(MilitarySettingsBundle.Keys.WAGE_RATE));
        }

        if (bundle.hasKey(MilitarySettingsBundle.Keys.OFFENSIVE_UNIT_COST)) {
            _offenseUnitCost = Util.parseInt(bundle.get(MilitarySettingsBundle.Keys.OFFENSIVE_UNIT_COST));
        }
        if (bundle.hasKey(MilitarySettingsBundle.Keys.DEFENSIVE_UNIT_COST)) {
            _defenseUnitCost = Util.parseInt(bundle.get(MilitarySettingsBundle.Keys.DEFENSIVE_UNIT_COST));
        }
        if (bundle.hasKey(MilitarySettingsBundle.Keys.ELITE_COST)) {
            _eliteCost = Util.parseInt(bundle.get(MilitarySettingsBundle.Keys.ELITE_COST));
        }
        if (bundle.hasKey(MilitarySettingsBundle.Keys.THIEF_COST)) {
            _thiefCost = Util.parseInt(bundle.get(MilitarySettingsBundle.Keys.THIEF_COST));
        }
    }

    private void _applyTradeSettingsBundle(final TradeSettingsBundle tradeSettingsBundle) {
        if (! tradeSettingsBundle.isValid()) { return; }

        _aidTaxRate = Util.parseFloat(tradeSettingsBundle.get(TradeSettingsBundle.Keys.TAX_RATE));
        _allowsIncomingAid = (Util.parseInt(tradeSettingsBundle.get(TradeSettingsBundle.Keys.AID_IS_BLOCKED)) == 0);
        _tradeBalance = Util.parseInt(tradeSettingsBundle.get(TradeSettingsBundle.Keys.TRADE_BALANCE));
    }

    private void _trimOldDeployedArmies() {
        if (_deployedArmies.size() == 0) {
            return;
        }

        List<DeployedArmy> trimmedDeployedArmies = new LinkedList<DeployedArmy>();
        for (DeployedArmy deployedArmy : _deployedArmies) {
            if (! deployedArmy.isExpired()) {
                trimmedDeployedArmies.add(deployedArmy);
            }
        }
        _deployedArmies = trimmedDeployedArmies;
    }

    private void _applyProvinceIntelBundle(final ProvinceIntelBundle intelBundle) {
        /*
            Integer defenseHome = null;
            {
                String defenseHomeString = intelBundle.get(ProvinceIntelBundle.Keys.DEFENSE_HOME);
                if (defenseHomeString.length() > 0) {
                    defenseHome = Util.parseInt(defenseHomeString);
                }
            }

            if (defenseHome != null) {
                EspionageArmyIntel.IntelAge intelAge = EspionageArmyIntel.intelAgeFromString(intelBundle.get(ProvinceIntelBundle.Keys.INTEL_AGE)); // TODO: Consider taking bundle date into account...

                _espionageArmyIntel.clear();
                final String[] deployedArmyKeys = new String[]{ProvinceIntelBundle.Keys.ARMY_ONE_OUT_UNTIL, ProvinceIntelBundle.Keys.ARMY_TWO_OUT_UNTIL, ProvinceIntelBundle.Keys.ARMY_THREE_OUT_UNTIL, ProvinceIntelBundle.Keys.ARMY_FOUR_OUT_UNTIL, ProvinceIntelBundle.Keys.ARMY_FIVE_OUT_UNTIL};

                Boolean defenseIsWithArmyHome = true;
                Long earliestArmyReturnTime = null;
                Long now = System.currentTimeMillis() / 1000L;
                for (String deployedArmyKey : deployedArmyKeys) {
                    if (intelBundle.hasKey(deployedArmyKey)) {
                        Long returnTime = Util.parseLong(intelBundle.get(deployedArmyKey));

                        if (returnTime > 0) {
                            defenseIsWithArmyHome = false;
                        }

                        if (earliestArmyReturnTime == null || earliestArmyReturnTime > returnTime) {
                            earliestArmyReturnTime = returnTime;
                        }

                        if (returnTime >= now) {
                            _espionageArmyIntel.add(EspionageArmyIntel.armyAway(defenseHome, intelAge, now));
                        }
                    }
                }

                if (defenseIsWithArmyHome) {
                    // Add a single intel entity with the defense-home.
                    _espionageArmyIntel.add(EspionageArmyIntel.armyHome(defenseHome, intelAge, now));
                }
                else {
                    // The defense-home property is only good until one of the armies returns...
                    for (EspionageArmyIntel espionageArmyIntel : _espionageArmyIntel) {
                        espionageArmyIntel.setExpirationTime(earliestArmyReturnTime);
                    }
                }
            }

            _trimOldDeployedArmies(); // Sure, why not...
        */

        if (intelBundle.hasGroupKey(ProvinceIntelBundle.Keys.ACTIVE_SPELLS_BUNDLE)) {
            final List<Bundle> activeSpellsBundle = intelBundle.getGroup(ProvinceIntelBundle.Keys.ACTIVE_SPELLS_BUNDLE);
            for (int i=0; i<activeSpellsBundle.size(); ++i) {
                final ProvinceIntelActiveSpellBundle activeSpellBundle = (ProvinceIntelActiveSpellBundle) activeSpellsBundle.get(i);
                final ActiveSpell activeSpell = ActiveSpell.fromBundle(activeSpellBundle);
                if (activeSpell != null) {
                    if (activeSpell.getExpirationTime() > 0) {
                        _activeSpells.put(activeSpell.getSpellName(), activeSpell);
                    }
                }
            }
        }
    }

    private void _applyScienceBundle(final ScienceBundle scienceBundle) {
        _science = Science.fromBundle(scienceBundle);
    }

    public static Province fromKingdomBundle(KingdomProvinceBundle bundle) {
        Province province = new Province();

        province._name = bundle.get(KingdomProvinceBundle.Keys.NAME);
        province._race = com.softwareverde.utopia.Province.getRaceForString(bundle.get(KingdomProvinceBundle.Keys.RACE));
        province._acres = Util.parseInt(bundle.get(KingdomProvinceBundle.Keys.ACRES));
        province._networth = Util.parseInt(bundle.get(KingdomProvinceBundle.Keys.NETWORTH));
        province._title = bundle.get(KingdomProvinceBundle.Keys.TITLE);

        return province;
    }

    private void _applyStateCouncilBundle(final StateCouncilBundle bundle) {
        _maxPopulation = Util.parseInt(bundle.get(StateCouncilBundle.Keys.MAX_POPULATION));
        _unemployedPeasants = Util.parseInt(bundle.get(StateCouncilBundle.Keys.UNEMPLOYED_PEASANTS));
        _unfilledJobs = Util.parseInt(bundle.get(StateCouncilBundle.Keys.UNFILLED_JOBS));
        _employmentPercent = Util.parseFloat(bundle.get(StateCouncilBundle.Keys.EMPLOYMENT_PERCENT)) / 100.0f;
        _income = Util.parseInt(bundle.get(StateCouncilBundle.Keys.INCOME));
        _militaryWages = Util.parseInt(bundle.get(StateCouncilBundle.Keys.MILITARY_WAGES));
        // _networth = Util.parseInt(bundle.get(UtopiaParser.StateCouncilBundle.Keys.NETWORTH));
        _honor = Util.parseInt(bundle.get(StateCouncilBundle.Keys.HONOR));
    }

    private void _applySpellResultBundle(final SpellResultBundle spellResultBundle) {
        final Boolean wasSuccess = (Util.parseInt(spellResultBundle.get(SpellResultBundle.Keys.WAS_SUCCESS)) > 0);
        if (! wasSuccess) { return; }

        final ActiveSpell activeSpell = ActiveSpell.fromBundle(spellResultBundle);
        if ( activeSpell == null) { return; }

        _activeSpells.put(activeSpell.getSpellName(), activeSpell);
    }

    public void _updateActiveSpells(final List<ActiveSpell> activeSpellList) {
        _activeSpells.clear();
        for (final ActiveSpell activeSpell : activeSpellList) {
            _activeSpells.put(activeSpell.getSpellName(), activeSpell);
        }
    }

    public String getName() {
        // return _name.trim()
        // TODO: Never store an unclean province name.
        return UtopiaParser.cleanProvinceName(_name);
    }
    public void setName(String name) { _name = name; }
    public Race getRace() { return _race; }
    public String getRulerName() { return _rulerName; }

    public String getRoyalCommands() { return _royalCommands; }

    public Integer getAcres() { return _acres; }
    public Float getBuildingEfficiency() { return _buildingEfficiency; }
    public Integer getFood() { return _food; }

    public Integer getMoney() { return _money; }
    public Integer getRunes() { return _runes; }
    public Integer getStealth() { return _stealth; }
    public Integer getMana() { return _mana; }

    public Integer getTradeBalance() { return _tradeBalance; }

    public Integer getPeasants() { return _peasants; }
    public Integer getSoldiers() { return _soldiers; }
    public Integer getDefensiveUnits() { return _defensiveUnits; }
    public Integer getOffensiveUnits() { return _offensiveUnits; }
    public Integer getElites() { return _elites; }
    public Integer getPrisoners() { return _prisoners; }
    public Integer getHorses() { return _horses; }

    public Integer getSoldiersHome() {
        Integer soldiers = _soldiers;
        for (final DeployedArmy deployedArmy : _deployedArmies) {
            soldiers -= deployedArmy.getSoldiers();
        }
        return soldiers;
    }

    public Integer getOffensiveUnitsHome() {
        Integer offensiveUnits = _offensiveUnits;
        for (final DeployedArmy deployedArmy : _deployedArmies) {
            offensiveUnits -= deployedArmy.getOffensiveUnits();
        }
        return offensiveUnits;
    }

    public Integer getElitesHome() {
        Integer elites = _elites;
        for (final DeployedArmy deployedArmy : _deployedArmies) {
            elites -= deployedArmy.getElites();
        }
        return elites;
    }

    public Integer getHorsesHome() {
        Integer horses = _horses;
        for (final DeployedArmy deployedArmy : _deployedArmies) {
            horses -= deployedArmy.getHorses();
        }
        return horses;
    }

    public Integer getThieves() { return _thieves; }
    public Integer getWizards() { return _wizards; }

    public Building getBuilding(Building.Type type) {
        switch (type) {
            case BARREN: return _barrenLand;
            case HOMES: return _homes;
            case FARMS: return _farms;
            case MILLS: return _mills;
            case BANKS: return _banks;
            case TRAINING_GROUNDS: return _trainingGrounds;
            case ARMORIES: return _armories;
            case BARRACKS: return _barracks;
            case FORTS: return _forts;
            case GUARD_STATIONS: return _guardStations;
            case HOSPITALS: return _hospitals;
            case GUILDS: return _guilds;
            case TOWERS: return _towers;
            case THIEVES_DENS: return _thievesDens;
            case WATCHTOWERS: return _watchTowers;
            case LABORATORIES: return _laboratories;
            case UNIVERSITIES: return _universities;
            case STABLES: return _stables;
            case DUNGEONS: return _dungeons;
        }

        return null;
    }
    public Integer[] getBuildingInProgress(final Building.Type type) {
        switch (type) {
            case BARREN: return _barracksInProgress;
            case HOMES: return _homesInProgress;
            case FARMS: return _farmsInProgress;
            case MILLS: return _millsInProgress;
            case BANKS: return _banksInProgress;
            case TRAINING_GROUNDS: return _trainingGroundsInProgress;
            case ARMORIES: return _armoriesInProgress;
            case BARRACKS: return _barracksInProgress;
            case FORTS: return _fortsInProgress;
            case GUARD_STATIONS: return _guardStationsInProgress;
            case HOSPITALS: return _hospitalsInProgress;
            case GUILDS: return _guildsInProgress;
            case TOWERS: return _towersInProgress;
            case THIEVES_DENS: return _thievesDensInProgress;
            case WATCHTOWERS: return _watchTowersInProgress;
            case LABORATORIES: return _laboratoriesInProgress;
            case UNIVERSITIES: return _universitiesInProgress;
            case STABLES: return _stablesInProgress;
            case DUNGEONS: return _dungeonsInProgress;
        }

        return Util.initializeIntegerArray(24);
    }

    public Integer getMaxPopulation() { return _maxPopulation; }
    public Integer getUnemployedPeasants() { return _unemployedPeasants; }
    public Integer getUnfilledJobs() { return _unfilledJobs; }
    public Float getEmploymentPercent() { return _employmentPercent; }
    public Integer getIncome() { return _income; }
    public Integer getMilitaryWages() { return _militaryWages; }
    public Integer getNetworth() { return _networth; }
    public Integer getMaxPeasants() {
        if (_maxPopulation == null) { return null; }

        return Util.coalesce(_maxPopulation) - (Util.coalesce(_getMilitaryPopulation()) + Util.coalesce(_thieves) + Util.coalesce(_getTotalThievesInProgress()) + Util.coalesce(_wizards));
    }

    public Integer getMaxHorses() {
        return ((_stables != null ? _stables.getCount() : 0) * 80); // NOTE: Not affected by Building Effectiveness.
    }

    public Integer getMaxPrisoners() {
        return (int) ((_dungeons != null ? _dungeons.getCount() : 0) * 20);  // NOTE: Not affected by Building Effectiveness.
    }

    private Integer _getMilitaryPopulation() {
        if (_soldiers == null || _offensiveUnits == null || _defensiveUnits == null || _elites == null) {
            return null;
        }

        return _soldiers + _offensiveUnits + _getTotalOffensiveUnitsInProgress() + _defensiveUnits + _getTotalDefensiveUnitsInProgress() + _elites + _getTotalElitesInProgress();
    }
    public Integer getMilitaryPopulation() {
        return _getMilitaryPopulation();
    }

    public String getTitle() { return _title; }
    public Integer getHonor() { return _honor; }
    public Integer getNwpa() {
        return _networth / _acres;
    }

    private Integer _getTotalOffensiveUnitsInProgress() {
        Integer sum = 0;
        for (Integer i = 0; i < _offensiveUnitsInProgress.length; i++) {
            sum += _offensiveUnitsInProgress[i];
        }
        return sum;
    }
    public Integer[] getOffensiveUnitsInProgress() {
        return _offensiveUnitsInProgress;
    }
    public Integer getTotalOffensiveUnitsInProgress() {
        return _getTotalOffensiveUnitsInProgress();
    }

    private Integer _getTotalDefensiveUnitsInProgress() {
        Integer sum = 0;
        for (Integer i = 0; i < _defensiveUnitsInProgress.length; i++) {
            sum += _defensiveUnitsInProgress[i];
        }
        return sum;
    }
    public Integer[] getDefensiveUnitsInProgress() {
        return _defensiveUnitsInProgress;
    }
    public Integer getTotalDefensiveUnitsInProgress() {
        return _getTotalDefensiveUnitsInProgress();
    }

    private Integer _getTotalElitesInProgress() {
        Integer sum = 0;
        for (Integer i = 0; i < _elitesInProgress.length; i++) {
            sum += _elitesInProgress[i];
        }
        return sum;
    }
    public Integer[] getElitesInProgress() {
        return _elitesInProgress;
    }
    public Integer getTotalElitesInProgress() {
        return _getTotalElitesInProgress();
    }

    private Integer _getTotalThievesInProgress() {
        Integer sum = 0;
        for (Integer i = 0; i < _thievesInProgress.length; i++) {
            sum += _thievesInProgress[i];
        }
        return sum;
    }
    public Integer[] getThievesInProgress() {
        return _thievesInProgress;
    }
    public Integer getTotalThievesInProgress() {
        return _getTotalThievesInProgress();
    }

    public Boolean hasArmiesDeployed() {
        synchronized (_deployedArmies) {
            for (DeployedArmy deployedArmy : _deployedArmies) {
                if (! deployedArmy.isExpired()) {
                    return true;
                }
            }

            for (EspionageArmyIntel espionageArmyIntel : _espionageArmyIntel) {
                // At-home intel does not tell us anything...
                if (espionageArmyIntel.isDefenseWithArmyHome()) {
                    continue;
                }

                if (! espionageArmyIntel.isExpired()) {
                    return true;
                }
            }
        }

        return false;
    }
    // Returns a copy of _deployedArmies
    public List<DeployedArmy> getDeployedArmies() {
        synchronized (_deployedArmies) {
            List<DeployedArmy> deployedArmies = new LinkedList<DeployedArmy>();
            for (DeployedArmy deployedArmy : _deployedArmies) {
                if (! deployedArmy.isExpired()) {
                    deployedArmies.add(deployedArmy);
                }
            }
            List<DeployedArmy> espionageIntelArmies = new LinkedList<DeployedArmy>();
            for (EspionageArmyIntel espionageArmyIntel : _espionageArmyIntel) {
                if (! espionageArmyIntel.isDefenseWithArmyHome()) {
                    espionageIntelArmies.add(DeployedArmy.fromBundle(espionageArmyIntel));
                }
            }

            // If deployedArmies is GTE espionageArmies, then rely on deployedArmies. This is because we
            //  cannot guarantee that this province has had its espionageIntel downloaded.
            if (deployedArmies.size() >= espionageIntelArmies.size() && deployedArmies.size() > 0) {
                return deployedArmies;
            }
            else {
                return espionageIntelArmies;
            }
        }
    }

    public Integer getSpellDuration(String spellName) {
        if (! _activeSpells.containsKey(spellName)) return 0;

        return (int) (_activeSpells.get(spellName).getExpirationTime() - (System.currentTimeMillis() / 1000L));
    }

    public DraftRate getDraftRate() {
        return _draftRate;
    }
    public Integer getDraftTarget() {
        return _draftTarget;
    }
    public Integer getMilitaryWageRate() { return _wageRate; }

    public void setUtopiaId(Integer utopiaId) {
        _utopiaId = utopiaId;
    }
    public Integer getUtopiaId() {
        return _utopiaId;
    }

    public void setId(Integer id) {
        _id = id;
    }
    public Integer getId() {
        return _id;
    }

    public void setKingdomIdentifier(Kingdom.Identifier kingdomIdentifier) {
        _kingdomIdentifier = new Kingdom.Identifier(kingdomIdentifier.getKingdomId(), kingdomIdentifier.getIslandId());
    }
    public Kingdom.Identifier getKingdomIdentifier() {
        return _kingdomIdentifier;
    }

    public Integer getTotalOffense() {
        return _offensivePoints;
    }
    public Integer getTotalOffenseAtHome() {
        // TODO: Implement.
        //  NOTE: More difficult than appears. Will probably need to make a webrequest.
        return _offensivePoints;
    }
    public Integer getTotalDefense() {
        return _defensivePoints;
    }
    public Integer getTotalDefenseAtHome() {

        Integer defensePointsAtHome = _defensivePoints; // Likely null or from a SoT.

        // Always rely on espionage data for defense if it is not expired.
        for (EspionageArmyIntel espionageArmyIntel : _espionageArmyIntel) {
            if (! espionageArmyIntel.isExpired()) {
                defensePointsAtHome = espionageArmyIntel.getDefense();
                break;
            }
        }

        return defensePointsAtHome;
    }

    public Float getOffenseModifier() { return _offenseModifier; }
    public void setOffenseModifier(Float offenseModifier) { _offenseModifier = offenseModifier; }

    public Integer getMercenaryCost() { return _mercenaryCost; }
    public void setMercenaryCost(Integer cost) { _mercenaryCost = cost; }

    public Float getMercenaryRate() { return _mercenaryRate; }
    public void setMercenaryRate(Float rate) { _mercenaryRate = rate; }

    public Integer getMinConquestNetworth() { return _minConquestNetworth; }
    public void setMinConquestNetworth(Integer minNetworth) { _minConquestNetworth = minNetworth; }

    public Integer getMaxConquestNetworth() { return _maxConquestNetworth; }
    public void setMaxConquestNetworth(Integer maxNetworth) { _maxConquestNetworth = maxNetworth; }

    public Boolean allowsIncomingAid() { return _allowsIncomingAid; }
    public Float getAidTaxRate() { return _aidTaxRate; }

    public Integer getBuildCost() { return _buildCost; }
    public Integer getRazeCost() { return _razeCost; }
    public Integer getBuildCredits() { return _buildCredits; }
    public Integer getBuildTime() { return _buildTime; }

    public Integer getOffenseUnitCost() { return _offenseUnitCost; }
    public Integer getDefenseUnitCost() { return _defenseUnitCost; }
    public Integer getEliteCost() { return _eliteCost; }
    public Integer getThiefCost() { return _thiefCost; }

    public Integer getExplorationSoldiersCost() { return _explorationSoldiersCost; }
    public Integer getExplorationGoldCost() { return _explorationGoldCost; }
    public Integer getExplorationAcresAvailable() { return _explorationAcresAvailable; }
    public Integer getExplorationAcresInProgress() { return _explorationAcresInProgress; }

    public List<ActiveSpell> getActiveSpells() {
        Long now = System.currentTimeMillis() / 1000L;

        List<ActiveSpell> activeSpells = new ArrayList<ActiveSpell>();
        for (String spellName : _activeSpells.keySet()) {
            final ActiveSpell activeSpell = _activeSpells.get(spellName);
            final Long expirationTime = activeSpell.getExpirationTime();

            if (expirationTime > now) {
                activeSpells.add(activeSpell);
            }
        }

        return activeSpells;
    }

    public Integer getScientistCount(final Science.Type scienceType) {
        if (_science == null) { return null; }

        switch (scienceType) {
            case ALCHEMY:       return _science.getAlchemyScientistCount();
            case TOOLS:         return _science.getToolScientistCount();
            case HOUSING:       return _science.getHousingScientistCount();
            case FOOD:          return _science.getFoodScientistCount();
            case MILITARY:      return _science.getMilitaryScientistCount();
            case CRIME:         return _science.getCrimeScientistCount();
            case CHANNELING:    return _science.getChannelingScientistCount();
        }
        return null;
    }

    public String getScienceEffect(final Science.Type scienceType) {
        if (_science == null) { return null; }

        switch (scienceType) {
            case ALCHEMY:       return _science.getAlchemyEffect();
            case TOOLS:         return _science.getToolEffect();
            case HOUSING:       return _science.getHousingEffect();
            case FOOD:          return _science.getFoodEffect();
            case MILITARY:      return _science.getMilitaryEffect();
            case CRIME:         return _science.getCrimeEffect();
            case CHANNELING:    return _science.getChannelingEffect();
        }
        return null;

    }

    public List<Scientist> getScientists() {
        if (_science == null) { return new ArrayList<Scientist>(); }
        return _science.getScientists();
    }

    public Boolean hasPlague() {
        return _hasPlague;
    }

    @Override
    public String toString() {
        return _name +" "+ _kingdomIdentifier;
    }
}
