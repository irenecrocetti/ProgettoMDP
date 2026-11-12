package it.unicam.cs.mpgc.rpg130573.domain.model;

import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.domain.model.managers.EffectManager;
import it.unicam.cs.mpgc.rpg130573.domain.model.managers.StatusManager;

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
        this.effectManager = new EffectManager(this);
    }

    public String getName() {return this.name;}
    public int getHp() {return this.statusManager.getHp();}
    public int getMaxHP() {return this.statusManager.getMaxHP();}
    public boolean isAlive() {return this.statusManager.isAlive();}
    public StatusManager getStatusManager() {return this.statusManager;}
    public EffectManager getEffectManager() {return this.effectManager;}

    public boolean isInvulnerable() {return this.statusManager.isInvulnerable();}

    /**
     * @return una copia non modificabile degli incantesimi posseduti
     */
    public List<Spell> getSpells() {return List.copyOf(spells);}

    public void setHp(int amount) {this.statusManager.setHp(amount);}
    public void setMaxHP(int amount) {this.statusManager.setMaxHP(amount);}

    protected void addSpell(Spell spell){
        if (!hasSpell(spell)) spells.add(spell);
    }

    /**
     * Applica un danno passando per i modificatori degli effetti (scudi, ecc.)
     */
    public void takeDamage(int damage){
        statusManager.loseHp(effectManager.applyIncomingDamageModifiers(damage));
    }

    /**
     * Controlla se tra gli incantesimi c'è già quello passato come parametro
     * Due incantesimi sono "lo stesso" se hanno lo stesso {@link Spell#getId() id}.
     */
    public boolean hasSpell(Spell spell){
        return spells.stream().anyMatch(s -> s.getId().equals(spell.getId()));
    }

    /**
     * Riporta l'entità allo stato iniziale dopo uno scontro
     */
    public void restore(){
        setHp(getMaxHP());
        effectManager.clear();
    }
}
