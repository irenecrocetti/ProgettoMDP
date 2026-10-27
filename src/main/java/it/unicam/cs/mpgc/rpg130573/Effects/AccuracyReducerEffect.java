package it.unicam.cs.mpgc.rpg130573.Effects;

/**
 * Classe che implementa un effetto che riduce l'accuracy dei nemici
 */
public class AccuracyReducerEffect extends AbstractEffect{
    private int accuracyPenalty;
    private final String name;

    public AccuracyReducerEffect(String name, int duration, int accuracyPenalty){
        super(duration);
        this.name = name;
        this.accuracyPenalty = accuracyPenalty;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int modifyAccuracy(int accuracy) {
        return Math.max(0, accuracy - accuracyPenalty);
    }

    /**
     * Fonde un effetto in arrivo con un effetto omonimo.
     * Se la penalizzazione è maggiore, la modifica, altrimenti questa
     * statistica non viene modificata.
     * In ogni caso, il numero di turni rimanenti viene aggiornato.
     */
    @Override
    public boolean refresh(Effect incoming) {
        if (!(incoming instanceof AccuracyReducerEffect other)) return false;
        this.accuracyPenalty = Math.max(this.accuracyPenalty, other.accuracyPenalty);
        setRemainingTurns(other.getRemainingTurns());
        return true;
    }
}
