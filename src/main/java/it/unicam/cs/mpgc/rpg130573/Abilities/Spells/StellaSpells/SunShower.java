package it.unicam.cs.mpgc.rpg130573.Abilities.Spells.StellaSpells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

public class SunShower extends WinxSpell {
    public static final int XP_COST = 0;
    public static final int STAMINA_COST = 20;
    public static final int UNLOCKED_AT_LEVEL = 1;
    public static final int HEAL_AMOUNT = 20;

    @Override
    protected void doEffect(Entity source, Entity target) {
        source.getStatusManager().heal(HEAL_AMOUNT);
    }

    @Override
    public String getName() {
        return "Sun Shower";
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
        return CharacterAllocation.STELLA;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.SELFBUFF;
    }
}
