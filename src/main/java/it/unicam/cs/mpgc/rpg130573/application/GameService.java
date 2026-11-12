package it.unicam.cs.mpgc.rpg130573.application;

import it.unicam.cs.mpgc.rpg130573.application.session.GameSession;
import it.unicam.cs.mpgc.rpg130573.application.storage.GameStorage;
import it.unicam.cs.mpgc.rpg130573.application.storage.PersistenceException;
import it.unicam.cs.mpgc.rpg130573.persistence.SaveService;

import java.util.Objects;

/**
 * Gestisce il ciclo di vita della partita corrente: creazione, caricamento e
 * salvataggio. Non dipende da nessuna libreria grafica, quindi qualsiasi UI
 * (desktop, web, mobile) può usarla.
 */
public class GameService {
    private final GameStorage storage;
    private GameSession current;

    public GameService(GameStorage storage){
        this.storage = Objects.requireNonNull(storage);
    }

    public boolean hasSave(){
        return storage.hasSave();
    }

    /**
     * Crea una nuova partita e la rende quella corrente.
     * Non tocca il salvataggio su disco.
     */
    public GameSession startNewGame(){
        current = GameSession.newGame();
        return current;
    }

    /**
     * Carica il salvataggio e lo rende la partita corrente.
     *
     * @throws IllegalStateException se non esiste alcun salvataggio
     * @throws PersistenceException se il salvataggio non è leggibile
     */
    public GameSession continueGame() {
        GameSession loaded = storage.load()
                .orElseThrow(() -> new IllegalStateException("Nessun salvataggio presente"));
        current = loaded;
        return current;
    }

    /**
     * @throws IllegalStateException se nessuna partita è stata avviata
     */
    public GameSession getCurrentSession(){
        if(current == null)
            throw new IllegalStateException("Nessuna partita in corso");
        return current;
    }

    /**
     * Salva la partita corrente senza propagare errori di persistenza: serve
     * ai salvataggi automatici, che non devono interrompere il gioco.
     *
     * @return true se il salvataggio è riuscito, false altrimenti
     */
    public boolean autosave(){
        try{
            saveCurrent();
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }

    public void saveCurrent(){
        storage.save(getCurrentSession());
    }
}
