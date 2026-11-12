package it.unicam.cs.mpgc.rpg130573.domain.ability.spells;

import it.unicam.cs.mpgc.rpg130573.domain.model.managers.WinxStatusManager;

public record SpellCost(int stamina, int xp, int unlockLevel) {

    public SpellCost {
        if (stamina < 0 || xp < 0 || unlockLevel < WinxStatusManager.MIN_LEVEL)
            throw new IllegalArgumentException("Costo non valido: " + this);
    }
}
