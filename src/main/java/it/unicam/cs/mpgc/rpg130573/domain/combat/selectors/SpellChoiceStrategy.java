package it.unicam.cs.mpgc.rpg130573.domain.combat.selectors;

import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.domain.model.villains.Villain;

@FunctionalInterface
public interface SpellChoiceStrategy {
    Spell chooseSpell(Villain villain);
}
