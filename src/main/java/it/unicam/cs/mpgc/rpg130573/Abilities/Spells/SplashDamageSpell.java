package it.unicam.cs.mpgc.rpg130573.Abilities.Spells;

import it.unicam.cs.mpgc.rpg130573.Model.Entity;

import java.util.List;

/**
 * Estende l'interfaccia Spell in modo che un incantesimo possa avere effetti
 * su più entità
 */
public interface SplashDamageSpell extends Spell {
    void executeMultiTarget(Entity source, Entity target, List<? extends Entity> context);
}
