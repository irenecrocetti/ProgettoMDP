package it.unicam.cs.mpgc.rpg130573.combat.events;

/**
 * Evento che può scattare una sola volta in una battaglia.
 * L'evento non ha memoria. La Battle ricorda se è già scattato o no.
 */
public interface BattleEvent {

    /** @return true se le condizioni che fanno scattare l'evento si sono avverate */
    boolean isTriggered(BattleContext context);

    /** Applica gli effetti dell'evento alla battaglia */
    void trigger(BattleContext context);

    /** @return un testo descrittivo che la UI mostra al giocatore */
    String getMessage();
}
