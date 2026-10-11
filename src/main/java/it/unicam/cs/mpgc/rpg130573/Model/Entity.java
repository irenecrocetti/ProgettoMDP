package it.unicam.cs.mpgc.rpg130573.Model;

import it.unicam.cs.mpgc.rpg130573.Model.Managers.StatusManager;

/**
 * Classe astratta che implementa una qualsiasi entità nel gioco, Winx o nemico
 */
public abstract class Entity {
    private final String name;
    private final StatusManager statusManager;

    public Entity(String name, StatusManager statusManager) {
        this.name = name;
        this.statusManager = statusManager;
        this.statusManager.setOwner(this);
    }

    public String getName() {return this.name;}
    public int getHp() {return this.statusManager.getHp();}
    public int getMaxHP() {return this.statusManager.getMaxHP();}
    public boolean isAlive() {return this.statusManager.isAlive();}
    public StatusManager getStatusManager() {return this.statusManager;}

    public void setHp(int amount) {this.statusManager.setHp(amount);}
    public void setMaxHP(int amount) {this.statusManager.setMaxHP(amount);}
}
