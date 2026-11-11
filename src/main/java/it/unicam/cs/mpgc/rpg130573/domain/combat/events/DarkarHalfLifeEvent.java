package it.unicam.cs.mpgc.rpg130573.domain.combat.events;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.effect.ShieldEffect;
import it.unicam.cs.mpgc.rpg130573.domain.model.villains.DarkBloom;
import it.unicam.cs.mpgc.rpg130573.domain.model.villains.Darkar;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;

/**
 * Quando Darkar arriva a metà vita libera le Pixie (scudo delle Winx),
 * ma Bloom viene catturata e passa dalla parte del nemico.
 */
public class DarkarHalfLifeEvent implements BattleEvent{
    private static final int PIXIE_SHIELD_DURATION = 3;
    private static final int PIXIE_SHIELD_REDUCTION_PERCENT = 50;
    private static final int PIXIE_SHIELD_MAX_HITS = 2;

    @Override
    public boolean isTriggered(BattleContext context) {
        return context.getVillains().stream()
                .anyMatch(v -> v instanceof Darkar && v.isAlive()
                    && v.getHp() * 2 <= v.getMaxHP());
    }

    @Override
    public void trigger(BattleContext context) {
        context.getWinxes().stream()
                .filter(w -> w.getCharacterAllocation() == CharacterAllocation.BLOOM)
                .findFirst()
                .ifPresent(context::removeWinx);

        context.addVillain(new DarkBloom());

        for(Winx winx : context.getWinxes()){
            winx.getEffectManager().addEffect(new ShieldEffect(
                    PIXIE_SHIELD_DURATION,
                    PIXIE_SHIELD_REDUCTION_PERCENT,
                    PIXIE_SHIELD_MAX_HITS
            ));
        }
    }

    @Override
    public String getMessage() {
        return "Le Winx riescono a liberare le Pixie, ma Bloom viene catturata e trasformata in Dark Bloom!";
    }
}
