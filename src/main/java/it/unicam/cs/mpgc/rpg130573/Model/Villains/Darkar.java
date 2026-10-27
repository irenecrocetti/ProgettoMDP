package it.unicam.cs.mpgc.rpg130573.Model.Villains;

import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.VillainSpells.DarkarBaseSpell;

public class Darkar extends Villain{
    private static final int DARKAR_HEALTH = 200;
    private static final int DARKAR_ACCURACY = 80;
    private static final int DARKAR_XP_RELEASED = 200;

    public Darkar(){
        super("Darkar", DARKAR_HEALTH, DARKAR_HEALTH, DARKAR_ACCURACY, DARKAR_XP_RELEASED);
        this.addSpell(new DarkarBaseSpell());
    }
}
