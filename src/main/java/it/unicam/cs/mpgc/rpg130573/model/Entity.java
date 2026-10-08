package it.unicam.cs.mpgc.rpg130573.model;

import it.unicam.cs.mpgc.rpg130573.model.managers.StatusManager;

/**
 * Classe astratta che implementa una qualsiasi entità nel gioco, Winx o nemico
 */
public abstract class Entity {
    private final String name;
    private int hp;
    private int maxHP;
    private boolean isAlive;
    private final StatusManager statusManager;

    public Entity(String name, int hp, int maxHP) {
        this.name = name;
        this.hp = hp;
        this.maxHP = maxHP;
        this.isAlive = true;
        this.statusManager = new StatusManager(this, this.hp, this.maxHP);
    }

    public String getName() {return this.name;}
    public int getHp() {return this.statusManager.getHp();}
    public int getMaxHP() {return this.statusManager.getMaxHP();}
    public boolean isAlive() {return this.statusManager.isAlive();}
    public StatusManager getStatusManager() {return this.statusManager;}

    public void setHp(int amount) {this.statusManager.setHp(amount);}
    public void setMaxHP(int amount) {this.statusManager.setMaxHP(amount);}
}
