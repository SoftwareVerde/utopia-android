package com.softwareverde.utopia;

import com.softwareverde.utopia.bundle.ScientistBundle;

public class Scientist {
    public enum Level {
        RECRUIT, NOVICE, GRADUATE, PROFESSOR
    }

    public static Level getLevelFromString(final String string) {
        switch (string.toUpperCase()) {
            case "RECRUIT": return Level.RECRUIT;
            case "NOVICE": return Level.NOVICE;
            case "GRADUATE": return Level.GRADUATE;
            case "PROFESSOR": return Level.PROFESSOR;
        }
        return null;
    }

    public static Scientist fromBundle(final ScientistBundle scientistBundle) {
        final String name = scientistBundle.get(ScientistBundle.Keys.SCIENTIST_NAME);
        final Level level = getLevelFromString(scientistBundle.get(ScientistBundle.Keys.SCIENTIST_LEVEL));
        final Science.Type type = Science.getTypeFromString(scientistBundle.get(ScientistBundle.Keys.CURRENT_ASSIGNMENT));
        final Integer ticksUntilAdvancement = Util.parseInt(scientistBundle.get(ScientistBundle.Keys.TICKS_UNTIL_ADVANCEMENT));
        final String formName = scientistBundle.get(ScientistBundle.Keys.FORM_NAME);

        final Scientist scientist = new Scientist();

        scientist._name = name;
        scientist._level = level;
        scientist._ticksUntilAdvencement = ticksUntilAdvancement;
        scientist._originalAssignment = type;
        scientist._newAssignment = type;
        scientist._formName = formName;

        return scientist;
    }

    private String _name;
    private Level _level;
    private Integer _ticksUntilAdvencement;
    private Science.Type _originalAssignment;
    private Science.Type _newAssignment;
    private String _formName;

    private Scientist() { }

    public String getName() { return _name; }
    public String getFormName() { return _formName; }
    public Level getLevel() { return _level; }
    public Integer getTicksUntilAdvancement() { return _ticksUntilAdvencement; }
    public Science.Type getOriginalAssignment() { return _originalAssignment; }
    public Science.Type getAssignment() { return _newAssignment; }

    public void setAssignment(final Science.Type assignment) { _newAssignment = assignment; }
}