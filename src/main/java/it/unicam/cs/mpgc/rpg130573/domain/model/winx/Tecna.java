package it.unicam.cs.mpgc.rpg130573.domain.model.winx;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.TecnaSpells.ElectroRay;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.TecnaSpells.Firewall;

/**
 * Definisce le statistiche e abilità di base di Tecna
 */
public class Tecna extends Winx{
    private static final int TECNA_DEFAULT_HEALTH = 80;
    private static final int TECNA_DEFAULT_STAMINA = 100;

    public Tecna(){
        super("Tecna", CharacterAllocation.TECNA, TECNA_DEFAULT_HEALTH, TECNA_DEFAULT_HEALTH, TECNA_DEFAULT_STAMINA, TECNA_DEFAULT_STAMINA,0);
        addSpell(new ElectroRay());
        addSpell(new Firewall());
    }
}
