package it.unicam.cs.mpgc.rpg130573.Model.Winx;

import it.unicam.cs.mpgc.rpg130573.Model.Entity;
import it.unicam.cs.mpgc.rpg130573.Model.Managers.WinxStatusManager;

/**
 * Classe che estende Entity ed implementa una Winx generica
 */
public class Winx extends Entity {

    public Winx(String name, int health, int maxHealth, int stamina, int maxStamina, int xp) {
        super(name, new WinxStatusManager(health, maxHealth, stamina, maxStamina, xp));
    }

    public int getStamina() {return this.getWinxStatusManager().getStamina();}
    public int getMaxStamina() {return this.getWinxStatusManager().getMaxStamina();}
    public int getXp() {return this.getWinxStatusManager().getXp();}
    public WinxStatusManager getWinxStatusManager() {return (WinxStatusManager) this.getStatusManager();}

    public void setStamina(int amount) {this.getWinxStatusManager().setStamina(amount);}
    public void setMaxStamina(int amount) {this.getWinxStatusManager().setMaxStamina(amount);}
}

