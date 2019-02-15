package com.softwareverde.utopia;

import com.softwareverde.util.StringUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Building {
    public enum Type {
        BARREN, HOMES, FARMS, BANKS, DUNGEONS, ARMORIES, MILLS, UNIVERSITIES, LABORATORIES,
        TRAINING_GROUNDS, BARRACKS, STABLES, FORTS, GUARD_STATIONS, WATCHTOWERS, HOSPITALS, GUILDS,
        THIEVES_DENS, TOWERS
    }

    private static final Map<Building.Type, String> _buildingNameMap;
    static  {
        _buildingNameMap = new HashMap<Building.Type, String>();
        _buildingNameMap.put(Type.BARREN, "Barren Land");
        _buildingNameMap.put(Type.HOMES, "Homes");
        _buildingNameMap.put(Type.FARMS, "Farms");
        _buildingNameMap.put(Type.MILLS, "Mills");
        _buildingNameMap.put(Type.BANKS, "Banks");
        _buildingNameMap.put(Type.TRAINING_GROUNDS, "Training Grounds");
        _buildingNameMap.put(Type.ARMORIES, "Armouries");
        _buildingNameMap.put(Type.BARRACKS, "Military Barracks");
        _buildingNameMap.put(Type.FORTS, "Forts");
        _buildingNameMap.put(Type.GUARD_STATIONS, "Guard Stations");
        _buildingNameMap.put(Type.HOSPITALS, "Hospitals");
        _buildingNameMap.put(Type.GUILDS, "Guilds");
        _buildingNameMap.put(Type.TOWERS, "Towers");
        _buildingNameMap.put(Type.THIEVES_DENS, "Thieves' Dens");
        _buildingNameMap.put(Type.WATCHTOWERS, "Watch Towers");
        _buildingNameMap.put(Type.LABORATORIES, "Laboratories");
        _buildingNameMap.put(Type.UNIVERSITIES, "Universities");
        _buildingNameMap.put(Type.STABLES, "Stables");
        _buildingNameMap.put(Type.DUNGEONS, "Dungeons");
    }

    private static final Map<Building.Type, String> _buildingIdentifierMap;
    static  {
        _buildingIdentifierMap = new HashMap<Building.Type, String>();
        _buildingIdentifierMap.put(Type.HOMES, "HOME");
        _buildingIdentifierMap.put(Type.FARMS, "FARM");
        _buildingIdentifierMap.put(Type.MILLS, "MILL");
        _buildingIdentifierMap.put(Type.BANKS, "BANK");
        _buildingIdentifierMap.put(Type.TRAINING_GROUNDS, "TRAINING_GROUND");
        _buildingIdentifierMap.put(Type.ARMORIES, "ARMOURY");
        _buildingIdentifierMap.put(Type.BARRACKS, "BARRACKS");
        _buildingIdentifierMap.put(Type.FORTS, "FORT");
        _buildingIdentifierMap.put(Type.GUARD_STATIONS, "GUARD_STATION");
        _buildingIdentifierMap.put(Type.HOSPITALS, "HOSPITAL");
        _buildingIdentifierMap.put(Type.GUILDS, "GUILD");
        _buildingIdentifierMap.put(Type.TOWERS, "TOWER");
        _buildingIdentifierMap.put(Type.THIEVES_DENS, "THIEVES_DEN");
        _buildingIdentifierMap.put(Type.WATCHTOWERS, "WATCHTOWER");
        _buildingIdentifierMap.put(Type.LABORATORIES, "LABORATORY");
        _buildingIdentifierMap.put(Type.UNIVERSITIES, "UNIVERSITY");
        _buildingIdentifierMap.put(Type.STABLES, "STABLE");
        _buildingIdentifierMap.put(Type.DUNGEONS, "DUNGEON");
    }

    public static String getBuildingName(final Building.Type type) {
        if (! Building._buildingNameMap.containsKey(type)) return "";
        return Building._buildingNameMap.get(type);
    }

    public static String getBuildingIdentifier(final Building.Type type) {
        if (! Building._buildingIdentifierMap.containsKey(type)) return "";
        return Building._buildingIdentifierMap.get(type);
    }

    public static List<Building.Type> getBuildingTypes() {
        final List<Building.Type> list = new LinkedList<Building.Type>();
        for (Building.Type type : Building._buildingNameMap.keySet()) {
            list.add(type);
        }
        return list;
    }

    public static Building.Type getBuildingType(String name) {
        Integer smallestDistance = Integer.MAX_VALUE;
        Building.Type matchedType = null;

        for (Building.Type type : Building._buildingNameMap.keySet()) {
            Integer distance = StringUtil.computeLevenshteinDistance(Building._buildingNameMap.get(type).toLowerCase(), name.toLowerCase());
            if (distance < smallestDistance) {
                smallestDistance = distance;
                matchedType = type;
            }
        }

        return matchedType;
    }

    private Type _type;
    private Integer _count;
    private String _effect;
    private Float _percent;

    public Building(Type type, Integer count, Float percent, String effect) {
        _type = type;
        _count = count;
        _effect = effect;
        _percent = percent;
    }

    public Type getType() { return _type; }
    public Integer getCount() { return _count; }
    public Float getPercent() {
        return _percent;
    }
    public String getEffect() {
        return _effect;
    }
}
