package com.softwareverde.utopia;

import com.softwareverde.utopia.bundle.ActiveSpellBundle;
import com.softwareverde.utopia.bundle.ProvinceIntelActiveSpellBundle;
import com.softwareverde.utopia.bundle.SpellResultBundle;

public class ActiveSpell {
    public static String coerceIdentifierIntoName(final String identifier) {
        if (identifier == null || identifier.isEmpty()) { return ""; }

        final String[] words = identifier.split("_");
        final StringBuffer stringBuffer = new StringBuffer();
        for (final String word : words) {
            stringBuffer.append(word.substring(0, 1).toUpperCase());
            if (word.length() > 1) {
                stringBuffer.append(word.substring(1).toLowerCase());
            }
            stringBuffer.append(" ");
        }
        return stringBuffer.toString().trim();
    }

    public static ActiveSpell fromBundle(final ActiveSpellBundle activeSpellBundle) {
        if (! activeSpellBundle.isValid()) { return null; }

        final String spellName = activeSpellBundle.get(ActiveSpellBundle.Keys.SPELL_NAME);
        final Long spellExpirationTime = Util.parseLong(activeSpellBundle.get(ActiveSpellBundle.Keys.SPELL_EXPIRATION_TIME));
        final String spellDescription = activeSpellBundle.get(ActiveSpellBundle.Keys.SPELL_DESCRIPTION);

        return new ActiveSpell(spellName, spellExpirationTime, spellDescription);
    }

    public static ActiveSpell fromBundle(final SpellResultBundle spellResultBundle) {
        final String spellIdentifier = spellResultBundle.get(SpellResultBundle.Keys.SPELL_IDENTIFIER);
        final String spellName = coerceIdentifierIntoName(spellIdentifier);
        final Long spellExpirationTime = Util.parseLong(spellResultBundle.get(SpellResultBundle.Keys.SPELL_EXPIRATION_TIME));

        if (spellName.isEmpty() || spellExpirationTime == 0) { return null; }

        return new ActiveSpell(spellName, spellExpirationTime, null);
    }

    public static ActiveSpell fromBundle(final ProvinceIntelActiveSpellBundle provinceIntelActiveSpellBundle) {
        final String spellIdentifier = provinceIntelActiveSpellBundle.get(ProvinceIntelActiveSpellBundle.Keys.SPELL_NAME);
        final Long spellExpirationTime = Util.parseLong(provinceIntelActiveSpellBundle.get(ProvinceIntelActiveSpellBundle.Keys.SPELL_EXPIRATION));

        if (spellIdentifier.isEmpty() || spellExpirationTime == 0) { return null; }

        return new ActiveSpell(spellIdentifier, spellExpirationTime, null);
    }

    private final String _spellName;
    private final Long _spellExpirationTime;
    private final String _spellDescription;

    private ActiveSpell(final String spellName, final Long expirationTime, final String spellDescription) {
        _spellName = spellName;
        _spellDescription = (spellDescription == null ? "" : spellDescription);
        _spellExpirationTime = (expirationTime == null ? 0L : expirationTime);
    }

    public String getSpellName() { return _spellName; }
    public Long getExpirationTime() { return _spellExpirationTime; }
    public String getSpellDescription() { return _spellDescription; }
}