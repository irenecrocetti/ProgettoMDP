package it.unicam.cs.mpgc.rpg130573.combat.selectors;

import it.unicam.cs.mpgc.rpg130573.abilities.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.model.villains.Villain;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Sceglie uno spell a caso tra quelli posseduti dal nemico
 */
public class RandomSpellChoice implements SpellChoiceStrategy{
    private final Random random;

    public RandomSpellChoice(){
        this(new Random());
    }

    public RandomSpellChoice(Random random){
        this.random = Objects.requireNonNull(random);
    }

    @Override
    public Spell chooseSpell(Villain villain) {
        List<Spell> spells = villain.getSpells();
        if(spells.isEmpty())
            throw new IllegalStateException(villain.getName() + " non ha incantesimi");
        return spells.get(random.nextInt(spells.size()));
    }
}
