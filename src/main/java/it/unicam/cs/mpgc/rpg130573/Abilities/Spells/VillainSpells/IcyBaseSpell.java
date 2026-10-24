package it.unicam.cs.mpgc.rpg130573.Abilities.Spells.VillainSpells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.VillainSpell;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

public class IcyBaseSpell extends VillainSpell {
    @Override
    protected void doEffect(Entity source, Entity target) {

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public TargetType getTargetType() {
        return null;
    }

    @Override
    public CharacterAllocation getCharacterAllocation() {
        return null;
    }

    @Override
    public SpellType getSpellType() {
        return null;
    }
}
