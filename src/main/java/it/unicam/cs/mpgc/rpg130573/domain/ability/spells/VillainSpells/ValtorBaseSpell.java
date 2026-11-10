package it.unicam.cs.mpgc.rpg130573.domain.ability.spells.VillainSpells;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.VillainSpell;
import it.unicam.cs.mpgc.rpg130573.domain.effect.DamageOverTimeEffect;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;

/**
 * Classe che implementa un incantesimo di base per Valtor
 */
public class ValtorBaseSpell extends VillainSpell {
    private static final int DAMAGE = 35;
    private static final int BURN_DURATION = 4;
    private static final int BURN_DAMAGE = 7;

    @Override
    protected void doEffect(Entity source, Entity target) {
        target.takeDamage(DAMAGE);
        target.getEffectManager().addEffect(new DamageOverTimeEffect("Burn", BURN_DURATION, BURN_DAMAGE));
    }

    @Override
    public String getName() {
        return "Blue Flame";
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.ENEMY;
    }

    @Override
    public CharacterAllocation getCharacterAllocation() {
        return CharacterAllocation.VALTOR;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.OFFENSE;
    }
}
