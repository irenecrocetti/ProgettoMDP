package it.unicam.cs.mpgc.rpg130573.domain.level;

import it.unicam.cs.mpgc.rpg130573.domain.model.villains.Valtor;
import it.unicam.cs.mpgc.rpg130573.domain.model.villains.Villain;

import java.util.List;

public class ValtorLevel implements Level{
    @Override
    public int getId() {
        return 3;
    }

    @Override
    public String getName() {
        return "Valtor";
    }

    @Override
    public List<Villain> createVillains() {
        return List.of(new Valtor());
    }
}
