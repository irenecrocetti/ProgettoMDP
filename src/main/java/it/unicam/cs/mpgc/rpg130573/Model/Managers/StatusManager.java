package it.unicam.cs.mpgc.rpg130573.Model.Managers;


/**
 * Classe manager che gestisce le caratteristiche che hanno in comune tutte le entità
 */
public class StatusManager {
    private int hp;
    private int maxHP;
    private boolean isAlive;

    public StatusManager(int hp, int maxHP){
        this.hp = hp;
        this.maxHP = maxHP;
        this.isAlive = true;
    }

    public int getHp() {return this.hp;}
    public int getMaxHP() {return this.maxHP;}
    public boolean isAlive() {return this.isAlive;}

    public void setHp(int amount) {this.hp = amount;}
    public void setMaxHP(int amount) {this.maxHP = amount;}

    /**
     * Applica il danno direttamente, senza passare per gli effetti
     */
    public void loseHp(int amount){
        this.hp = Math.max(0, this.hp - amount);
        if(this.hp == 0) this.isAlive = false;
    }

    /**
     * Guarisce un'entità.
     *
     * @param amount la quantità di hp da rigenerare
     */
    public void heal(int amount){
        if(!isAlive()) return;
        this.hp = Math.min(this.maxHP, this.hp + amount);
    }
}
