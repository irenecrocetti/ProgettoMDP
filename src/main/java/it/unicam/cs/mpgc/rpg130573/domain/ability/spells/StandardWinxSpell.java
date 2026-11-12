package it.unicam.cs.mpgc.rpg130573.domain.ability.spells;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;

import java.util.Objects;

/**
 * Incantesimo delle Winx descritto da dati: profilo, costo e comportamento.
 * È privo di stato, quindi una stessa istanza può essere condivisa da più Winx.
 * Un incantesimo con logica davvero particolare può estendere direttamente {@link WinxSpell}.
 */
public class StandardWinxSpell extends WinxSpell{
    private final SpellProfile profile;
    private final SpellCost cost;
    private final SpellBeahavior beahavior;

    public StandardWinxSpell(SpellProfile profile, SpellCost cost, SpellBeahavior beahavior){
        this.profile = Objects.requireNonNull(profile);
        this.cost = Objects.requireNonNull(cost);
        this.beahavior = Objects.requireNonNull(beahavior);
    }

    @Override
    public String getId() {
        return profile.id();
    }

    @Override
    public String getName() {
        return profile.name();
    }

    @Override
    public TargetType getTargetType() {
        return profile.targetType();
    }

    @Override
    public CharacterAllocation getCharacterAllocation() {
        return profile.allocation();
    }

    @Override
    public SpellType getSpellType() {
        return profile.spellType();
    }

    @Override
    public int getStaminaCost() {
        return cost.stamina();
    }

    @Override
    public int getXPCost() {
        return cost.xp();
    }

    @Override
    public int getUnlockLevel() {
        return cost.unlockLevel();
    }

    @Override
    protected void doEffect(Entity source, Entity target) {
        beahavior.apply(source, target);
    }
}
