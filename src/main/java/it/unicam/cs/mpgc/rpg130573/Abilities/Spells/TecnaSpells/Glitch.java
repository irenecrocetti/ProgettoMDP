package it.unicam.cs.mpgc.rpg130573.Abilities.Spells.TecnaSpells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.Effects.AccuracyReducerEffect;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

public class Glitch extends WinxSpell {
    public static final int STAMINA_COST = 30;
    public static final int XP_COST = 100;
    public static final int UNLOCKED_AT_LEVEL = 2;
    public static final int DAMAGE = 32;
    public static final int ELECTRIFY_DAMAGE = 4;
    public static final int ELECTRIFY_DURATION = 3;

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
        target.getEffectManager().addEffect(new AccuracyReducerEffect("Electrify", ELECTRIFY_DURATION, ELECTRIFY_DAMAGE));
    }

    @Override
    public String getName() {
        return "Glitch";
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
