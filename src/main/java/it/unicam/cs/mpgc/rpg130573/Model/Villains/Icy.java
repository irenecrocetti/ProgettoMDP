package it.unicam.cs.mpgc.rpg130573.Model.Villains;

import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.VillainSpells.IcyBaseSpell;

public class Icy extends Villain{
    private static final int ICY_HEALTH = 100;
    private static final int ICY_ACCURACY = 70;
    private static final int ICY_XP_RELEASED = 50;

    public Icy(){
        super("Icy", ICY_HEALTH, ICY_HEALTH, ICY_ACCURACY, ICY_XP_RELEASED);
        addSpell(new IcyBaseSpell());
    }
}
