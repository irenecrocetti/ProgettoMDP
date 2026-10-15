package it.unicam.cs.mpgc.rpg130573.Abilities.Spells.BloomSpells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

/**
 * Scaglia una palla di fuoco contro un nemico causando un determinato danno
 */
public class FireBall extends WinxSpell {
    public static final int XP_COST = 0;
    public static final int STAMINA_COST = 20;
    public static final int UNLOCKED_AT_LEVEL = 1;
    public static final int DAMAGE = 20;

    @Override
    protected void doEffect(Entity source, Entity target) {
        target.getStatusManager().takeDamage(DAMAGE);
    }

    @Override
    public String getName() {
        return "Fire Ball";
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
        return CharacterAllocation.BLOOM;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.OFFENSE;
    }
}
