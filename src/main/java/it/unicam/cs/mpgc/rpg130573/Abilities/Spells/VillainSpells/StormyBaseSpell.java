package it.unicam.cs.mpgc.rpg130573.Abilities.Spells.VillainSpells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.VillainSpell;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

/**
 * Classe che implementa un incantesimo di base per Stormy
 */
public class StormyBaseSpell extends VillainSpell {
    private final int DAMAGE = 20;

    @Override
    protected void doEffect(Entity source, Entity target) {
        target.getStatusManager().takeDamage(DAMAGE);
    }

    @Override
    public String getName() {
        return "Lightning";
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.ENEMY;
    }

    @Override
    public CharacterAllocation getCharacterAllocation() {
        return CharacterAllocation.STORMY;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.OFFENSE;
    }
}
