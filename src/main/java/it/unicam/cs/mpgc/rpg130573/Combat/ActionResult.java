package it.unicam.cs.mpgc.rpg130573.Combat;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellResult;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.Spell;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

/**
 * Descrive un'azione avvenuta in battaglia, in modo che la UI possa mostrarla
 * senza che il modello debba conoscere la UI.
 */
public record ActionResult(Entity source, Entity target, Spell spell, SpellResult result) {}
