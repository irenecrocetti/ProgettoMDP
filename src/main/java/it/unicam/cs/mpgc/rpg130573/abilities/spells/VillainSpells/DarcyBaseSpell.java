package it.unicam.cs.mpgc.rpg130573.abilities.spells.VillainSpells;

import it.unicam.cs.mpgc.rpg130573.abilities.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.abilities.enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.abilities.enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.abilities.spells.VillainSpell;
import it.unicam.cs.mpgc.rpg130573.model.Entity;

/**
 * Classe che implementa un incantesimo base per Darcy
 */
public class DarcyBaseSpell extends VillainSpell {
    private static final int DAMAGE = 18;

    @Override
    protected void doEffect(Entity source, Entity target) {
        target.takeDamage(DAMAGE);
    }

    @Override
    public String getName() {
        return "Kaos";
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.ENEMY;
    }

    @Override
    public CharacterAllocation getCharacterAllocation() {
        return CharacterAllocation.DARCY;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.OFFENSE;
    }
}
