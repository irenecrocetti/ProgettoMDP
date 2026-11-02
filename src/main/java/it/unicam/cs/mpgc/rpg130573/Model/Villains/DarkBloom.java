package it.unicam.cs.mpgc.rpg130573.Model.Villains;

import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.BloomSpells.FireBall;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.BloomSpells.FlameShield;

public class DarkBloom extends Villain{
    private static final int DARK_BLOOM_HEALTH = 200;
    private static final int DARK_BLOOM_ACCURACY = 75;
    private static final int DARK_BLOOM_XP_RELEASED = 0;

    public DarkBloom(){
        super("DarkBloom", DARK_BLOOM_HEALTH, DARK_BLOOM_HEALTH, DARK_BLOOM_ACCURACY, DARK_BLOOM_XP_RELEASED);
        //TODO aggiungere incantesimi
    }
}
