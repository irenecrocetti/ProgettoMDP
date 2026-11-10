package it.unicam.cs.mpgc.rpg130573.domain.combat.selectors;

import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;
import it.unicam.cs.mpgc.rpg130573.domain.model.villains.Villain;

import java.util.List;

/**
 * Decide su chi un'entità lancia un incantesimo già scelto
 */
@FunctionalInterface
public interface TargetingStrategy {
    Entity chooseTarget(Spell spell, Entity source, List<Winx> winxes, List<Villain> villains);
}
