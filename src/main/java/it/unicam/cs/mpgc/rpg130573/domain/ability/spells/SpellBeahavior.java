package it.unicam.cs.mpgc.rpg130573.domain.ability.spells;

import it.unicam.cs.mpgc.rpg130573.domain.effect.Effect;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;

import java.util.Objects;
import java.util.function.Supplier;

/** Cosa fa un incantesimo quando va a segno */
@FunctionalInterface
public interface SpellBeahavior {
    void apply(Entity source, Entity target);

    default SpellBeahavior andThen(SpellBeahavior next) {
        Objects.requireNonNull(next);
        return (source, target) -> {
            apply(source, target);
            next.apply(source, target);
        };
    }

    /** Danno al bersaglio, passando per scudi e altri modificatori */
    static SpellBeahavior damage(int amount){
        return (source, target) -> target.takeDamage(amount);
    }

    /**
     * Applica un effetto al bersaglio. Prende un Supplier e non un Effect perché gli
     * effetti hanno stato: ogni lancio deve crearne uno nuovo.
     */
    static SpellBeahavior inflict(Supplier<Effect> effect){
        return (source, target) -> source.getEffectManager().addEffect(effect.get());
    }

    /** Come inflict, ma l'effetto va a chi lancia */
    static SpellBeahavior buffSelf(Supplier<Effect> effect){
        return (source, target) -> source.getEffectManager().addEffect(effect.get());
    }

    static SpellBeahavior healSelf(int amount){
        return (source, target) -> source.getStatusManager().heal(amount);
    }
}
