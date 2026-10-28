package it.unicam.cs.mpgc.rpg130573.Abilities.Spells.StellaSpells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.Effects.AccuracyReducerEffect;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

public class SunBolt extends WinxSpell {
    public static final int STAMINA_COST = 30;
    public static final int XP_COST = 100;
    public static final int UNLOCKED_AT_LEVEL = 2;
    public static final int DAMAGE = 28;
    public static final int BLINDNESS_PENALTY = 30;
    public static final int BLINDNESS_DURATION = 2;

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
        target.getEffectManager().addEffect(new AccuracyReducerEffect("Blindness", BLINDNESS_DURATION, BLINDNESS_PENALTY));
    }

    @Override
    public String getName() {
        return "Sun Bolt";
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.ENEMY;
    }

    @Override
    public CharacterAllocation getCharacterAllocation() {
        return CharacterAllocation.STELLA;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.OFFENSE;
    }
}
