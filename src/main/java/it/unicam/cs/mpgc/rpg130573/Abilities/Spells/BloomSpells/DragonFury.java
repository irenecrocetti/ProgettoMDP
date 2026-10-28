package it.unicam.cs.mpgc.rpg130573.Abilities.Spells.BloomSpells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.Effects.DamageOverTimeEffect;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

public class DragonFury extends WinxSpell {
    public static final int XP_COST = 200;
    public static final int STAMINA_COST = 40;
    public static final int UNLOCKED_AT_LEVEL = 3;
    public static final int DAMAGE = 45;
    public static final int BURN_DAMAGE = 7;
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
        return "Dragon Fury";
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
