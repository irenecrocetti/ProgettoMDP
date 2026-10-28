package it.unicam.cs.mpgc.rpg130573.Abilities.Spells.StellaSpells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.Effects.AccuracyReducerEffect;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

/**
 * Scaglia della luce solare contro il nemico, che viene "accecato" applicando
 * un danno temporaneo alla sua accuracy.
 */
public class RisingSun extends WinxSpell {
    public static final int XP_COST = 0;
    public static final int STAMINA_COST = 20;
    public static final int UNLOCKED_AT_LEVEL = 1;
    public static final int DAMAGE = 15;
    public static final int BLINDNESS_DURATION = 2;
    public static final int BLINDNESS_PENALTY = 25;

    @Override
    protected void doEffect(Entity source, Entity target) {
        target.getStatusManager().takeDamage(DAMAGE);
        target.getEffectManager().addEffect(new AccuracyReducerEffect("Blindness", BLINDNESS_DURATION, BLINDNESS_PENALTY));
    }

    @Override
    public String getName() {
        return "Rising Sun";
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
