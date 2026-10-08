package it.unicam.cs.mpgc.rpg130573.model.managers;

import it.unicam.cs.mpgc.rpg130573.model.Entity;

/**
 * Classe manager che gestisce le caratteristiche che hanno in comune tutte le entità
 */
public class StatusManager {
    private final Entity owner;
    private int hp;
    private int maxHP;
    private boolean isAlive;

    public StatusManager(Entity owner, int hp, int maxHP){
        this.owner = owner;
        this.hp = hp;
        this.maxHP = maxHP;
        this.isAlive = true;
    }

    public Entity getOwner() {return this.owner;}
    public int getHp() {return this.hp;}
    public int getMaxHP() {return this.maxHP;}
    public boolean isAlive() {return this.isAlive;}

    public void setHp(int amount) {this.hp = amount;}
    public void setMaxHP(int amount) {this.maxHP = amount;}

    /**
     * Registra il danno subito da un'entità.
     * I punti vita non possono scendere sotto lo 0.
     * Se i punti vita sono pari a 0 l'entità viene contrassegnata come non più in vita.
     *
     * @param damage quantità di danno inflitto all'entità
     */
    public void takeDamage(int damage) {
        this.hp = Math.max(0, this.hp - damage);
        if(this.hp == 0) {
            this.isAlive = false;
        }
    }

    /**
     * Guarisce un'entità.
     *
     * @param amount la quantità di hp da rigenerare
     */
    public void heal(int amount){
        this.hp = Math.min(this.maxHP, this.hp + amount);
    }
}
