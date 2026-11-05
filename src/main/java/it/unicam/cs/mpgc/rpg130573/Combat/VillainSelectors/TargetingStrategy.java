package it.unicam.cs.mpgc.rpg130573.Combat.VillainSelectors;

import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.Spell;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;
import it.unicam.cs.mpgc.rpg130573.Model.Winx.Winx;
import it.unicam.cs.mpgc.rpg130573.Model.Villains.Villain;

import java.util.List;

/**
 * Decide su chi un'entità lancia un incantesimo già scelto
 */
@FunctionalInterface
public interface TargetingStrategy {
    Entity chooseTarget(Spell spell, Entity source, List<Winx> winxes, List<Villain> villains);
}
