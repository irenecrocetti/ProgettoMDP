package it.unicam.cs.mpgc.rpg130573.combat.selectors;

import it.unicam.cs.mpgc.rpg130573.model.Entity;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Sceglie un bersaglio vivo a caso
 */
public class RandomTargeting extends AbstractTargetingStrategy{
    private final Random random;

    public RandomTargeting(){
        this(new Random());
    }

    public RandomTargeting(Random random){
        this.random = Objects.requireNonNull(random);
    }

    @Override
    protected Entity pick(List<? extends Entity> alive) {
        return alive.get(random.nextInt(alive.size()));
    }
}
