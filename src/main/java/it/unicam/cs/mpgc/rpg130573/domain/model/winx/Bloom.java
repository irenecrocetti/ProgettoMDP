package it.unicam.cs.mpgc.rpg130573.domain.model.winx;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.WinxSpells;

/**
 * Definisce le statistiche e abilità di Bloom
 */
public class Bloom extends Winx{
    private static final int BLOOM_DEFAULT_HEALTH = 100;
    private static final int BLOOM_DEFAULT_STAMINA = 80;

    public Bloom() {
        super("Bloom", CharacterAllocation.BLOOM, BLOOM_DEFAULT_HEALTH, BLOOM_DEFAULT_HEALTH, BLOOM_DEFAULT_STAMINA, BLOOM_DEFAULT_STAMINA, 0);
        addSpell(WinxSpells.FIRE_BALL);
        addSpell(WinxSpells.FLAME_SHIELD);
    }

}
