package it.unicam.cs.mpgc.rpg130573.Model.Winx;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;
import it.unicam.cs.mpgc.rpg130573.Model.Managers.StatusManager;
import it.unicam.cs.mpgc.rpg130573.Model.Managers.WinxStatusManager;

/**
 * Classe che estende Entity ed implementa una Winx generica
 */
public class Winx extends Entity {
    private final CharacterAllocation allocation;

    public Winx(String name, CharacterAllocation allocation, int health, int maxHealth, int stamina, int maxStamina, int xp) {
        super(name, new WinxStatusManager(health, maxHealth, stamina, maxStamina, xp));
        this.allocation = allocation;
    }

    @Override
    public WinxStatusManager getStatusManager() {
        return (WinxStatusManager) super.getStatusManager();
    }

    public int getStamina() {return this.getStatusManager().getStamina();}
    public int getMaxStamina() {return this.getStatusManager().getMaxStamina();}
    public int getXp() {return this.getStatusManager().getXp();}
    public CharacterAllocation getCharacterAllocation() {return this.allocation;}
    public int getLevel() {return this.getStatusManager().getLevel();}

    /**
     * Passa al livello successivo
     *
     * @return true se il livello è aumentato, false se era già al massimo
     */
    public boolean levelUp() {return this.getStatusManager().levelUp();}

    public void setStamina(int amount) {this.getStatusManager().setStamina(amount);}
    public void setMaxStamina(int amount) {this.getStatusManager().setMaxStamina(amount);}

    /**
     * Aggiunge un incantesimo appreso
     *
     * @param spell l'incantesimo da apprendere
     * @return true se l'incantesimo è stato aggiunto, false se era già posseduto
     */
    public boolean learnSpell(WinxSpell spell){
        if(hasSpell(spell)) return false;
        addSpell(spell);
        return true;
    }

    /**
     * Ristabilisce le statistiche della Winx allo stato iniziale
     */
    public void recoverAfterBattle(){
        setStamina(getMaxStamina());
        setHp(getMaxHP());
        getEffectManager().clear();
    }
}

