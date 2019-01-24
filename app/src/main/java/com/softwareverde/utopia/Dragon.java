package com.softwareverde.utopia;

public class Dragon {
    public enum Type {
        EMERALD,
        SAPPHIRE,
        GOLD,
        RUBY
    }

    public static String getName(Type type) {
        switch (type) {
            case EMERALD: { return "Emerald"; }
            case SAPPHIRE: { return "Sapphire"; }
            case GOLD: { return "Gold"; }
            case RUBY: { return "Ruby"; }
            default: { return ""; }
        }
    }
    public static Type getType(String type) {
        switch (type.toUpperCase()) {
            case "EMERALD": { return Type.EMERALD; }
            case "SAPPHIRE": { return Type.SAPPHIRE; }
            case "GOLD": { return Type.GOLD; }
            case "RUBY": { return Type.RUBY; }
            default: { return null; }
        }
    }
    public static String getColorString(Type type) {
        switch (type) {
            case EMERALD: { return "#00FF00"; }
            case SAPPHIRE: { return "#0000FF"; }
            case GOLD: { return "#FFD700"; }
            case RUBY: { return "#FF0000"; }
            default: { return ""; }
        }
    }

    private Type _type;
    private Integer _health;
    private Integer _maxHealth;

    public Dragon(Type type, Kingdom kingdom) {
        _type = type;
        _health = null;
        _calculateMaxHealth(kingdom);
    }
    public Dragon(Type type, Kingdom kingdom, Integer health) {
        _type = type;
        _health = health;
        _calculateMaxHealth(kingdom);
    }
    public void setHealth(Integer health) {
        _health = health;
        if (_health < 0) {
            _health = 0;
        }
        if (_health > _maxHealth) {
            _health = _maxHealth;
        }
    }
    private void _calculateMaxHealth(Kingdom kingdom) {
        _maxHealth = kingdom.getNetworth() / 44;
    }

    public Boolean isValid() { return _type != null; }
    public String getNamedType() { return Dragon.getName(_type); }
    public String getColorString() { return Dragon.getColorString(_type); }
    public Type getType() { return _type; }
    public Integer getHealth() { return _health; }
    public Integer getMaxHealth() { return _maxHealth; }
}
