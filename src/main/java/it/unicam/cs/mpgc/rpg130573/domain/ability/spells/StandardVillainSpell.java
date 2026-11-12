package it.unicam.cs.mpgc.rpg130573.domain.ability.spells;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;

import java.util.Objects;

/**
 * Incantesimo di un nemico descritto da dati: profilo, costo e comportamento.
 * È privo di stato, quindi una stessa istanza può essere condivisa da più Villain.
 * Un incantesimo con logica davvero particolare può estendere direttamente {@link VillainSpell}.
 */
public class StandardVillainSpell extends VillainSpell {
    private final SpellProfile profile;
    private final SpellBeahavior beahavior;

    public StandardVillainSpell(SpellProfile profile, SpellBeahavior beahavior){
        this.profile = Objects.requireNonNull(profile);
        this.beahavior = Objects.requireNonNull(beahavior);
    }

    /** Incantesimo offensivo: bersaglia l'avversario */
    public static StandardVillainSpell attack(String id, String name,
                                              CharacterAllocation owner, SpellBeahavior beahavior){
        return new StandardVillainSpell(
                new SpellProfile(id, name, owner, TargetType.ENEMY, SpellType.OFFENSE), beahavior);
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
    protected void doEffect(Entity source, Entity target) {
        beahavior.apply(source, target);
    }
}
