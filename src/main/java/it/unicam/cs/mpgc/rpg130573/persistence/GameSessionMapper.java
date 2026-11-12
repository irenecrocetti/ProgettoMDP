package it.unicam.cs.mpgc.rpg130573.persistence;

import it.unicam.cs.mpgc.rpg130573.application.session.GameSession;
import it.unicam.cs.mpgc.rpg130573.application.session.GameSessionFactory;
import it.unicam.cs.mpgc.rpg130573.application.storage.PersistenceException;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.domain.model.managers.WinxStatusManager;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;

import java.util.*;

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
            throw new PersistenceException("Versione di salvataggio non supportata: " + snapshot.version());
        if(snapshot.winxes() == null)
            throw new PersistenceException("Nel salvataggio mancano le Winx");

        Map<CharacterAllocation, WinxSnapshot> byCharacter = indexByCharacter(snapshot.winxes());
        requireExpectedParty(byCharacter.keySet());

        List<Winx> winxes = factory.getParty().stream()
                .map(character -> restore(byCharacter.get(character)))
                .toList();
        try {
             return factory.restoreGame(winxes, snapshot.highestClearedLevel());
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Salvataggio non valido: " + e.getMessage(), e);
        }
    }

    private static Map<CharacterAllocation, WinxSnapshot> indexByCharacter(List<WinxSnapshot> snapshots){
        Map<CharacterAllocation, WinxSnapshot> result = new EnumMap<>(CharacterAllocation.class);
        for (WinxSnapshot s : snapshots) {
            if (s == null || s.character() == null)
                throw new PersistenceException("Nel salvataggio c'è una Winx senza personaggio");
            if (result.put(s.character(), s) != null)
                throw new PersistenceException("Winx duplicata nel salvataggio: " + s.character());
        }
        return result;
    }

    private void requireExpectedParty(Set<CharacterAllocation> found){
        if (!found.equals(Set.copyOf(factory.getParty())))
            throw new PersistenceException("Le Winx del salvataggio " + found
            + " non corrispondono a quelle del gioco " + factory.getParty());
    }

    private Winx restore(WinxSnapshot s){
        String who = s.character().name();
        if (s.level() < WinxStatusManager.MIN_LEVEL || s.level() > WinxStatusManager.MAX_LEVEL)
            throw new PersistenceException(who + ": il livello non valido: " + s.level());
        if (s.xp() < 0)
            throw new PersistenceException(who + ": XP non validi: " + s.xp());
        if (s.purchasedSpellsIds() == null)
            throw new PersistenceException(who + ": mancano gli incantesimi acquistati");

        Winx winx = factory.createWinx(s.character());
        for (int i = WinxStatusManager.MIN_LEVEL; i < s.level(); i++){
            winx.levelUp();
        }
        winx.getStatusManager().gainXP(s.xp());
        learnPurchasedSpells(winx, s);
        winx.restore();
        return winx;
    }

    private void learnPurchasedSpells(Winx winx, WinxSnapshot s){
        Set<String> seen = new HashSet<>();
        for (String id : s.purchasedSpellsIds()) {
            if (!seen.add(id))
                throw new PersistenceException(winx.getName() + ": incantesimo duplicato: " + id);
            WinxSpell spell = factory.findSpell(id).orElseThrow(
                    () -> new PersistenceException("Incantesimo sconosciuto: " + id));
            if (spell.getCharacterAllocation() != winx.getCharacterAllocation())
                throw new PersistenceException(id + " non appartiene a " + winx.getName());
            if (spell.getUnlockLevel() > s.level())
                throw new PersistenceException(winx.getName() + " non può avere " + id
                        + ": richiede livello " + spell.getUnlockLevel());
            winx.learnSpell(spell);
        }
    }
}
