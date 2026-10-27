package it.unicam.cs.mpgc.rpg130573.Abilities.Spells.TecnaSpells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.Effects.DamageOverTimeEffect;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

/**
 * Applica un danno sul nemico, che viene "elettrizzato" applicando un
 * danno nel tempo
 */
public class ElectroRay extends WinxSpell {
    public static final int XP_COST = 0;
    public static final int STAMINA_COST = 20;
    public static final int UNLOCKED_AT_LEVEL = 1;
    public static final int DAMAGE = 10;
    public static final int ELECTRIFY_DAMAGE = 5;
    public static final int ELECTRIFY_DURATION = 4;

    @Override
    protected void doEffect(Entity source, Entity target) {
        target.getStatusManager().takeDamage(DAMAGE);
        target.getEffectManager().addEffect(new DamageOverTimeEffect("Electrify", ELECTRIFY_DURATION, ELECTRIFY_DAMAGE));
    }

    @Override
    public String getName() {
        return "Electro Ray";
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
        return CharacterAllocation.TECNA;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.OFFENSE;
    }
}
