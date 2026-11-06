package it.unicam.cs.mpgc.rpg130573.combat;

import it.unicam.cs.mpgc.rpg130573.model.villains.Villain;
import it.unicam.cs.mpgc.rpg130573.model.winx.Winx;

import java.util.List;

/**
 * Gestisce gli eventi che avvengono in automatico dopo ogni turno o livello
 */
public class BattleManager {
    private static final int STAMINA_REGEN_PER_ROUND = 3;

    /**
     * Si assicura che ogni nemico inizi con il massimo della vita e che ogni winx
     * inizi con il massimo della vita e della stamina
     * @param winxes lista delle winx
     * @param villains lista dei nemici
     */
    public void prepareBattle(List<Winx> winxes, List<Villain> villains){
        winxes.forEach(Winx::restore);
        villains.forEach(Villain::restore);
    }

    /**
     * Rigenera una quantità predefinita di stamina ad ogni turno
     * @param winxes lista delle winx
     */
    public void startNewRound(List<Winx> winxes){
        for(Winx winx : winxes){
            if(winx.isAlive()){
                winx.getStatusManager().regenerateStamina(STAMINA_REGEN_PER_ROUND);
            }
        }
    }

}
