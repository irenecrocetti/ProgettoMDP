package it.unicam.cs.mpgc.rpg130573.Abilities.Spells.BloomSpells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.Effects.DamageOverTimeEffect;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

public class HeatFlare extends WinxSpell {
    public static final int XP_COST = 100;
    public static final int STAMINA_COST = 30;
    public static final int UNLOCKED_AT_LEVEL = 2;
    public static final int DAMAGE = 40;
    public static final int BURN_DAMAGE = 6;
    public static final int BURN_DURATION = 3;

    @Override
    protected int getStaminaCost() {
        return STAMINA_COST;
    }

    @Override
    protected int getXPCost() {
        return XP_COST;
    }

    @Override
    protected int getUnlockLevel() {
        return UNLOCKED_AT_LEVEL;
    }

    @Override
    protected void doEffect(Entity source, Entity target) {
        target.getStatusManager().takeDamage(DAMAGE);
        target.getEffectManager().addEffect(new DamageOverTimeEffect("Burn", BURN_DURATION, BURN_DAMAGE));
    }

    @Override
    public String getName() {
        return "Heat Flare";
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.ENEMY;
    }

    @Override
    public CharacterAllocation getCharacterAllocation() {
        return CharacterAllocation.BLOOM;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.OFFENSE;
    }
}
