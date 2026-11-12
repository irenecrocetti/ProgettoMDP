package it.unicam.cs.mpgc.rpg130573.domain.model.villains;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.StandardVillainSpell;

import static it.unicam.cs.mpgc.rpg130573.domain.ability.spells.SpellBeahavior.damage;
import static it.unicam.cs.mpgc.rpg130573.domain.ability.spells.SpellBeahavior.inflict;
import static it.unicam.cs.mpgc.rpg130573.domain.effect.StatusEffects.burn;

public class Valtor extends Villain{
    private static final int VALTOR_HEALTH = 450;
    private static final int VALTOR_ACCURACY = 90;
    private static final int VALTOR_XP_RELEASED = 300;

    public Valtor(){
        super("Valtor", VALTOR_HEALTH, VALTOR_HEALTH, VALTOR_ACCURACY, VALTOR_XP_RELEASED);
        addSpell(StandardVillainSpell.attack("ValtorBaseSpell", "Blue Flame", CharacterAllocation.VALTOR,
                damage(35).andThen(inflict(() -> burn(4,7) ))));
    }
}
