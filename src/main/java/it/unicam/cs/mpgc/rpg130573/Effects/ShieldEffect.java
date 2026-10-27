package it.unicam.cs.mpgc.rpg130573.Effects;

/**
 * Classe che implementa un effetto scudo
 */
public class ShieldEffect extends AbstractEffect{
    private int reductionPercent;
    private int remainingHits;

    public ShieldEffect(int duration, int reductionPercent, int maxHits){
        super(duration);
        this.reductionPercent = reductionPercent;
        this.remainingHits = maxHits;
    }

    @Override
    public String getName() {
        return "Shield";
    }

    @Override
    public boolean isExpired() {
        return remainingHits == 0 || super.isExpired();
    }

    /**
     * Una volta colpito lo scudo viene eliminato
     */
    @Override
    public int modifyIncomingDamage(int damage) {
        if(remainingHits <= 0) return damage;
        remainingHits--;
        return damage * (100 - reductionPercent) / 100;
    }

    /**
     * Fonde un effetto in arrivo con un effetto omonimo.
     * Se arriva uno scudo più forte (calcolato in base alla capacità totale
     * di assorbimento, reductionPercent × remainingHits), vengono sostituite
     * le statistiche con quelle di quest'ultimo. Altrimenti, vengono solo
     * aggiornati i turni rimanenti.
     */
    @Override
    public boolean refresh(Effect incoming) {
        if (!(incoming instanceof ShieldEffect other)) return false;
        if (other.reductionPercent * other.remainingHits
                > this.reductionPercent * this.remainingHits) {
            this.reductionPercent = other.reductionPercent;
            this.remainingHits = other.remainingHits;
        }
        setRemainingTurns(other.getRemainingTurns());
        return true;
    }
}
