package com.softwareverde.utopia;

import com.softwareverde.utopia.bundle.SpellBundle;

public class Spell {
    public enum SpellType {
        DEFENSIVE, OFFENSIVE
    }
    public static class SpellNames {
        public static final String AGGRESSION = "Aggression"; // Used when calculating offense...
        public static final String STORMS = "Storms";
        public static final String DROUGHT = "Drought";
        public static final String VERMIN = "Vermin";
        public static final String GLUTTONY = "Gluttony";
        public static final String GREED = "Greed";
        public static final String PITFALLS = "Pitfalls";
        public static final String CHASTITY = "Chastity";
        public static final String EXPLOSIONS = "Explosions";
        public static final String METEOR_SHOWERS = "Meteor Showers";
        public static final String RIOTS = "Riots";
        public static final String BLIZZARD = "Blizzard";
    }

    public static String typeToString(SpellType spellType) {
        switch (spellType) {
            case DEFENSIVE: return "DEFENSIVE";
            case OFFENSIVE: return "OFFENSIVE";
        }

        return "";
    }
    public static SpellType stringToType(String spellTypeString) {
        switch (spellTypeString.toUpperCase()) {
            case "DEFENSIVE": return SpellType.DEFENSIVE;
            case "OFFENSIVE": return SpellType.OFFENSIVE;
        }
        return null;
    }

    public static Spell fromBundle(final SpellBundle bundle) {
        if (! bundle.isValid()) { return null; }

        final String spellIdentifier = bundle.get(SpellBundle.Keys.SPELL_IDENTIFIER);
        final String spellName = bundle.get(SpellBundle.Keys.SPELL_NAME);
        final Integer spellRuneCost = Util.parseInt(bundle.get(SpellBundle.Keys.SPELL_COST));
        final String spellType = bundle.get(SpellBundle.Keys.SPELL_TYPE);

        final Spell spell = new Spell();
        spell._identifier = spellIdentifier;
        spell._name = spellName;
        spell._runeCost = spellRuneCost;
        spell._type = Spell.stringToType(spellType);

        return spell;
    }

    private SpellType _type;
    private String _name;
    private String _identifier;
    private Integer _runeCost;

    private Spell() { }
    protected Spell(final SpellType spellType, final String name, final String identifier, final Integer runeCost) {
        _type = spellType;
        _name = name;
        _identifier = identifier;
        _runeCost = runeCost;
    }

    public String getName() { return _name; }
    public String getIdentifier() { return _identifier; }
    public Integer getRuneCost() { return _runeCost; }
    public SpellType getType() { return _type; }
}
