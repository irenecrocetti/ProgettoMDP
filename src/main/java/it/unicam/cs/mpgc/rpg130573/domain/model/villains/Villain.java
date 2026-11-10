package it.unicam.cs.mpgc.rpg130573.domain.model.villains;

import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;
import it.unicam.cs.mpgc.rpg130573.domain.model.managers.StatusManager;

public  class Villain extends Entity {
    private int accuracy;
    private int XPReleased;

    public Villain(String name, int health, int maxHealth, int accuracy, int XPReleased) {
        super(name, new StatusManager(health, maxHealth));
        this.accuracy = accuracy;
        this.XPReleased = XPReleased;
    }

    /**
     * Permette di creare un nemico con uno status manager specializzato.
     * Come ad esempio uno che non gli permette di subire danno.
     */
    public Villain(String name, StatusManager statusManager, int accuracy, int XPReleased){
        super(name, statusManager);
        this.accuracy = accuracy;
        this.XPReleased = XPReleased;
    }

    public int getAccuracy() {return this.accuracy;}
    public int getXPReleased() {return this.XPReleased;}

    public int getEffectiveAccuracy() {
        return this.getEffectManager().applyAccuracyModifiers(this.accuracy);
    }
}
