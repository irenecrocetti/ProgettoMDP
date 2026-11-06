package it.unicam.cs.mpgc.rpg130573.abilities.spells.TecnaSpells;

import it.unicam.cs.mpgc.rpg130573.abilities.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.abilities.enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.abilities.enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.abilities.spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.effects.DamageOverTimeEffect;
import it.unicam.cs.mpgc.rpg130573.model.Entity;

public class EnergySphere extends WinxSpell {
    public static final int STAMINA_COST = 40;
    public static final int XP_COST = 200;
    public static final int UNLOCKED_AT_LEVEL = 3;
    public static final int DAMAGE = 40;
    public static final int ELECTRIFY_DAMAGE = 5;
    public static final int ELECTRIFY_DURATION = 3;

    @Override
    public int getStaminaCost() {
        return STAMINA_COST;
    }

    @Override
    public int getXPCost() {
        return XP_COST;
    }

    @Override
    public int getUnlockLevel() {
        return UNLOCKED_AT_LEVEL;
    }

    @Override
    protected void doEffect(Entity source, Entity target) {
        target.takeDamage(DAMAGE);
        target.getEffectManager().addEffect(new DamageOverTimeEffect("Electrify", ELECTRIFY_DURATION, ELECTRIFY_DAMAGE));
    }

    @Override
    public String getName() {
        return "Energy Sphere";
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.ENEMY;
    }

    @Override
    public CharacterAllocation getCharacterAllocation() {
        return CharacterAllocation.TECNA;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.OFFENSE;
    }
}
