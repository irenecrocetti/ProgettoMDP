package it.unicam.cs.mpgc.rpg130573.Abilities.Spells.BloomSpells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.Effects.ShieldEffect;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

/**
 * Applica uno scudo su di Bloom
 */
public class FlameShield extends WinxSpell {
    public static final int XP_COST = 0;
    public static final int STAMINA_COST = 20;
    public static final int UNLOCKED_AT_LEVEL = 1;
    public static final int DURATION = 2;

    @Override
    protected void doEffect(Entity source, Entity target) {
        source.getEffectManager().addEffect(new ShieldEffect(DURATION));
    }

    @Override
    public String getName() {
        return "Flame Shield";
    }

    @Override
    public int getXPCost() {
        return XP_COST;
    }

    @Override
    public int getStaminaCost() {
        return STAMINA_COST;
    }

    @Override
    public int getUnlockLevel() {
        return UNLOCKED_AT_LEVEL;
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.SELF;
    }

    @Override
    public CharacterAllocation getCharacterAllocation() {
        return CharacterAllocation.BLOOM;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.SHIELD;
    }
}
