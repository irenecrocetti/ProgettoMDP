package it.unicam.cs.mpgc.rpg130573.domain.model.managers;

/**
 * Classe manager che estende il manager generale per tutte le entità.
 * La classe gestisce le caratteristiche che hanno in comune tutte le Winx.
 */
public class WinxStatusManager extends StatusManager{
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 3;

    private int stamina;
    private int maxStamina;
    private int xp;
    private int level = MIN_LEVEL;

    public WinxStatusManager(int health, int maxHealth, int stamina, int maxStamina, int xp) {
        super(health, maxHealth);
        this.stamina = stamina;
        this.maxStamina = maxStamina;
        this.xp = xp;
    }

    public int getStamina() {return this.stamina;}
    public int getMaxStamina() {return this.maxStamina;}
    public int getXp() {return this.xp;}
    public int getLevel() {return this.level;}

    public void setStamina(int amount){this.stamina = amount;}
    public void setMaxStamina(int amount){this.maxStamina = amount;}

    /**
     * Fa avanzare una Winx al livello successivo (da chiamare
     * quando una Winx supera un livello). Il livello non può superare
     * MAX_LEVEL e vengono aumentate la vita e la stamina proporzionalmente.
     *
     * @return true se il livello viene aumentato, false se era già al
     * massimo
     */
    public boolean levelUp(){
        if(this.level >= MAX_LEVEL) return false;
        this.level++;
        this.setMaxHP(this.getMaxHP() + 20);
        this.setMaxStamina(this.getMaxStamina() + 20);
        return true;
    }

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
    public boolean spendXP(int amount){
        if(amount < 0 || amount > this.xp) return false;
        this.xp -= amount;
        return true;
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
