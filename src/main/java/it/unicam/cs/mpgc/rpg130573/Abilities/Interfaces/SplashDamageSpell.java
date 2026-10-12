package it.unicam.cs.mpgc.rpg130573.Abilities.Interfaces;

import it.unicam.cs.mpgc.rpg130573.Model.Entity;

import java.util.List;

public interface SplashDamageSpell extends Spell {
    void executeMultiTarget(Entity source, Entity target, List<? extends Entity> context);
}
