package it.unicam.cs.mpgc.rpg130573.Model;

import it.unicam.cs.mpgc.rpg130573.Abilities.Spells.Spell;
import it.unicam.cs.mpgc.rpg130573.Model.Managers.EffectManager;
import it.unicam.cs.mpgc.rpg130573.Model.Managers.StatusManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe astratta che implementa una qualsiasi entità nel gioco, Winx o nemico
 */
public abstract class Entity {
    private final String name;
    private final StatusManager statusManager;
    private final EffectManager effectManager;
    private final List<Spell> spells = new ArrayList<>();

    public Entity(String name, StatusManager statusManager) {
        this.name = name;
        this.statusManager = statusManager;
        this.statusManager.setOwner(this);
        this.effectManager = new EffectManager(this);
    }

    public String getName() {return this.name;}
    public int getHp() {return this.statusManager.getHp();}
    public int getMaxHP() {return this.statusManager.getMaxHP();}
    public boolean isAlive() {return this.statusManager.isAlive();}
    public StatusManager getStatusManager() {return this.statusManager;}
    public EffectManager getEffectManager() {return this.effectManager;}

    public void setHp(int amount) {this.statusManager.setHp(amount);}
    public void setMaxHP(int amount) {this.statusManager.setMaxHP(amount);}

    protected void addSpell(Spell spell){
        if (!hasSpell(spell)) spells.add(spell);
    }

    /**
     * Controlla se tra gli incantesimi c'è già quello passato come parametro
     * @param spell incantesimo da cercare
     * @return true se l'incantesimo è presente, false altrimenti
     */
    public boolean hasSpell(Spell spell){
        for(Spell s : spells ){
            if(s.getClass() == spell.getClass()) return true;
        }
        return false;
    }
}
