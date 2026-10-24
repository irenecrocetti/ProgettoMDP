package it.unicam.cs.mpgc.rpg130573.Abilities.Spells;

import it.unicam.cs.mpgc.rpg130573.Model.Entity;
import it.unicam.cs.mpgc.rpg130573.Model.Villains.Villain;

/**
 * Descrive un incantesimo che può essere usato esclusivamente da un'entità
 * Villain.
 */
public abstract class VillainSpell implements Spell{

    @Override
    public boolean execute(Entity source, Entity target) {
        if(!(source instanceof Villain)) return false;
        doEffect(source, target);
        return true;
    }

    protected abstract void doEffect(Entity source, Entity target);
}
