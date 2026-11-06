package it.unicam.cs.mpgc.rpg130573.abilities.spells;

import it.unicam.cs.mpgc.rpg130573.abilities.enums.SpellResult;
import it.unicam.cs.mpgc.rpg130573.abilities.enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.model.Entity;
import it.unicam.cs.mpgc.rpg130573.model.managers.WinxStatusManager;
import it.unicam.cs.mpgc.rpg130573.model.winx.Winx;

/**
 * Contiene i metodi che descrivono un incantesimo che può essere usato solo da
 * un'entità Winx.
 * Ogni incantesimo deve avere informazioni su il costo in stamina, il costo
 * in XP e il livello a cui viene sbloccato.
 */
public abstract class WinxSpell implements Spell{

    @Override
    public final SpellResult execute(Entity source, Entity target){
        if(!source.isAlive()) return SpellResult.CANNOT_CAST;
        if(getTargetType() != TargetType.SELF && !target.isAlive()) return SpellResult.CANNOT_CAST;
        if(!(source instanceof Winx)) return SpellResult.CANNOT_CAST;
        WinxStatusManager sm = ((Winx) source).getStatusManager();
        if(sm.getStamina() >= getStaminaCost()){
            sm.decreaseStamina(getStaminaCost());
            doEffect(source, target);
            return SpellResult.HIT;
        }
        return SpellResult.CANNOT_CAST;
    }

    public abstract int getStaminaCost();

    public abstract int getXPCost();

    public abstract int getUnlockLevel();

    protected abstract void doEffect(Entity source, Entity target);
}
