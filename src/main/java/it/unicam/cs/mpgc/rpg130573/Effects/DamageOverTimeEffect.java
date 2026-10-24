package it.unicam.cs.mpgc.rpg130573.Effects;

import it.unicam.cs.mpgc.rpg130573.Model.Entity;

/**
 * Classe che implementa un danno nel tempo
 */
public class DamageOverTimeEffect extends AbstractEffect{
    private final String name;
    private final int damagePerTurn;

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
}
