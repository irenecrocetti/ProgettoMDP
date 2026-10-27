package it.unicam.cs.mpgc.rpg130573.Model.Villains;

import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.VillainSpells.StormyBaseSpell;

public class Stormy extends Villain{
    public static final int STORMY_HEALTH = 90;
    public static final int STORMY_ACCURACY = 70;
    public static final int STORMY_XP_RELEASED = 50;

    public Stormy(){
        super("Stormy", STORMY_HEALTH, STORMY_HEALTH, STORMY_ACCURACY, STORMY_XP_RELEASED);
        addSpell(new StormyBaseSpell());
    }
}
