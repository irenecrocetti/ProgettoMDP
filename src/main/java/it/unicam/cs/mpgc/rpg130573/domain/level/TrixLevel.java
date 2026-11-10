package it.unicam.cs.mpgc.rpg130573.domain.level;

import it.unicam.cs.mpgc.rpg130573.domain.model.villains.Darcy;
import it.unicam.cs.mpgc.rpg130573.domain.model.villains.Icy;
import it.unicam.cs.mpgc.rpg130573.domain.model.villains.Stormy;
import it.unicam.cs.mpgc.rpg130573.domain.model.villains.Villain;

import java.util.List;

/**
 * Livello 1: le Winx giocano contro le Trix. Si vince se vengono
 * sconfitte tutte e tre.
 */
public class TrixLevel implements Level{
    @Override
    public int getId() {
        return 1;
    }

    @Override
    public String getName() {
        return "Le Trix";
    }

    @Override
    public List<Villain> createVillains() {
        return List.of(new Icy(), new Darcy(), new Stormy());
    }
}
