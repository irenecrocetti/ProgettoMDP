package it.unicam.cs.mpgc.rpg130573.application.storage;

import it.unicam.cs.mpgc.rpg130573.application.session.GameSession;

import java.util.Optional;

/**
 * Cosa serve nell'applicazione per conservare una partita.
 * Non dice né dove né come: lo decide chi la implementa.
 */
public interface GameStorage {

    /**
     * @throws PersistenceException se il salvataggio non riesce
     */
    void save(GameSession session);

    /** @return la partita salvata, vuoto se non esiste
     *  @throws PersistenceException se il salvataggio non è leggibile */
    Optional<GameSession> load();

    boolean hasSave();
}
