package it.unicam.cs.mpgc.rpg130573.combat.selectors;

import it.unicam.cs.mpgc.rpg130573.abilities.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.model.villains.Villain;

@FunctionalInterface
public interface SpellChoiceStrategy {
    Spell chooseSpell(Villain villain);
}
