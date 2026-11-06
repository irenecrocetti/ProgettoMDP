package it.unicam.cs.mpgc.rpg130573.model.villains;

import it.unicam.cs.mpgc.rpg130573.abilities.spells.VillainSpells.ValtorBaseSpell;

public class Valtor extends Villain{
    private static final int VALTOR_HEALTH = 450;
    private static final int VALTOR_ACCURACY = 90;
    private static final int VALTOR_XP_RELEASED = 300;

    public Valtor(){
        super("Valtor", VALTOR_HEALTH, VALTOR_HEALTH, VALTOR_ACCURACY, VALTOR_XP_RELEASED);
        addSpell(new ValtorBaseSpell());
    }
}
