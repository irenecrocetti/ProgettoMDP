package it.unicam.cs.mpgc.rpg130573.Effects;

import it.unicam.cs.mpgc.rpg130573.Model.Entity;

/**
 * Classe che implementa un danno nel tempo
 */
public class DamageOverTimeEffect extends AbstractEffect{
    private final String name;
    private int damagePerTurn;

    public DamageOverTimeEffect(String name, int duration, int damagePerTurn){
        super(duration);
        this.name = name;
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void onTurnEnd(Entity owner) {
        owner.getStatusManager().loseHp(damagePerTurn);
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Fonde un effetto in arrivo con un effetto omonimo.
     * Se il danno inflitto per turno è maggiore, modifica il danno dell'effetto con
     * quest'utltimo, altrimenti questa statistica non viene modificata
     * In ogni caso, il numero di turni rimanenti viene aggiornato.
     */
    @Override
    public boolean refresh(Effect incoming) {
        if (!(incoming instanceof DamageOverTimeEffect other)) return false;
        this.damagePerTurn = Math.max(this.damagePerTurn, other.damagePerTurn);
        setRemainingTurns(other.getRemainingTurns());
        return true;
    }
}
