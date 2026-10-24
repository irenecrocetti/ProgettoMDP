package it.unicam.cs.mpgc.rpg130573.Model.Winx;

import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.BloomSpells.FireBall;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.BloomSpells.FlameShield;

/**
 * Definisce le statistiche e abilità di Bloom
 */
public class Bloom extends Winx{
    private static final int BLOOM_DEFAULT_HEALTH = 100;
    private static final int BLOOM_DEFAULT_STAMINA = 80;

    public Bloom() {
        super("Bloom", BLOOM_DEFAULT_HEALTH, BLOOM_DEFAULT_HEALTH, BLOOM_DEFAULT_STAMINA, BLOOM_DEFAULT_STAMINA, 0);
        addSpell(new FlameShield());
        addSpell(new FireBall());
    }

}
