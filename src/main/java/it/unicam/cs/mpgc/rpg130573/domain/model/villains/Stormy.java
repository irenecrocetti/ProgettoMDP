package it.unicam.cs.mpgc.rpg130573.domain.model.villains;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.StandardVillainSpell;

import static it.unicam.cs.mpgc.rpg130573.domain.ability.spells.SpellBeahavior.damage;
import static it.unicam.cs.mpgc.rpg130573.domain.ability.spells.SpellBeahavior.inflict;
import static it.unicam.cs.mpgc.rpg130573.domain.effect.StatusEffects.frost;

public class Stormy extends Villain{
    public static final int STORMY_HEALTH = 90;
    public static final int STORMY_ACCURACY = 70;
    public static final int STORMY_XP_RELEASED = 50;

    public Stormy(){
        super("Stormy", STORMY_HEALTH, STORMY_HEALTH, STORMY_ACCURACY, STORMY_XP_RELEASED);
        addSpell(StandardVillainSpell.attack("StormyBaseSpell", "Lightning", CharacterAllocation.STORMY,
                damage(22)));
    }
}
