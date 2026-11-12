package it.unicam.cs.mpgc.rpg130573.domain.model.villains;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.StandardVillainSpell;

import static it.unicam.cs.mpgc.rpg130573.domain.ability.spells.SpellBeahavior.damage;
import static it.unicam.cs.mpgc.rpg130573.domain.ability.spells.SpellBeahavior.inflict;
import static it.unicam.cs.mpgc.rpg130573.domain.effect.StatusEffects.sickness;

public class Darkar extends Villain{
    private static final int DARKAR_HEALTH = 200;
    private static final int DARKAR_ACCURACY = 80;
    private static final int DARKAR_XP_RELEASED = 200;

    public Darkar(){
        super("Darkar", DARKAR_HEALTH, DARKAR_HEALTH, DARKAR_ACCURACY, DARKAR_XP_RELEASED);
        addSpell(StandardVillainSpell.attack("DarkarBaseSpell", "Shadow Energy", CharacterAllocation.DARKAR,
                damage(30).andThen(inflict(() -> sickness(3,4)))));
    }
}
