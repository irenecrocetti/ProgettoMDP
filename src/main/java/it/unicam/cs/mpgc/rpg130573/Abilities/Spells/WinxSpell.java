package it.unicam.cs.mpgc.rpg130573.Abilities.Spells;

import it.unicam.cs.mpgc.rpg130573.Abilities.Enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.Model.Entity;
import it.unicam.cs.mpgc.rpg130573.Model.Managers.WinxStatusManager;
import it.unicam.cs.mpgc.rpg130573.Model.Villains.DarkBloom;
import it.unicam.cs.mpgc.rpg130573.Model.Winx.Winx;

/**
 * Contiene i metodi che descrivono un incantesimo che può essere usato solo da
 * un'entità Winx.
 * Ogni incantesimo deve avere informazioni su il costo in stamina, il costo
 * in XP e il livello a cui viene sbloccato.
 */
public abstract class WinxSpell implements Spell{

    @Override
    public final boolean execute(Entity source, Entity target){
        if(!source.isAlive()) return false;
        if(getTargetType() != TargetType.SELF && !target.isAlive()) return false;
        if(!(source instanceof Winx)) return false;
        WinxStatusManager sm = ((Winx) source).getStatusManager();
        if(sm.getStamina() >= getStaminaCost()){
            sm.decreaseStamina(getStaminaCost());
            doEffect(source, target);
            return true;
        }
        return false;
    }

    public abstract int getStaminaCost();

    public abstract int getXPCost();

    public abstract int getUnlockLevel();

    protected abstract void doEffect(Entity source, Entity target);
}
