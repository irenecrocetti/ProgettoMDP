package it.unicam.cs.mpgc.rpg130573.domain.ability.spells;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.SpellResult;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;

/**
 * Contiene i metodi che riguardano un incantesimo generico
 *
 * Ogni implementazione di un incantesimo deve includere il nome,
 * il tipo di target che accetta, il personaggio che
 * può usare l'incantesimo e il tipo di incantesimo
 */
public interface Spell {

    /**
     * Metodo che esegue l'incantesimo sull'entità indicata
     *
     * @param source l'entità che esegue l'incantesimo
     * @param target l'entità su cui si scaglia l'incantesimo
     */
    SpellResult execute(Entity source, Entity target);

    String getName();
    /**
     * @return il tipo di entità a cui e diretto l'incantesimo
     */
    TargetType getTargetType();

    /**
     * @return il tipo di entità che può eseguire l'incantesimo
     */
    CharacterAllocation getCharacterAllocation();

    /**
     * @return il tipo di incantesimo
     */
    SpellType getSpellType();

    /**
     * Identificatore stabile dell'incantesimo: serve a confrontare gli incantesimi e finisce
     * nei salvataggi, quindi non va cambiato una volta pubblicato. Non è il nome mostrato.
     */
    String getId();
}
