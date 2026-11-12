package it.unicam.cs.mpgc.rpg130573.persistence;

import it.unicam.cs.mpgc.rpg130573.application.session.GameSessionFactory;
import it.unicam.cs.mpgc.rpg130573.application.storage.PersistenceException;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.application.session.GameSession;
import it.unicam.cs.mpgc.rpg130573.domain.level.LevelRegistry;
import it.unicam.cs.mpgc.rpg130573.domain.model.managers.WinxStatusManager;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.WinxFactory;
import it.unicam.cs.mpgc.rpg130573.domain.shop.SpellShop;

import java.util.List;

public class GameSessionMapper {
    private final GameSessionFactory factory;

    public GameSessionMapper(GameSessionFactory factory){
        this.factory = factory;
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
                .filter(id -> factory.findSpell(id).isPresent())
                .toList();
        return new WinxSnapshot(winx.getCharacterAllocation(),
                winx.getLevel(), winx.getXp(), purchased);
    }

    public GameSession fromSnapshot(GameSnapshot snapshot){
        if(snapshot.version() != GameSnapshot.CURRENT_VERSION)
            throw new PersistenceException("Versione di salvataggio non supportata " + snapshot.version());
        try{
            List<Winx> winxes = snapshot.winxes().stream().map(this::restore).toList();
            return factory.restoreGame(winxes, snapshot.highestClearedLevel());
        } catch (IllegalArgumentException e){
            throw new PersistenceException("Dati di salvataggio non validi", e);
        }
    }

    private Winx restore(WinxSnapshot s){
        if(s.level() < WinxStatusManager.MIN_LEVEL || s.level() > WinxStatusManager.MAX_LEVEL)
            throw new PersistenceException("Livello non valido: " + s.level());
        if(s.xp() < 0)
            throw new PersistenceException("XP non validi: " + s.xp());

        Winx winx = factory.createWinx(s.character());
        for(int i = WinxStatusManager.MIN_LEVEL; i < s.level(); i++){
            winx.levelUp();
        }
        winx.getStatusManager().gainXP(s.xp());

        for(String id : s.purchasedSpellsIds()){
            WinxSpell spell = factory.findSpell(id)
                    .orElseThrow(() -> new PersistenceException("Incantesimo sconosciuto: " + id));
            if(spell.getCharacterAllocation() != winx.getCharacterAllocation())
                throw new PersistenceException(id + " non appartiene a " + winx.getName());
            winx.learnSpell(spell);
        }
        winx.restore();
        return winx;
    }
}
