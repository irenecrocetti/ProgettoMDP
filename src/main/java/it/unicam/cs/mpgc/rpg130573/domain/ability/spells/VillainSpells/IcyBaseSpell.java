package it.unicam.cs.mpgc.rpg130573.domain.ability.spells.VillainSpells;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.VillainSpell;
import it.unicam.cs.mpgc.rpg130573.domain.effect.DamageOverTimeEffect;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;

/**
 * Classe che implementa un incantesimo base per Icy
 */
public class IcyBaseSpell extends VillainSpell {
    private static final int DAMAGE = 15;
    private static final int FROST_DURATION = 3;
    private static final int FROST_DAMAGE = 4;

    @Override
    protected void doEffect(Entity source, Entity target) {
        target.takeDamage(DAMAGE);
        target.getEffectManager().addEffect(new DamageOverTimeEffect("Frost", FROST_DURATION, FROST_DAMAGE));
    }

    @Override
    public String getName() {
        return "Frostbite";
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.ENEMY;
    }

    @Override
    public CharacterAllocation getCharacterAllocation() {
        return CharacterAllocation.ICY;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.OFFENSE;
    }
}
