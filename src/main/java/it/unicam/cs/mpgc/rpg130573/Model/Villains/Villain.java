package it.unicam.cs.mpgc.rpg130573.Model.Villains;

import it.unicam.cs.mpgc.rpg130573.Model.Entity;
import it.unicam.cs.mpgc.rpg130573.Model.Managers.StatusManager;

public  class Villain extends Entity {
    private int accuracy;
    private int XPReleased;

    public Villain(String name, int health, int maxHealth, int accuracy, int XPReleased) {
        super(name, new StatusManager(health, maxHealth));
        this.accuracy = accuracy;
        this.XPReleased = XPReleased;
    }

    public int getAccuracy() {return this.accuracy;}
    public int getXPReleased() {return this.XPReleased;}
}
