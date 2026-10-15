package it.unicam.cs.mpgc.rpg130573.Abilities.Spells;

import it.unicam.cs.mpgc.rpg130573.Model.Entity;
import it.unicam.cs.mpgc.rpg130573.Model.Managers.WinxStatusManager;
import it.unicam.cs.mpgc.rpg130573.Model.Winx.Winx;

public abstract class WinxSpell implements Spell{

    @Override
    public final boolean execute(Entity source, Entity target){
        if(!(source instanceof Winx)) return false;
        WinxStatusManager sm = ((Winx) source).getStatusManager();
        if(sm.getStamina() >= getStaminaCost()){
            sm.decreaseStamina(getStaminaCost());
            doEffect(source, target);
            return true;
        }
        return false;
    }

    protected abstract void doEffect(Entity source, Entity target);
}
