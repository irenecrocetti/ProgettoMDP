package it.unicam.cs.mpgc.rpg130573.Abilities.Interfaces;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

/**
 * Contiene i metodi che riguardano un incantesimo generico
 *
 * Ogni implementazione di un incantesimo deve includere il nome,
 * il costo in XP, il costo in stamina, il livello a cui viene
 * sbloccato, il tipo di target che accetta, il personaggio che
 * può usare l'incantesimo ed il tipo di incantesimo
 */
public interface Spell {

    /**
     * Metodo che esegue l'incantesimo sull'entità indicata
     *
     * @param source l'entità che esegue l'incantesimo
     * @param target l'entità su cui si scaglia l'incantesimo
     */
    default void execute(Entity source, Entity target) {}

    String getName();

    int getXPCost();

    int getStaminaCost();

    int isUnlockedAtLevel();

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
    SpellType getEnchantmentType();
}
