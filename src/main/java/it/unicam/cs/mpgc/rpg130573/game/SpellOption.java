package it.unicam.cs.mpgc.rpg130573.game;

import it.unicam.cs.mpgc.rpg130573.abilities.spells.Spell;

/**
 * Un incantesimo della Winx corrente con tutte le informazioni necessarie: quanto
 * costa e se può essere lanciato nell'immediato
 */
public record SpellOption(Spell spell, int staminaCost, boolean castable) {}
