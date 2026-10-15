package it.unicam.cs.mpgc.rpg130573.Model.Managers;

import it.unicam.cs.mpgc.rpg130573.Effects.Effect;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Gestisce gli effetti su ogni entità
 */
public class EffectManager {
    private final List<Effect> effects = new ArrayList<>();
    private final Entity owner;

    public EffectManager(Entity owner){ this.owner = owner; }

    public void addEffect(Effect effect){
        effects.add(effect);
        effect.onApply(owner);
    }

    public int applyIncomingDamageModifiers(int damage) {
        for (Effect e : effects) damage = e.modifyIncomingDamage(damage);
        return damage;
    }

    public int applyAccuracyModifiers(int accuracy){
        for (Effect e : effects) accuracy = e.modifyAccuracy(accuracy);
        return accuracy;
    }

    /**
     * Viene chiamato quando l'entità finisce il turno
     */
    public void onTurnEnd() {
        Iterator<Effect> it = effects.iterator();
        while (it.hasNext()) {
            Effect e = it.next();
            e.tick();
            if (e.isExpired()) {
                e.onExpire(owner);
                it.remove();
            }
        }
    }

    public List<Effect> getActiveEffects() { return List.copyOf(effects); }

}
