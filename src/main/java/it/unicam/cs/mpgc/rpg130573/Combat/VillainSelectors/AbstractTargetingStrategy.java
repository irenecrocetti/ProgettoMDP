package it.unicam.cs.mpgc.rpg130573.Combat.VillainSelectors;

import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.Spell;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;
import it.unicam.cs.mpgc.rpg130573.Model.Villains.Villain;
import it.unicam.cs.mpgc.rpg130573.Model.Winx.Winx;

import java.util.List;

/**
 * Risolve la lista dei candidati in base al tipo di bersaglio dello spell e
 * scarta i morti. Rimane alle sottoclassi scegliere tra i vivi.
 */
public abstract class AbstractTargetingStrategy implements TargetingStrategy{

    @Override
    public Entity chooseTarget(Spell spell, Entity source,
                               List<Winx> winxes, List<Villain> villains) {
        List<? extends Entity> candidates = switch (spell.getTargetType()) {
            case ENEMY -> winxes;
            case ALLY -> villains;
            case SELF -> List.of(source);
        };

        List<? extends Entity> alive = candidates.stream()
                .filter(Entity::isAlive)
                .toList();
        if(alive.isEmpty())
            throw new IllegalStateException("Nessun bersaglio vivo disponibile");

        return pick(alive);
    }

    /** @param alive candidati vivi, mai vuota */
    protected abstract Entity pick(List<? extends Entity> alive);
}
