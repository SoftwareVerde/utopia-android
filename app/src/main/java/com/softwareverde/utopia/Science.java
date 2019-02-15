package com.softwareverde.utopia;

import com.softwareverde.utopia.bundle.Bundle;
import com.softwareverde.utopia.bundle.ScienceBundle;
import com.softwareverde.utopia.bundle.ScientistBundle;

import java.util.ArrayList;
import java.util.List;

public class Science {
    public enum Type {
        ALCHEMY, TOOLS, HOUSING, FOOD, MILITARY, CRIME, CHANNELING
    }

    public static Type getTypeFromString(final String string) {
        switch (string.toUpperCase()) {
            case "ALCHEMY": return Type.ALCHEMY;
            case "TOOLS": return Type.TOOLS;
            case "HOUSING": return Type.HOUSING;
            case "FOOD": return Type.FOOD;
            case "MILITARY": return Type.MILITARY;
            case "CRIME": return Type.CRIME;
            case "CHANNELING": return Type.CHANNELING;
        }
        return null;
    }

    public static String getStringForType(final Type type) {
        switch (type) {
            case ALCHEMY: return "ALCHEMY";
            case TOOLS: return "TOOLS";
            case HOUSING: return "HOUSING";
            case FOOD: return "FOOD";
            case MILITARY: return "MILITARY";
            case CRIME: return "CRIME";
            case CHANNELING: return "CHANNELING";
        }
        return null;
    }

    public static Science fromBundle(ScienceBundle scienceBundle) {
        final Science science = new Science();

        science._alchemyScientistCount = Util.parseInt(scienceBundle.get(ScienceBundle.Keys.ALCHEMY_SCIENTIST_COUNT));
        science._toolScientistCount = Util.parseInt(scienceBundle.get(ScienceBundle.Keys.TOOL_SCIENTIST_COUNT));
        science._housingScientistCount = Util.parseInt(scienceBundle.get(ScienceBundle.Keys.HOUSING_SCIENTIST_COUNT));
        science._foodScientistCount = Util.parseInt(scienceBundle.get(ScienceBundle.Keys.FOOD_SCIENTIST_COUNT));
        science._militaryScientistCount = Util.parseInt(scienceBundle.get(ScienceBundle.Keys.MILITARY_SCIENTIST_COUNT));
        science._crimeScientistCount = Util.parseInt(scienceBundle.get(ScienceBundle.Keys.CRIME_SCIENTIST_COUNT));
        science._channelingScientistCount = Util.parseInt(scienceBundle.get(ScienceBundle.Keys.CHANNELING_SCIENTIST_COUNT));

        science._alchemyEffect = scienceBundle.get(ScienceBundle.Keys.ALCHEMY_EFFECT);
        science._toolEffect = scienceBundle.get(ScienceBundle.Keys.TOOL_EFFECT);
        science._housingEffect = scienceBundle.get(ScienceBundle.Keys.HOUSING_EFFECT);
        science._foodEffect = scienceBundle.get(ScienceBundle.Keys.FOOD_EFFECT);
        science._militaryEffect = scienceBundle.get(ScienceBundle.Keys.MILITARY_EFFECT);
        science._crimeEffect = scienceBundle.get(ScienceBundle.Keys.CRIME_EFFECT);
        science._channelingEffect = scienceBundle.get(ScienceBundle.Keys.CHANNELING_EFFECT);

        for (final Bundle subBundle : scienceBundle.getGroup(ScienceBundle.Keys.SCIENTISTS_GROUP)) {
            final ScientistBundle scientistBundle = (ScientistBundle) subBundle;
            final Scientist scientist = Scientist.fromBundle(scientistBundle);
            science._scientists.add(scientist);
        }

        return science;
    }

    private Integer _alchemyScientistCount = 0;
    private Integer _toolScientistCount = 0;
    private Integer _housingScientistCount = 0;
    private Integer _foodScientistCount = 0;
    private Integer _militaryScientistCount = 0;
    private Integer _crimeScientistCount = 0;
    private Integer _channelingScientistCount = 0;

    private String _alchemyEffect = "";
    private String _toolEffect = "";
    private String _housingEffect = "";
    private String _foodEffect = "";
    private String _militaryEffect = "";
    private String _crimeEffect = "";
    private String _channelingEffect = "";

    private List<Scientist> _scientists = new ArrayList<Scientist>();

    private Science() { }

    public void setAlchemyScientistCount(final Integer count) { _alchemyScientistCount = count; }
    public void setToolsScientistCount(final Integer count) { _toolScientistCount = count; }
    public void setHousingScientistCount(final Integer count) { _housingScientistCount = count; }
    public void setFoodScientistCount(final Integer count) { _foodScientistCount = count; }
    public void setMilitaryScientistCount(final Integer count) { _militaryScientistCount = count; }
    public void setCrimeScientistCount(final Integer count) { _crimeScientistCount = count; }
    public void setChannelingScientistCount(final Integer count) { _channelingScientistCount = count; }

    public Integer getAlchemyScientistCount() { return _alchemyScientistCount; }
    public Integer getToolScientistCount() { return _toolScientistCount; }
    public Integer getHousingScientistCount() { return _housingScientistCount; }
    public Integer getFoodScientistCount() { return _foodScientistCount; }
    public Integer getMilitaryScientistCount() { return _militaryScientistCount; }
    public Integer getCrimeScientistCount() { return _crimeScientistCount; }
    public Integer getChannelingScientistCount() { return _channelingScientistCount; }

    public void setAlchemyEffect(final String effect) { _alchemyEffect = effect; }
    public void setToolEffect(final String effect) { _toolEffect = effect; }
    public void setHousingEffect(final String effect) { _housingEffect = effect; }
    public void setFoodEffect(final String effect) { _foodEffect = effect; }
    public void setMilitaryEffect(final String effect) { _militaryEffect = effect; }
    public void setCrimeEffect(final String effect) { _crimeEffect = effect; }
    public void setChannelingEffect(final String effect) { _channelingEffect = effect; }

    public String getAlchemyEffect() { return _alchemyEffect; }
    public String getToolEffect() { return _toolEffect; }
    public String getHousingEffect() { return _housingEffect; }
    public String getFoodEffect() { return _foodEffect; }
    public String getMilitaryEffect() { return _militaryEffect; }
    public String getCrimeEffect() { return _crimeEffect; }
    public String getChannelingEffect() { return _channelingEffect; }

    public List<Scientist> getScientists() { return _scientists; }
}