package it.unicam.cs.mpgc.rpg130573.persistence;

/**
 * Eccezione dedicata, cosicché sia indipendente dal supporto su cui vengono
 * salvati i dati di gioco
 */
public class PersistenceException extends RuntimeException{
    public PersistenceException(String message) { super(message); }
    public PersistenceException(String message, Throwable cause) { super(message, cause); }
}
