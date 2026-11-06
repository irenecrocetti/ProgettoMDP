package it.unicam.cs.mpgc.rpg130573.model.villains;

import it.unicam.cs.mpgc.rpg130573.abilities.spells.VillainSpells.DarkBloomBaseSpell;

public class DarkBloom extends Villain{
    private static final int DARK_BLOOM_HEALTH = 200;
    private static final int DARK_BLOOM_ACCURACY = 75;
    private static final int DARK_BLOOM_XP_RELEASED = 0;

    public DarkBloom(){
        super("Dark Bloom", DARK_BLOOM_HEALTH, DARK_BLOOM_HEALTH, DARK_BLOOM_ACCURACY, DARK_BLOOM_XP_RELEASED);
        addSpell(new DarkBloomBaseSpell());
    }
}
