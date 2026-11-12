package it.unicam.cs.mpgc.rpg130573.domain.model.villains;


import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.StandardVillainSpell;

import static it.unicam.cs.mpgc.rpg130573.domain.ability.spells.SpellBeahavior.damage;

public class Darcy extends Villain{
    public static final int DARCY_HEALTH = 100;
    public static final int DARCY_ACCURACY = 70;
    public static final int DARCY_XP_RELEASED = 50;

    public Darcy(){
        super("Darcy", DARCY_HEALTH, DARCY_HEALTH, DARCY_ACCURACY, DARCY_XP_RELEASED);
        addSpell(StandardVillainSpell.attack("DarcyBaseSpell", "Kaos", CharacterAllocation.DARCY,
                damage(18)));
    }
}

