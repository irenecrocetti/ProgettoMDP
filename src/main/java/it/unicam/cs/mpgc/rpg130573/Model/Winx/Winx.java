package it.unicam.cs.mpgc.rpg130573.Model.Winx;

import it.unicam.cs.mpgc.rpg130573.Model.Entity;
import it.unicam.cs.mpgc.rpg130573.Model.Managers.StatusManager;
import it.unicam.cs.mpgc.rpg130573.Model.Managers.WinxStatusManager;

/**
 * Classe che estende Entity ed implementa una Winx generica
 */
public class Winx extends Entity {

    public Winx(String name, int health, int maxHealth, int stamina, int maxStamina, int xp) {
        super(name, new WinxStatusManager(health, maxHealth, stamina, maxStamina, xp));
    }

    @Override
    public WinxStatusManager getStatusManager() {
        return (WinxStatusManager) super.getStatusManager();
    }

    public int getStamina() {return this.getStatusManager().getStamina();}
    public int getMaxStamina() {return this.getStatusManager().getMaxStamina();}
    public int getXp() {return this.getStatusManager().getXp();}

    public void setStamina(int amount) {this.getStatusManager().setStamina(amount);}
    public void setMaxStamina(int amount) {this.getStatusManager().setMaxStamina(amount);}
}

