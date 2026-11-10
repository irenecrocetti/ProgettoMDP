package it.unicam.cs.mpgc.rpg130573.application.battle;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.SpellResult;
import it.unicam.cs.mpgc.rpg130573.domain.combat.ActionResult;
import it.unicam.cs.mpgc.rpg130573.domain.combat.events.BattleEvent;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;

import java.util.List;
import java.util.Optional;

/**
 * Tutto ciò che è accaduto a seguito di un'azione del giocatore.
 *
 * @param actor             la Winx che ha agito
 * @param action            l'incantesimo lanciato, vuoto se la Winx ha riposato
 * @param enemyActions      le azioni dei nemici (vuota se il loro turno non è ancora arrivato)
 * @param events            gli eventi che sono stati scatenati
 * @param battleFinished    true se la battaglia è conclusa
 */
public record TurnReport(Winx actor,
                         Optional<ActionResult> action,
                         List<ActionResult> enemyActions,
                         List<BattleEvent> events,
                         boolean battleFinished) {

    /**
     * @return false se l'azione è stata rifiutata e il turno non è stato consumato
     */
    public boolean turnConsumed() {
        return action.map(a -> a.result() != SpellResult.CANNOT_CAST).orElse(true);
    }
}
