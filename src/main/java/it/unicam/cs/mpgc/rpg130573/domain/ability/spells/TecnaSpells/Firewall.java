package it.unicam.cs.mpgc.rpg130573.domain.ability.spells.TecnaSpells;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.domain.effect.ShieldEffect;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;

/**
 * Applica uno scudo su Tecna
 */
public class Firewall extends WinxSpell {
    public static final int XP_COST = 0;
    public static final int STAMINA_COST = 20;
    public static final int UNLOCKED_AT_LEVEL = 1;
    public static final int DURATION = 3;
    public static final int DAMAGE_REDUCTION_PERCENT = 60;
    public static final int MAX_HITS = 3;

    @Override
    protected void doEffect(Entity source, Entity target) {
        source.getEffectManager().addEffect(new ShieldEffect(DURATION, DAMAGE_REDUCTION_PERCENT, MAX_HITS));
    }

    @Override
    public String getName() {
        return "Firewall";
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
        return CharacterAllocation.TECNA;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.SHIELD;
    }
}
