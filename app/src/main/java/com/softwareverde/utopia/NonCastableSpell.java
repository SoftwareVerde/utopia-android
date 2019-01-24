package com.softwareverde.utopia;

// TODO: Make NonCastableSpell (rename to Spell) the parent, and rename Spell into CastableSpell as child.
public class NonCastableSpell extends Spell {
    public NonCastableSpell(final String spellName) {
        super(null, spellName, null, null);
    }
}
