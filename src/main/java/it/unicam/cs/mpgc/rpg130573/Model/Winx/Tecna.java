package it.unicam.cs.mpgc.rpg130573.Model.Winx;

import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.TecnaSpells.ElectroRay;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.TecnaSpells.Firewall;

/**
 * Definisce le statistiche e abilità di base di Tecna
 */
public class Tecna extends Winx{
    private static final int TECNA_DEFAULT_HEALTH = 80;
    private static final int TECNA_DEFAULT_STAMINA = 100;

    public Tecna(){
        super("Tecna",TECNA_DEFAULT_HEALTH, TECNA_DEFAULT_HEALTH, TECNA_DEFAULT_STAMINA, TECNA_DEFAULT_STAMINA,0);
        addSpell(new ElectroRay());
        addSpell(new Firewall());
    }
}
