package com.softwareverde.utopia.database;

import com.softwareverde.utopia.Util;
import com.softwareverde.utopia.Building;
import com.softwareverde.utopia.Dragon;
import com.softwareverde.utopia.Kingdom;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.bundle.BuildingBundle;
import com.softwareverde.utopia.bundle.BuildingsBundle;
import com.softwareverde.utopia.bundle.DeployedArmiesBundle;
import com.softwareverde.utopia.bundle.DeployedArmyBundle;
import com.softwareverde.utopia.bundle.ThroneBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UtopiaDatabaseV1 implements UtopiaDatabase {
    public static final int DATABASE_VERSION = 1;               // NOTE: Should remain int (primitive) for iOS export.
    public static final String DATABASE_NAME = "utopia.db";

    private static HashMap<String, String> _PROVINCE_ATTRIBUTE_TO_THRONE_BUNDLE_MAP = new HashMap<String, String>() {{
        put("race", ThroneBundle.Keys.RACE);
        put("ruler_name", ThroneBundle.Keys.RULER_NAME);
        put("acres", ThroneBundle.Keys.LAND);
        put("food", ThroneBundle.Keys.FOOD);
        put("money", ThroneBundle.Keys.GOLD);
        put("runes", ThroneBundle.Keys.RUNES);
        put("trade_balance", ThroneBundle.Keys.TRADE_BALANCE);
        put("peasants", ThroneBundle.Keys.PEASANTS);
        put("soldiers", ThroneBundle.Keys.SOLDIERS);
        put("defensive_units", ThroneBundle.Keys.DEFENSIVE_UNITS);
        put("offensive_units", ThroneBundle.Keys.OFFENSIVE_UNITS);
        put("elites", ThroneBundle.Keys.ELITES);
        put("horses", ThroneBundle.Keys.HORSES);
        put("prisoners", ThroneBundle.Keys.PRISONERS);
        put("thieves", ThroneBundle.Keys.THIEVES);
        put("wizards", ThroneBundle.Keys.WIZARDS);
        put("title", ThroneBundle.Keys.TITLE);
        put("networth", ThroneBundle.Keys.NETWORTH);
        put("defense", ThroneBundle.Keys.DEFENSIVE_POINTS);
        put("offense", ThroneBundle.Keys.OFFENSIVE_POINTS);
    }};

    private static HashMap<String, BuildingAttributeMapItem> _PROVINCE_ATTRIBUTE_TO_BUILDING_BUNDLE_MAP = new HashMap<String, BuildingAttributeMapItem>() {{
        put("barren_land", new BuildingAttributeMapItem(BuildingsBundle.Keys.BARREN, Building.Type.BARREN));
        put("homes", new BuildingAttributeMapItem(BuildingsBundle.Keys.HOMES, Building.Type.HOMES));
        put("farms", new BuildingAttributeMapItem(BuildingsBundle.Keys.FARMS, Building.Type.FARMS));
        put("mills", new BuildingAttributeMapItem(BuildingsBundle.Keys.MILLS, Building.Type.MILLS));
        put("banks", new BuildingAttributeMapItem(BuildingsBundle.Keys.BANKS, Building.Type.BANKS));
        put("training_grounds", new BuildingAttributeMapItem(BuildingsBundle.Keys.TRAINING_GROUNDS, Building.Type.TRAINING_GROUNDS));
        put("armories", new BuildingAttributeMapItem(BuildingsBundle.Keys.ARMORIES, Building.Type.ARMORIES));
        put("barracks", new BuildingAttributeMapItem(BuildingsBundle.Keys.BARRACKS, Building.Type.BARRACKS));
        put("forts", new BuildingAttributeMapItem(BuildingsBundle.Keys.FORTS, Building.Type.FORTS));
        put("guard_stations", new BuildingAttributeMapItem(BuildingsBundle.Keys.GUARD_STATIONS, Building.Type.GUARD_STATIONS));
        put("hospitals", new BuildingAttributeMapItem(BuildingsBundle.Keys.HOSPITALS, Building.Type.HOSPITALS));
        put("guilds", new BuildingAttributeMapItem(BuildingsBundle.Keys.GUILDS, Building.Type.GUILDS));
        put("towers", new BuildingAttributeMapItem(BuildingsBundle.Keys.TOWERS, Building.Type.TOWERS));
        put("thieves_dens", new BuildingAttributeMapItem(BuildingsBundle.Keys.THIEVES_DENS, Building.Type.THIEVES_DENS));
        put("watch_towers", new BuildingAttributeMapItem(BuildingsBundle.Keys.WATCH_TOWERS, Building.Type.WATCHTOWERS));
        put("laboratories", new BuildingAttributeMapItem(BuildingsBundle.Keys.LABORATORIES, Building.Type.LABORATORIES));
        put("universities", new BuildingAttributeMapItem(BuildingsBundle.Keys.UNIVERSITIES, Building.Type.UNIVERSITIES));
        put("stables", new BuildingAttributeMapItem(BuildingsBundle.Keys.STABLES, Building.Type.STABLES));
        put("dungeons", new BuildingAttributeMapItem(BuildingsBundle.Keys.DUNGEONS, Building.Type.DUNGEONS));
    }};

    private static Long _getTimestamp() {
        return System.currentTimeMillis() / 1000L;
    }

    private static <T> String _stringify(T object) {
        if (object == null) { return null; }
        return object.toString();
    }

    private static class BuildingAttributeMapItem {
        public String buildingKey;
        public Building.Type buildingType;
        public BuildingAttributeMapItem(String buildingKey, Building.Type buildingType) {
            this.buildingKey = buildingKey;
            this.buildingType = buildingType;
        }
    }

    private final Object _mutex = new Object();
    private Database _database;

    private Long _getProvinceId(final Province province) {
        final Integer utopiaId = province.getUtopiaId();
        final Kingdom.Identifier kingdomIdentifier = province.getKingdomIdentifier();
        final String provinceName = province.getName();

        // NOTE: Preferring fetch by name/kingdomIdentifier as a workaround for a bug where utopiaIds could not be retrieved due to duplicate provinces.
        if ( (provinceName != null) && (kingdomIdentifier != null && kingdomIdentifier.isValid()) ) {
            return _getProvinceIdByKingdomIdentifier(provinceName, province.getKingdomIdentifier());
        }

        if (utopiaId != null && utopiaId > 0) {
            final Long provinceId = _getProvinceIdByUtopiaId(utopiaId);
            if (provinceId != null && provinceId > 0) { return provinceId; }
        }

        return 0L;
    }

    private Long _getProvinceIdByKingdomIdentifier(String provinceName, Kingdom.Identifier kingdomIdentifier) {
        Long provinceId = 0L;

        if (provinceName == null) {
            provinceName = "";
        }

        if (kingdomIdentifier == null) {
            kingdomIdentifier = new Kingdom.Identifier(0, 0);
        }

        final List<Database.Row> rows = _database.query("SELECT id FROM provinces WHERE kingdom = ? AND island = ? AND name = ?", new String[]{kingdomIdentifier.getKingdomId().toString(), kingdomIdentifier.getIslandId().toString(), provinceName});
        if (rows.size() > 0) {
            provinceId = Util.parseLong(rows.get(0).getValue("id"));
        }

        return provinceId;
    }

    private Long _getKingdomIdByKingdomIdentifier(Kingdom.Identifier kingdomIdentifier) {
        Long kingdomId = 0L;

        if (kingdomIdentifier == null) {
            kingdomIdentifier = new Kingdom.Identifier(0, 0);
        }

        final List<Database.Row> rows = _database.query("SELECT id FROM kingdoms WHERE kingdom = ? AND island = ?", new String[]{kingdomIdentifier.getKingdomId().toString(), kingdomIdentifier.getIslandId().toString()});
        if (rows.size() > 0) {
            kingdomId = Util.parseLong(rows.get(0).getValue("id"));
        }

        return kingdomId;
    }

    private Long _getProvinceIdByUtopiaId(Integer utopiaId) {
        Long provinceId = 0L;

        if (utopiaId == null) {
            utopiaId = 0;
        }

        final List<Database.Row> rows = _database.query("SELECT id FROM provinces WHERE utopia_id = ?", new String[]{ utopiaId.toString() });
        if (rows.size() > 0) {
            provinceId = Util.parseLong(rows.get(0).getValue("id"));
        }

        return provinceId;
    }

    private void _createAllTables() {
        _database.executeDdl("CREATE TABLE provinces (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, utopia_id INTEGER, kingdom INTEGER, island INTEGER)");
        _database.executeDdl("CREATE TABLE province_attributes (id INTEGER PRIMARY KEY AUTOINCREMENT, province_id INTEGER, key TEXT, value TEXT, timestamp INTEGER, FOREIGN KEY (province_id) REFERENCES provinces(id))");
        _database.executeDdl("CREATE TABLE kingdoms (id INTEGER PRIMARY KEY AUTOINCREMENT, kingdom INTEGER, island INTEGER, dragon_type TEXT, dragon_health INTEGER)");
        _database.executeDdl("CREATE TABLE deployed_armies (id INTEGER PRIMARY KEY AUTOINCREMENT, province_id INTEGER, generals INTEGER, soldiers INTEGER, elites INTEGER, offensive_units INTEGER, horses INTEGER, acres INTEGER, return_time INTEGER, FOREIGN KEY (province_id) REFERENCES provinces(id))");
        // TODO:
        // _database.executeDdl("CREATE TABLE executed_ops (id INTEGER PRIMARY KEY AUTOINCREMENT, target_province_id INTEGER, timestamp INTEGER, result_text TEXT, op_data TEXT FOREIGN KEY (target_province_id) REFERENCES provinces(id))");
    }

    private void _dropAllTables() {
        _database.executeDdl("DROP TABLE IF EXISTS provinces");
        _database.executeDdl("DROP TABLE IF EXISTS province_attributes");
        _database.executeDdl("DROP TABLE IF EXISTS kingdoms");
        _database.executeDdl("DROP TABLE IF EXISTS deployed_armies");
        // _database.executeDdl("DROP TABLE IF EXISTS executed_ops");
    }

    private Long _insertProvince(final Province province) {
        final Integer utopiaId = province.getUtopiaId();
        final Kingdom.Identifier kingdomIdentifier = province.getKingdomIdentifier();
        if (kingdomIdentifier == null || (! kingdomIdentifier.isValid())) {
            throw new RuntimeException("Utopia DB - Invalid KD Identifier");
        }

        final String provinceName = province.getName();
        final String utopiaIdString = (utopiaId != null ? utopiaId.toString() : null);
        final String kingdomString = kingdomIdentifier.getKingdomId().toString();
        final String islandString = kingdomIdentifier.getIslandId().toString();

        { // Duplicate-Province Sanity Check
            final List<Database.Row> rows = _database.query("SELECT id FROM provinces WHERE name = ? AND kingdom = ? AND island = ?", new String[]{ provinceName, kingdomString, islandString });
            if (rows.size() > 0) {
                throw new RuntimeException("Utopia DB - Duplicate Province Exception");
                // return cursor.getLong(cursor.getColumnIndex("id"));
            }
        }

        _database.executeSql("INSERT INTO provinces (name, utopia_id, kingdom, island) VALUES (?, ?, ?, ?)", new String[]{ provinceName, utopiaIdString, kingdomString, islandString });

        return _database.getInsertId();
    }

    private Long _insertKingdom(Kingdom kingdom) {
        final Kingdom.Identifier kingdomIdentifier = kingdom.getIdentifier();
        if (kingdomIdentifier != null) {
            final String kingdomString = kingdomIdentifier.getKingdomId().toString();
            final String islandString = kingdomIdentifier.getIslandId().toString();

            String dragonType = null;
            String dragonHealth = null;
            if (kingdom.hasDragon()) {
                Dragon dragon = kingdom.getDragon();
                dragonType = (""+ dragon.getNamedType()).toUpperCase();
                dragonHealth = ""+ dragon.getHealth();
            }

            _database.executeSql("INSERT INTO kingdoms (kingdom, island, dragon_type, dragon_health) VALUES (?, ?, ?, ?)", new String[]{ kingdomString, islandString, dragonType, dragonHealth });
        }

        return _database.getInsertId();
    }

    private void _updateProvince(final Long id, final Province province) {
        final Integer utopiaId = province.getUtopiaId();
        final Kingdom.Identifier kingdomIdentifier = province.getKingdomIdentifier();
        if (utopiaId != null || kingdomIdentifier != null) {
            String utopiaIdString = null;
            String kingdomString = null;
            String islandString = null;

            if (utopiaId != null) {
                utopiaIdString = utopiaId.toString();
            }
            if (kingdomIdentifier != null) {
                kingdomString = kingdomIdentifier.getKingdomId().toString();
                islandString = kingdomIdentifier.getIslandId().toString();
            }

            _database.executeSql("UPDATE provinces SET name = ?, utopia_id = ?, kingdom = ?, island = ? WHERE id = ?", new String[]{ province.getName(), utopiaIdString, kingdomString, islandString, id.toString() });
        }
    }

    private void _updateKingdom(final Long id, final Kingdom kingdom) {
        String dragonType = null;
        String dragonHealth = null;

        if (kingdom.hasDragon()) {
            Dragon dragon = kingdom.getDragon();
            dragonType = dragon.getNamedType().toUpperCase();
            if (dragon.getHealth() != null) {
                dragonHealth = dragon.getHealth().toString();
            }
        }

        _database.executeSql("UPDATE kingdoms SET dragon_type = ?, dragon_health = ? WHERE id = ?", new String[]{dragonType, dragonHealth, id.toString()});
    }

    private <T extends Object> void _storeProvinceAttribute (final Long provinceId, final String key, final T value) {
        String stringValue = "";

        if (value != null) {
            if (value instanceof Building) {
                stringValue = ((Building) value).getCount().toString();
            }
            else {
                stringValue = value.toString();
            }
        }

        if (stringValue.length() == 0) {
            return;
        }

        final List<Database.Row> rows = _database.query("SELECT id FROM province_attributes WHERE province_id = ? AND key = ?", new String[]{ provinceId.toString(), key });
        if (rows.size() > 0) {
            final Long provinceAttributeId = Util.parseLong(rows.get(0).getValue("id"));

            _database.executeSql("UPDATE province_attributes SET value = ?, timestamp = ? WHERE id = ?", new String[]{ stringValue, _getTimestamp().toString(), provinceAttributeId.toString() });
        }
        else {
            _database.executeSql("INSERT INTO province_attributes (province_id, key, value, timestamp) VALUES (?, ?, ?, ?)", new String[]{ provinceId.toString(), key, stringValue, _getTimestamp().toString() });
        }
    }

    private void _updateProvinceUtopiaId(final Long provinceId, final Integer utopiaId) {
        _database.executeSql("UPDATE provinces SET utopia_id = ? WHERE id = ?", new String[]{ utopiaId.toString(), provinceId.toString() });
    }

    private void _clearDeployedArmies(final Long provinceId) {
        _database.executeSql("DELETE FROM deployed_armies WHERE province_id = ?", new String[]{ provinceId.toString() });
    }

    private void _clearOldDeployedArmies(final Long provinceId) {
        _database.executeSql("DELETE FROM deployed_armies WHERE province_id = ? AND return_time < ?", new String[]{ provinceId.toString(), _getTimestamp().toString() });
    }

    private void _storeDeployedArmy(final Long provinceId, final Province.DeployedArmy deployedArmy) {
        _insertDeployedArmy(provinceId, deployedArmy);
    }

    private void _updateDeployedArmy(final Long provinceId, final Long deployedArmyId, final Province.DeployedArmy deployedArmy) {
        _database.executeSql("UPDATE deployed_armies SET province_id = ?, generals = ?, soldiers = ?, elites = ?, offensive_units = ?, horses = ?, acres = ?, return_time = ?) WHERE id = ?", new String[] {
                _stringify(provinceId), _stringify(deployedArmy.getGenerals()), _stringify(deployedArmy.getSoldiers()), _stringify(deployedArmy.getElites()),
                _stringify(deployedArmy.getOffensiveUnits()), _stringify(deployedArmy.getHorses()), _stringify(deployedArmy.getAcres()),
                _stringify(deployedArmy.getReturnTimeFromEpoch()), _stringify(deployedArmyId)
        });
    }

    private Long _insertDeployedArmy(final Long provinceId, final Province.DeployedArmy deployedArmy) {
        _database.executeSql("INSERT INTO deployed_armies (province_id, generals, soldiers, elites, offensive_units, horses, acres, return_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", new String[]{_stringify(provinceId), _stringify(deployedArmy.getGenerals()), _stringify(deployedArmy.getSoldiers()), _stringify(deployedArmy.getElites()), _stringify(deployedArmy.getOffensiveUnits()), _stringify(deployedArmy.getHorses()), _stringify(deployedArmy.getAcres()), _stringify(deployedArmy.getReturnTimeFromEpoch())});
        return _database.getInsertId();
    }

    // NOTE: Produces an incomplete bundle. (e.x. lacks current resources and date)
    private DeployedArmiesBundle _getDeployedArmies(final Long provinceId) {
        final List<DeployedArmyBundle> deployedArmyBundles = new ArrayList<DeployedArmyBundle>();

        final List<Database.Row> rows = _database.query("SELECT * FROM deployed_armies WHERE province_id = ?", new String[]{ provinceId.toString() });
        for (final Database.Row row : rows) {
            final DeployedArmyBundle deployedArmyBundle = new DeployedArmyBundle();
            deployedArmyBundle.put(DeployedArmyBundle.Keys.GENERALS, row.getValue("generals"));
            deployedArmyBundle.put(DeployedArmyBundle.Keys.SOLDIERS, row.getValue("soldiers"));
            deployedArmyBundle.put(DeployedArmyBundle.Keys.DEFENSIVE_UNITS, "0");
            deployedArmyBundle.put(DeployedArmyBundle.Keys.OFFENSIVE_UNITS, row.getValue("offensive_units"));
            deployedArmyBundle.put(DeployedArmyBundle.Keys.ELITE_UNITS, row.getValue("elites"));
            deployedArmyBundle.put(DeployedArmyBundle.Keys.HORSES, row.getValue("horses"));
            deployedArmyBundle.put(DeployedArmyBundle.Keys.CAPTURED_LAND, row.getValue("acres"));
            deployedArmyBundle.put(DeployedArmyBundle.Keys.RETURN_TIME, row.getValue("return_time"));
            deployedArmyBundles.add(deployedArmyBundle);
        }

        final DeployedArmiesBundle deployedArmiesBundle = new DeployedArmiesBundle();

        for (final DeployedArmyBundle deployedArmyBundle : deployedArmyBundles) {
            deployedArmiesBundle.addToGroup(DeployedArmiesBundle.Keys.DEPLOYED_ARMIES_GROUP, deployedArmyBundle);
        }

        return deployedArmiesBundle;
    }

    private Integer _getProvinceUtopiaId(final Province province) {
        final Long provinceId = _getProvinceId(province);
        return _getProvinceUtopiaId(provinceId);
    }

    private Integer _getProvinceUtopiaId(final Long provinceId) {
        Integer utopiaId = null;

        final List<Database.Row> rows = _database.query("SELECT id, utopia_id FROM provinces WHERE id = ?", new String[]{ provinceId.toString() });
        if (rows.size() > 0) {
            utopiaId = Util.parseInt(rows.get(0).getValue("utopia_id"));
        }

        return utopiaId;
    }

    private ThroneBundle _makeThroneBundleFromProvinceId(final Long provinceId) {
        final List<Database.Row> provinceRows = _database.query("SELECT * FROM provinces WHERE id = ?", new String[]{provinceId.toString()});
        if (provinceRows.size() == 0) {
            return null;
        }

        final ThroneBundle throneBundle = new ThroneBundle();

        throneBundle.put(ThroneBundle.Keys.PROVINCE_NAME, provinceRows.get(0).getValue("name"));
        throneBundle.put(ThroneBundle.Keys.KINGDOM, provinceRows.get(0).getValue("kingdom"));
        throneBundle.put(ThroneBundle.Keys.ISLAND, provinceRows.get(0).getValue("island"));

        final List<Database.Row> provinceAttributeRows = _database.query("SELECT * FROM province_attributes WHERE province_id = ?", new String[]{ provinceId.toString() });
        for (final Database.Row provinceAttributeRow : provinceAttributeRows) {
            final String key = provinceAttributeRow.getValue("key");
            final String value = provinceAttributeRow.getValue("value");

            if (_PROVINCE_ATTRIBUTE_TO_THRONE_BUNDLE_MAP.containsKey(key)) {
                throneBundle.put(_PROVINCE_ATTRIBUTE_TO_THRONE_BUNDLE_MAP.get(key), value);
            }
        }

        return throneBundle;
    }

    private BuildingsBundle _makeBuildingBundleFromProvinceId(final Long provinceId) {
        BuildingsBundle buildingsBundle = new BuildingsBundle();

        final List<Database.Row> provinceRows = _database.query("SELECT * FROM provinces WHERE id = ?", new String[]{ provinceId.toString() });
        if (provinceRows.size() == 0) {
            return null;
        }

        final List<Database.Row> provinceAttributeRows = _database.query("SELECT * FROM province_attributes WHERE province_id = ?", new String[]{ provinceId.toString() });

        // Calculate Total Land
        Integer totalLand = 0;
        for (final Database.Row provinceAttributeRow : provinceAttributeRows) {
            final String key = provinceAttributeRow.getValue("key");
            final String value = provinceAttributeRow.getValue("value");

            if (_PROVINCE_ATTRIBUTE_TO_BUILDING_BUNDLE_MAP.containsKey(key)) {
                totalLand += Util.parseInt(value);
            }
        }

        // Store buildings in bundle...
        for (final Database.Row provinceAttributeRow : provinceAttributeRows) {
            final String key = provinceAttributeRow.getValue("key");
            final String value = provinceAttributeRow.getValue("value");

            if (_PROVINCE_ATTRIBUTE_TO_BUILDING_BUNDLE_MAP.containsKey(key)) {
                final BuildingAttributeMapItem buildingAttributeMapItem = _PROVINCE_ATTRIBUTE_TO_BUILDING_BUNDLE_MAP.get(key);
                final Integer buildingLandCount = Util.parseInt(value);

                final BuildingBundle buildingBundle = new BuildingBundle();
                buildingBundle.put(BuildingBundle.Keys.NAME, Building.getBuildingName(buildingAttributeMapItem.buildingType));
                buildingBundle.put(BuildingBundle.Keys.COUNT, buildingLandCount.toString());
                buildingBundle.put(BuildingBundle.Keys.PERCENT, ""+ ((float) buildingLandCount / (float) totalLand));

                buildingsBundle.put(buildingAttributeMapItem.buildingKey, buildingBundle);
                // buildingsBundle.put(buildingAttributeMapItem.buildingKey, value);
            }
        }

        return buildingsBundle;
    }

    public UtopiaDatabaseV1(final Database database) {
        _database = database;

        if (_database.shouldBeCreated()) {
            _createAllTables();
            _database.setVersion(DATABASE_VERSION);
        }
        else if (_database.shouldBeUpgraded() || _database.shouldBeDowngraded()) {
            _dropAllTables();
            _createAllTables();
            _database.setVersion(DATABASE_VERSION);
        }
    }

    @Override
    public void storeProvince(final Province province) {
        synchronized (_mutex) {
            if (! province.isIdentifiable()) {
                System.out.println("NOTICE: Cannot store unidentifiable province.");
                return;
            }

            Long provinceId = _getProvinceId(province);
            if (provinceId > 0) {
                _updateProvince(provinceId, province);
            }
            else {
                provinceId = _insertProvince(province);
            }

            final Integer utopiaId = province.getUtopiaId();
            if (utopiaId != null && utopiaId > 0) {
                _updateProvinceUtopiaId(provinceId, utopiaId);
            }

            _storeProvinceAttribute(provinceId, "race", Province.getStringForRace(province.getRace()));
            _storeProvinceAttribute(provinceId, "ruler_name", province.getRulerName());
            _storeProvinceAttribute(provinceId, "acres", province.getAcres());
            _storeProvinceAttribute(provinceId, "food", province.getFood());
            _storeProvinceAttribute(provinceId, "money", province.getMoney());
            _storeProvinceAttribute(provinceId, "runes", province.getRunes());
            _storeProvinceAttribute(provinceId, "trade_balance", province.getTradeBalance());
            _storeProvinceAttribute(provinceId, "peasants", province.getPeasants());
            _storeProvinceAttribute(provinceId, "soldiers", province.getSoldiers());
            _storeProvinceAttribute(provinceId, "defensive_units", province.getDefensiveUnits());
            _storeProvinceAttribute(provinceId, "offensive_units", province.getOffensiveUnits());
            _storeProvinceAttribute(provinceId, "elites", province.getElites());
            _storeProvinceAttribute(provinceId, "horses", province.getHorses());
            _storeProvinceAttribute(provinceId, "prisoners", province.getPrisoners());
            _storeProvinceAttribute(provinceId, "thieves", province.getThieves());
            _storeProvinceAttribute(provinceId, "wizards", province.getWizards());
            _storeProvinceAttribute(provinceId, "title", province.getTitle());
            _storeProvinceAttribute(provinceId, "networth", province.getNetworth());

            _storeProvinceAttribute(provinceId, "building_efficiency", province.getBuildingEfficiency());
            _storeProvinceAttribute(provinceId, "barren_land", province.getBuilding(Building.Type.BARREN));
            _storeProvinceAttribute(provinceId, "homes", province.getBuilding(Building.Type.HOMES));
            _storeProvinceAttribute(provinceId, "farms", province.getBuilding(Building.Type.FARMS));
            _storeProvinceAttribute(provinceId, "mills", province.getBuilding(Building.Type.MILLS));
            _storeProvinceAttribute(provinceId, "banks", province.getBuilding(Building.Type.BANKS));
            _storeProvinceAttribute(provinceId, "training_grounds", province.getBuilding(Building.Type.TRAINING_GROUNDS));
            _storeProvinceAttribute(provinceId, "armories", province.getBuilding(Building.Type.ARMORIES));
            _storeProvinceAttribute(provinceId, "barracks", province.getBuilding(Building.Type.BARRACKS));
            _storeProvinceAttribute(provinceId, "forts", province.getBuilding(Building.Type.FORTS));
            _storeProvinceAttribute(provinceId, "guard_stations", province.getBuilding(Building.Type.GUARD_STATIONS));
            _storeProvinceAttribute(provinceId, "hospitals", province.getBuilding(Building.Type.HOSPITALS));
            _storeProvinceAttribute(provinceId, "guilds", province.getBuilding(Building.Type.GUILDS));
            _storeProvinceAttribute(provinceId, "towers", province.getBuilding(Building.Type.TOWERS));
            _storeProvinceAttribute(provinceId, "thieves_dens", province.getBuilding(Building.Type.THIEVES_DENS));
            _storeProvinceAttribute(provinceId, "watch_towers", province.getBuilding(Building.Type.WATCHTOWERS));
            _storeProvinceAttribute(provinceId, "laboratories", province.getBuilding(Building.Type.LABORATORIES));
            _storeProvinceAttribute(provinceId, "universities", province.getBuilding(Building.Type.UNIVERSITIES));
            _storeProvinceAttribute(provinceId, "stables", province.getBuilding(Building.Type.STABLES));
            _storeProvinceAttribute(provinceId, "dungeons", province.getBuilding(Building.Type.DUNGEONS));

            _storeProvinceAttribute(provinceId, "defense", province.getTotalDefense());
            _storeProvinceAttribute(provinceId, "offense", province.getTotalOffense());

            Boolean hasClearedDeployedArmies = false;
            if (province.hasArmiesDeployed()) {
                final List<Province.DeployedArmy> deployedArmyList = province.getDeployedArmies();
                for (Province.DeployedArmy deployedArmy : deployedArmyList) {
                    if (!deployedArmy.isFromEspionage()) {

                        if (!hasClearedDeployedArmies) {
                            _clearDeployedArmies(provinceId);
                            hasClearedDeployedArmies = true;
                        }

                        _storeDeployedArmy(provinceId, deployedArmy);
                    }
                }
            }
            else {
                _clearOldDeployedArmies(provinceId);
            }
        }
    }

    @Override
    public void storeKingdom(final Kingdom kingdom) {
        synchronized (_mutex) {
            if (kingdom == null) { return; }

            Long kingdomId = _getKingdomIdByKingdomIdentifier(kingdom.getIdentifier());
            if (kingdomId > 0) {
                _updateKingdom(kingdomId, kingdom);
            }
            else {
                kingdomId = _insertKingdom(kingdom);
            }
        }
    }

    @Override
    public Integer getProvinceUtopiaId(final Province province) {
        synchronized (_mutex) {
            return _getProvinceUtopiaId(province);
        }
    }


    @Override
    public Province getProvince(final String provinceName, final Integer kingdomId, final Integer islandId) {
        synchronized (_mutex) {
            final Province province = new Province();

            province.setKingdomIdentifier(new Kingdom.Identifier(kingdomId, islandId));
            province.setName(provinceName);

            final Long provinceId = _getProvinceIdByKingdomIdentifier(provinceName, new Kingdom.Identifier(kingdomId, islandId));
            if (provinceId != null && provinceId > 0) {

                // Inject Throne Intel
                final ThroneBundle throneBundle = _makeThroneBundleFromProvinceId(provinceId);
                throneBundle.put(ThroneBundle.Keys.KINGDOM, kingdomId.toString());
                throneBundle.put(ThroneBundle.Keys.ISLAND, islandId.toString());
                province.update(throneBundle);

                // Inject Army Intel
                final DeployedArmiesBundle deployedArmyBundleList = _getDeployedArmies(provinceId);
                province.update(deployedArmyBundleList);

                // Inject Survey Intel
                final BuildingsBundle buildingsBundle = _makeBuildingBundleFromProvinceId(provinceId);
                province.update(buildingsBundle);

                // Inject UtopiaId
                final Integer utopiaId = _getProvinceUtopiaId(provinceId);
                province.setUtopiaId(utopiaId);
            }

            return province;
        }
    }

    @Override
    public void clear() {
        synchronized (_mutex) {
            _dropAllTables();
            _createAllTables();
        }
    }
}
