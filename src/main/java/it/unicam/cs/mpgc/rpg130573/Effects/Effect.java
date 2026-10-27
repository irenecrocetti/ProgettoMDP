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

    /**
     * Chiamato a ogni fine turno dall'owner, prima del tick della durata
     */
    default void onTurnEnd(Entity owner) {}

    /**
     * Prova a fondere un effetto omonimo in arrivo in questo, già attivo.
     *
     * @param incoming il nuovo effetto con lo stesso nome
     * @return true se l'effetto è stato fuso (e quindi il nuovo va scartato),
     *         false se questo effetto non supporta il refresh e va sostituito
     */
    default boolean refresh(Effect incoming) { return false; }
}
