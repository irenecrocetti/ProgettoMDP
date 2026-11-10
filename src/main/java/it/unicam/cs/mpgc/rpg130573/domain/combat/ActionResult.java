package it.unicam.cs.mpgc.rpg130573.domain.combat;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.SpellResult;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;

/**
 * Descrive un'azione avvenuta in battaglia, in modo che la UI possa mostrarla
 * senza che il modello debba conoscere la UI.
 */
public record ActionResult(Entity source, Entity target, Spell spell, SpellResult result) {}
