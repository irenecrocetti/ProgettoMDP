package it.unicam.cs.mpgc.rpg130573.Model.Winx;

import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.StellaSpells.RisingSun;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.StellaSpells.SunShower;

/**
 * Definisce le statistiche e abilità di base di Stella
 */
public class Stella extends Winx{
    private static final int STELLA_DEFAULT_HEALTH = 90;
    private static final int STELLA_DEFAULT_STAMINA = 90;

    public Stella(){
        super("Stella", STELLA_DEFAULT_HEALTH, STELLA_DEFAULT_HEALTH, STELLA_DEFAULT_STAMINA, STELLA_DEFAULT_STAMINA, 0);
        addSpell(new RisingSun());
        addSpell(new SunShower());
    }
}
