package it.unicam.cs.mpgc.rpg130573.domain.combat.events;

import it.unicam.cs.mpgc.rpg130573.domain.model.villains.Villain;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;

import java.util.List;

/**
 * Parte della battaglia che un oggetto può vedere e modificare.
 * Evita di dare agli eventi l'accesso a tutta la battle.
 */
public interface BattleContext {

    /** @return copia non modificabile della lista delle winx attualmente in battaglia*/
    List<Winx> getWinxes();

    /** @return copia non modificabile della lista dei nemici attualmente in battaglia */
    List<Villain> getVillains();

    /** Toglie una winx dalla battaglia in corso. */
    void removeWinx(Winx winx);

    /** Aggiunge un nemico alla battaglia in corso. */
    void addVillain(Villain villain);
}
