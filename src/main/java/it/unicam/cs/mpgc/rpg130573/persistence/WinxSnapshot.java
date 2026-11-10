package it.unicam.cs.mpgc.rpg130573.persistence;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;

import java.util.List;

/** Dati persistenti di una Winx, tutto ciò che non è ricalcolabile */
public record WinxSnapshot(CharacterAllocation character,
                           int level,
                           int xp,
                           List<String> purchasedSpellsIds) {}