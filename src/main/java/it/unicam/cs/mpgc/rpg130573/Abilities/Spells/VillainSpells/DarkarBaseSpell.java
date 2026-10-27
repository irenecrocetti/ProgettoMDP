package it.unicam.cs.mpgc.rpg130573.Abilities.Spells.VillainSpells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.VillainSpell;
import it.unicam.cs.mpgc.rpg130573.Effects.DamageOverTimeEffect;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

/**
 * Classe che implementa un incantesimo di base per Darkar
 */
public class DarkarBaseSpell extends VillainSpell {
    private static final int DAMAGE = 30;
    private static final int SICKNESS_DAMAGE = 4;
    private static final int SICKNESS_DURATION = 3;

    @Override
    protected void doEffect(Entity source, Entity target) {
        target.getStatusManager().takeDamage(DAMAGE);
        target.getEffectManager().addEffect(new DamageOverTimeEffect("Sickness", SICKNESS_DURATION, SICKNESS_DAMAGE));
    }

    @Override
    public String getName() {
        return "Shadow Energy";
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.ENEMY;
    }

    @Override
    public CharacterAllocation getCharacterAllocation() {
        return CharacterAllocation.DARKAR;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.OFFENSE;
    }
}
