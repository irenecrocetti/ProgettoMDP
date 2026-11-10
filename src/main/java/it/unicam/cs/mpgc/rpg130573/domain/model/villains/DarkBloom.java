package it.unicam.cs.mpgc.rpg130573.domain.model.villains;

import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.VillainSpells.DarkBloomBaseSpell;
import it.unicam.cs.mpgc.rpg130573.domain.model.managers.InvulnerableStatusManager;

/**
 * Implementa Bloom quando viene catturata da Darkar. Può attaccare
 * ma non può subire danni.
 */
public class DarkBloom extends Villain{
    private static final int DARK_BLOOM_HEALTH = 200;
    private static final int DARK_BLOOM_ACCURACY = 75;
    private static final int DARK_BLOOM_XP_RELEASED = 0;

    public DarkBloom(){
        super("Dark Bloom", new InvulnerableStatusManager(DARK_BLOOM_HEALTH),
                DARK_BLOOM_ACCURACY, DARK_BLOOM_XP_RELEASED);
        addSpell(new DarkBloomBaseSpell());
    }
}
