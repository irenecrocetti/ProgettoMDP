package it.unicam.cs.mpgc.rpg130573.domain.model.winx;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;
import it.unicam.cs.mpgc.rpg130573.domain.model.managers.WinxStatusManager;

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

    @Override
    public void restore() {
        super.restore();
        setStamina(getMaxStamina());
    }
}

