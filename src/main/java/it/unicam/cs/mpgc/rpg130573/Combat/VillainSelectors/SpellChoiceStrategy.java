package it.unicam.cs.mpgc.rpg130573.Combat.VillainSelectors;

import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.Spell;
import it.unicam.cs.mpgc.rpg130573.Model.Villains.Villain;

@FunctionalInterface
public interface SpellChoiceStrategy {
    Spell chooseSpell(Villain villain);
}
