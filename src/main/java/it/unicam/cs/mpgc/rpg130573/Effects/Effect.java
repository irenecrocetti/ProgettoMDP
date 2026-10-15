package it.unicam.cs.mpgc.rpg130573.Effects;

import it.unicam.cs.mpgc.rpg130573.Model.Entity;

/**
 * Contiene i metodi necessari per definire gli effetti
 */
public interface Effect {
    String getName();

    /**
     * Ritorna i turni rimanenti
     */
    int getRemainingTurns();

    /**
     * Chiamato a fine turno per decrementare la durata
     */
    void tick();

    default boolean isExpired(){return this.getRemainingTurns() <= 0;}

    // Hook che non fanno nulla, ogni effetto fa l'override di quello che gli serve

    /**
     * Modifica il danno in arrivo e restituisce il danno risultante
     */
    default int modifyIncomingDamage(int damage) {return damage;}

    /**
     * Modifica l'accuracy effettiva, che viene ridotta temporaneamente
     */
    default int modifyAccuracy(int accuracy) {return accuracy;}

    /**
     * Chiamato quando l'effetto viene applicato
     */
    default void onApply(Entity owner) {}

    /**
     * Chiamato quando l'effetto scade o viene rimosso
     */
    default void onExpire(Entity owner) {}
}
