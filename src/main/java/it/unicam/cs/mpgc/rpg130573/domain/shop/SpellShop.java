package it.unicam.cs.mpgc.rpg130573.domain.shop;

import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.BloomSpells.DragonFury;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.BloomSpells.HeatFlare;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.StellaSpells.LightOcean;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.StellaSpells.SunBolt;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.TecnaSpells.EnergySphere;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.TecnaSpells.Glitch;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;

import java.util.List;
import java.util.Optional;

/**
 * Shop in cui le Winx spendono gli xp per acquistare nuovi incantesimi.
 * Un incantesimo è acquistabile se appartiene alla Winx, non è già posseduto,
 * il livello della Winx ha raggiunto quello di sblocco e gli xp sono sufficienti.
 */
public class SpellShop {
    private final List<WinxSpell> catalog;

    public SpellShop(List<WinxSpell> catalog){
        this.catalog = List.copyOf(catalog);
    }

    /**
     * Crea lo shop con tutti gli incantesimi acquistabili del gioco
     */
    public static SpellShop createDefault(){
        return new SpellShop(List.of(
                new HeatFlare(), new DragonFury(),
                new SunBolt(), new LightOcean(),
                new Glitch(), new EnergySphere()
        ));
    }

    /**
     * @return gli incantesimi della Winx già sbloccati dal suo livello e non ancora posseduti
     */
    public List<WinxSpell> getAvailableSpells(Winx buyer){
        return forBuyer(buyer).stream()
                .filter(s -> s.getUnlockLevel() <= buyer.getLevel())
                .toList();

    }

    /**
     * @return gli incantesimi della Winx non ancora sbloccati (livello troppo basso)
     */
    public List<WinxSpell> getLockedSpells(Winx buyer){
        return forBuyer(buyer).stream()
                .filter(s -> s.getUnlockLevel() > buyer.getLevel())
                .toList();
    }

    private List<WinxSpell> forBuyer(Winx buyer){
        return catalog.stream()
                .filter(s -> s.getCharacterAllocation() == buyer.getCharacterAllocation())
                .filter(s -> !buyer.hasSpell(s))
                .toList();
    }

    /**
     * Tenta l'acquisto di un incantesimo. Se tutte le condizioni sono rispettate
     * scala gli xp e aggiunge l'incantesimo alla Winx.
     */
    public PurchaseResult buy(Winx buyer, WinxSpell spell){
        if(catalog.stream().noneMatch(s -> s.getClass() == spell.getClass()))
            return PurchaseResult.NOT_IN_SHOP;
        if(spell.getCharacterAllocation() != buyer.getCharacterAllocation())
            return PurchaseResult.WRONG_CHARACTER;
        if(buyer.hasSpell(spell))
            return PurchaseResult.ALREADY_OWNED;
        if(spell.getUnlockLevel() > buyer.getLevel())
            return PurchaseResult.LEVEL_TOO_LOW;
        if(!buyer.getStatusManager().spendXP(spell.getXPCost()))
            return PurchaseResult.NOT_ENOUGH_XP;

        buyer.learnSpell(spell);
        return PurchaseResult.SUCCESS;
    }

    public Optional<WinxSpell> findById(String id){
        return catalog.stream().filter(s -> s.getId().equals(id)).findFirst();
    }
}
