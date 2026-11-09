package it.unicam.cs.mpgc.rpg130573.persistence;

import it.unicam.cs.mpgc.rpg130573.abilities.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.abilities.spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.game.GameSession;
import it.unicam.cs.mpgc.rpg130573.levels.LevelRegistry;
import it.unicam.cs.mpgc.rpg130573.model.managers.WinxStatusManager;
import it.unicam.cs.mpgc.rpg130573.model.winx.Winx;
import it.unicam.cs.mpgc.rpg130573.model.winx.WinxFactory;
import it.unicam.cs.mpgc.rpg130573.shop.SpellShop;

import java.util.List;

public class GameSessionMapper {
    private final WinxFactory winxFactory;
    private final SpellShop shop;
    private final LevelRegistry registry;

    public GameSessionMapper(WinxFactory winxFactory, SpellShop shop, LevelRegistry registry){
        this.winxFactory = winxFactory;
        this.shop = shop;
        this.registry = registry;
    }

    public GameSnapshot toSnapshot(GameSession session){
        List<WinxSnapshot> winxes = session.getWinxes().stream()
                .map(this::toSnapshot).toList();
        return new GameSnapshot(GameSnapshot.CURRENT_VERSION,
                session.getHighestClearedLevel(), winxes);
    }

    private WinxSnapshot toSnapshot(Winx winx){
        List<String> purchased = winx.getSpells().stream()
                .map(Spell::getId)
                .filter(id -> shop.findById(id).isPresent())
                .toList();
        return new WinxSnapshot(winx.getCharacterAllocation(),
                winx.getLevel(), winx.getXp(), purchased);
    }

    public GameSession fromSnapshot(GameSnapshot snapshot){
        if(snapshot.version() != GameSnapshot.CURRENT_VERSION)
            throw new PersistenceException("Versione di salvataggio non supportata " + snapshot.version());
        try{
            List<Winx> winxes = snapshot.winxes().stream().map(this::restore).toList();
            return new GameSession(winxes, shop, registry, snapshot.highestClearedLevel());
        } catch (IllegalArgumentException e){
            throw new PersistenceException("Dati di salvataggio non validi", e);
        }
    }

    private Winx restore(WinxSnapshot s){
        if(s.level() < WinxStatusManager.MIN_LEVEL || s.level() > WinxStatusManager.MAX_LEVEL)
            throw new PersistenceException("Livello non valido: " + s.level());
        if(s.xp() < 0)
            throw new PersistenceException("XP non validi: " + s.xp());

        Winx winx = winxFactory.create(s.character());
        for(int i = WinxStatusManager.MIN_LEVEL; i < s.level(); i++){
            winx.levelUp();
        }
        winx.getStatusManager().gainXP(s.xp());

        for(String id : s.purchasedSpellsIds()){
            WinxSpell spell = shop.findById(id)
                    .orElseThrow(() -> new PersistenceException("Incantesimo sconosciuto: " + id));
            if(spell.getCharacterAllocation() != winx.getCharacterAllocation())
                throw new PersistenceException(id + " non appartiene a " + winx.getName());
            winx.learnSpell(spell);
        }
        winx.restore();
        return winx;
    }
}
