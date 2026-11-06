package it.unicam.cs.mpgc.rpg130573.levels;

import it.unicam.cs.mpgc.rpg130573.model.Entity;
import it.unicam.cs.mpgc.rpg130573.model.villains.Villain;

import java.util.List;

/**
 * Descrive un livello di gioco: chi si affronta e quando si considera vinto.
 */
public interface Level {

    /**
     * @return l'id del gioco (1,2,..., n) che corrisponde con l'ordine di gioco
     */
    int getId();

    String getName();

    /**
     * Crea nuove istanze dei nemici ogni volta che si gioca il livello
     */
    List<Villain> createVillains();

    /**
     * Condizione di vittoria. Di default si vince quando tutti i nemici sono
     * morti. I livelli con una logica diversa fanno override.
     *
     * @param villains i nemici presenti nello scontro, inclusi quelli
     *                 aggiunti dopo l'inizio
     */
    default boolean isWon(List<Villain> villains){
        return villains.stream().noneMatch(Entity::isAlive);
    }
}
