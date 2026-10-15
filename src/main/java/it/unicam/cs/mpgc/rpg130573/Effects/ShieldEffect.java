package it.unicam.cs.mpgc.rpg130573.Effects;

/**
 * Classe che implementa uno scudo
 */
public class ShieldEffect extends AbstractEffect{
    private boolean isConsumed;

    public ShieldEffect(int duration){
        this.isConsumed = false;
        super(duration);
    }

    @Override
    public String getName() {
        return "Shield";
    }

    @Override
    public boolean isExpired() {
        return isConsumed || super.isExpired();
    }

    /**
     * Una volta colpito lo scudo viene eliminato
     */
    @Override
    public int modifyIncomingDamage(int damage) {
        if(!isConsumed){
            isConsumed = true;
            return 0;
        }
        return damage;
    }
}
