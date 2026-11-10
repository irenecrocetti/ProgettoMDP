package it.unicam.cs.mpgc.rpg130573.application.session;

import java.util.OptionalInt;

/**
 * Esito di un livello. Pensato per essere mostrato dalla UI.
 *
 * @param levelId           il livello appena giocato
 * @param won               true se le Winx hanno appena vinto
 * @param xpGained          XP guadagnati da ciascuna Winx (0 in caso di sconfitta)
 * @param firstClear        true se è la prima volta che il livello viene superato,
 *                          false altrimenti
 * @param unlockedLevelId   il livello appena sbloccato (se presente)
 */
public record LevelOutcome(int levelId, boolean won, int xpGained,
                           boolean firstClear, OptionalInt unlockedLevelId) {

    public static LevelOutcome defeat(int levelId){
        return new LevelOutcome(levelId, false, 0, false, OptionalInt.empty());
    }
}
