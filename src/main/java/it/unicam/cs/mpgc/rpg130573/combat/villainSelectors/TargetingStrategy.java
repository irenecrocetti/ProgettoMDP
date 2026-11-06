package it.unicam.cs.mpgc.rpg130573.combat.villainSelectors;

import it.unicam.cs.mpgc.rpg130573.abilities.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.model.Entity;
import it.unicam.cs.mpgc.rpg130573.model.winx.Winx;
import it.unicam.cs.mpgc.rpg130573.model.villains.Villain;

import java.util.List;

/**
 * Decide su chi un'entità lancia un incantesimo già scelto
 */
@FunctionalInterface
public interface TargetingStrategy {
    Entity chooseTarget(Spell spell, Entity source, List<Winx> winxes, List<Villain> villains);
}
