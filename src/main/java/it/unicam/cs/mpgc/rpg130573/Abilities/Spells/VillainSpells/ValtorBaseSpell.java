package it.unicam.cs.mpgc.rpg130573.Abilities.Spells.VillainSpells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.VillainSpell;
import it.unicam.cs.mpgc.rpg130573.Effects.DamageOverTimeEffect;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

/**
 * Classe che implementa un incantesimo di base per Valtor
 */
public class ValtorBaseSpell extends VillainSpell {
    private final int DAMAGE = 35;
    private final int BURN_DURATION = 4;
    private final int BURN_DAMAGE = 7;

    @Override
    protected void doEffect(Entity source, Entity target) {
        target.getStatusManager().takeDamage(DAMAGE);
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
