package it.unicam.cs.mpgc.rpg130573.Model.Managers;

/**
 * Classe manager che estende il manager generale per tutte le entità.
 * La classe gestisce le caratteristiche che hanno in comune tutte le Winx.
 */
public class WinxStatusManager extends StatusManager{
    private int stamina;
    private int maxStamina;
    private int xp;

    public WinxStatusManager(int health, int maxHealth, int stamina, int maxStamina, int xp) {
        super(health, maxHealth);
        this.stamina = stamina;
        this.maxStamina = maxStamina;
        this.xp = xp;
    }

    public int getStamina() {return this.stamina;}
    public int getMaxStamina() {return this.maxStamina;}
    public int getXp() {return this.xp;}

    public void setStamina(int amount){this.stamina = amount;}
    public void setMaxStamina(int amount){this.maxStamina = amount;}

    //TODO gestione interna al personaggio dei livelli

    /**
     * Rigenera una quantità di stamina.
     *
     * @param amount la quantità di stamina da rigenerare
     */
    public void regenerateStamina(int amount){
        if(this.isAlive()){
            this.stamina = Math.min(this.maxStamina, stamina + amount);
        }
    }

    public void decreaseStamina(int amount){
        if(this.isAlive()){
            this.stamina = Math.max(0, stamina - amount);
        }
    }

    /**
     * Rigenera completamente la stamina
     */
    public void rest(){
        if(this.isAlive()){
            this.stamina = this.maxStamina;
        }
    }

    /**
     * Spende una quantità di xp nello shop.
     *
     * @param amount quantità di xp spesi
     */
    public void spendXP(int amount){
        this.xp -= amount;
    }

    /**
     * Aggiunge una quantità di xp
     *
     * @param amount quantità di xp guadagnati
     */
    public void gainXP(int amount){
        this.xp += amount;
    }
}
