package it.unicam.cs.mpgc.rpg130573.Model.Villains;

import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.VillainSpells.DarcyBaseSpell;

public class Darcy extends Villain{
    public static final int DARCY_HEALTH = 100;
    public static final int DARCY_ACCURACY = 70;
    public static final int DARCY_XP_RELEASED = 50;

    public Darcy(){
        super("Darcy", DARCY_HEALTH, DARCY_HEALTH, DARCY_ACCURACY, DARCY_XP_RELEASED);
        addSpell(new DarcyBaseSpell());
    }
}

