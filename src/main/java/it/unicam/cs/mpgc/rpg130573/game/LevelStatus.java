package it.unicam.cs.mpgc.rpg130573.game;

/**
 * Stato di un livello rispetto al progresso della partita
 */
public enum LevelStatus {
    /** Livello già superato almeno una volta, può essere rigiocato */
    COMPLETED,
    /** Livello ancora non superato, ma giocabile */
    AVAILABLE,
    /** Livello non ancora sbloccato, non può essere giocato */
    LOCKED
}
