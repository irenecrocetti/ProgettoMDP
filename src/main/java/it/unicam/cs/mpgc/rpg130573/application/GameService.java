package it.unicam.cs.mpgc.rpg130573.application;

import it.unicam.cs.mpgc.rpg130573.application.session.GameSession;
import it.unicam.cs.mpgc.rpg130573.persistence.PersistenceException;
import it.unicam.cs.mpgc.rpg130573.persistence.SaveService;

/**
 * Gestisce il ciclo di vita della partita corrente: creazione, caricamento e
 * salvataggio. Non dipende da nessuna libreria grafica, quindi qualsiasi UI
 * (desktop, web, mobile) può usarla.
 */
public class GameService {
    private final SaveService saveService;
    private GameSession current;

    public GameService(SaveService saveService){
        this.saveService = saveService;
    }

    public boolean hasSave(){
        return saveService.hasSave();
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
     * @throws it.unicam.cs.mpgc.rpg130573.persistence.PersistenceException se il file è irraggiungibile
     */
    public GameSession continueGame() {
        GameSession loaded = saveService.load()
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
        saveService.save(getCurrentSession());
    }
}
