package it.unicam.cs.mpgc.rpg130573.domain.effect;

/**
 * Catalogo degli effetti di stato. IL nome di un effetto ne è l'identità: due
 * effetti con lo stesso nome si fondono con {@link Effect#refresh(Effect)}.
 * Tenere qui i nomi evita che un refuso crei effetti distinti in silenzio.
 */
public final class StatusEffects {
    public static final String BURN = "Burn";
    public static final String ELECTRIFY = "Electrify";
    public static final String FROST = "Frost";
    public static final String SICKNESS = "Sickness";
    public static final String BLINDNESS = "Blindness";
    public static final String SHIELD = "Shield";

    private StatusEffects() {}

    public static Effect burn(int duration, int damagePerTurn){
        return new DamageOverTimeEffect(BURN, duration, damagePerTurn);
    }

    public static Effect electrify(int duration, int damagePerTurn){
        return new DamageOverTimeEffect(ELECTRIFY, duration, damagePerTurn);
    }

    public static Effect frost(int duration, int damagePerTurn){
        return new DamageOverTimeEffect(FROST, duration, damagePerTurn);
    }

    public static Effect sickness(int duration, int damagePerTurn){
        return new DamageOverTimeEffect(SICKNESS, duration, damagePerTurn);
    }

    public static Effect blindness(int duration, int accuracyPenalty){
        return new AccuracyReducerEffect(BLINDNESS, duration, accuracyPenalty);
    }

    public static Effect shield(int duration, int reductionPercent, int maxHits){
        return new ShieldEffect(duration, reductionPercent, maxHits);
    }
}
