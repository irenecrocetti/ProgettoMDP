package it.unicam.cs.mpgc.rpg130573.levels;

import it.unicam.cs.mpgc.rpg130573.combat.events.BattleEvent;
import it.unicam.cs.mpgc.rpg130573.combat.events.DarkarHalfLifeEvent;
import it.unicam.cs.mpgc.rpg130573.model.villains.Darkar;
import it.unicam.cs.mpgc.rpg130573.model.villains.Villain;

import java.util.List;

public class DarkarLevel implements Level{
    @Override
    public int getId() {
        return 2;
    }

    @Override
    public String getName() {
        return "Darkar";
    }

    @Override
    public List<Villain> createVillains() {
        return List.of(new Darkar());
    }

    @Override
    public boolean isWon(List<Villain> villains) {
        return villains.stream().noneMatch(v -> v instanceof Darkar && v.isAlive());
    }

    @Override
    public List<BattleEvent> createEvents() {
        return List.of(new DarkarHalfLifeEvent());
    }
}
