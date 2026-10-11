package it.unicam.cs.mpgc.rpg130573.Model.Villains;

import it.unicam.cs.mpgc.rpg130573.Model.Entity;
import it.unicam.cs.mpgc.rpg130573.Model.Managers.StatusManager;

public  class Villain extends Entity {
    private int accuracy;

    public Villain(String name, int health, int maxHealth, int accuracy) {
        super(name, new StatusManager(health, maxHealth));
        this.accuracy = accuracy;
    }

    public int getAccuracy() {return this.accuracy;}

}
