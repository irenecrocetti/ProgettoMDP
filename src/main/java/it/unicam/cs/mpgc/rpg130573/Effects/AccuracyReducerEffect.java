package it.unicam.cs.mpgc.rpg130573.Effects;

/**
 * Classe che implementa un effetto che riduce l'accuracy dei nemici
 */
public class AccuracyReducerEffect extends AbstractEffect{
    private final int accuracyPenalty;
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
}
