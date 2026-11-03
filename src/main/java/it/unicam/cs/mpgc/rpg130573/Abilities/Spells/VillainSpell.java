package it.unicam.cs.mpgc.rpg130573.Abilities.Spells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellResult;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;
import it.unicam.cs.mpgc.rpg130573.Model.Villains.Villain;

import java.util.Random;

/**
 * Descrive un incantesimo che può essere usato esclusivamente da un'entità
 * Villain.
 */
public abstract class VillainSpell implements Spell{
    @Override
    public SpellResult execute(Entity source, Entity target) {
        if(!source.isAlive() || !target.isAlive()) return SpellResult.CANNOT_CAST;
        if(!(source instanceof Villain villain)) return SpellResult.CANNOT_CAST;
        if(!rollHit(villain)) return SpellResult.MISS;
        doEffect(source, target);
        return SpellResult.HIT;
    }

    private boolean rollHit(Villain v){
        Random random = new Random();
        return random.nextInt(100) < v.getEffectiveAccuracy();
    }

    protected abstract void doEffect(Entity source, Entity target);
}
