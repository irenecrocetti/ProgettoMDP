package it.unicam.cs.mpgc.rpg130573.Effects;

/**
 * Classe che serve semplicemente a evitare di riscrivere gli stessi metodi per ogni effetto
 */
public abstract class AbstractEffect implements Effect{
    private int remainingTurns;

    protected AbstractEffect(int duration){ this.remainingTurns = duration; }

    @Override
    public void tick() {
        remainingTurns--;
    }

    @Override
    public int getRemainingTurns() {
        return this.remainingTurns;
    }

    protected void setRemainingTurns(int turns) { this.remainingTurns = turns; }
}
