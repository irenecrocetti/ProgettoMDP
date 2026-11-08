package it.unicam.cs.mpgc.rpg130573.game;

import it.unicam.cs.mpgc.rpg130573.abilities.spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.combat.Battle;
import it.unicam.cs.mpgc.rpg130573.levels.Level;
import it.unicam.cs.mpgc.rpg130573.levels.LevelRegistry;
import it.unicam.cs.mpgc.rpg130573.model.villains.Villain;
import it.unicam.cs.mpgc.rpg130573.model.winx.Bloom;
import it.unicam.cs.mpgc.rpg130573.model.winx.Stella;
import it.unicam.cs.mpgc.rpg130573.model.winx.Tecna;
import it.unicam.cs.mpgc.rpg130573.model.winx.Winx;
import it.unicam.cs.mpgc.rpg130573.shop.PurchaseResult;
import it.unicam.cs.mpgc.rpg130573.shop.SpellShop;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class GameSession {
    private final List<Winx> winxes;
    private final SpellShop shop;
    private final LevelRegistry registry;
    private int highestClearedLevel;

    private Level activeLevel;
    private Battle activeBattle;

    /**
     * Crea un nuovo salvataggio, le winx al livello 1, nessun livello superato
     */
    public static GameSession newGame(){
        return new GameSession(
                List.of(new Bloom(), new Stella(), new Tecna()),
                SpellShop.createDefault(),
                LevelRegistry.createDefault(),
                0);
    }

    public GameSession(List<Winx> winxes, SpellShop shop,
                       LevelRegistry registry, int highestClearedLevel){
        if(highestClearedLevel < 0 || highestClearedLevel > registry.size())
            throw new IllegalArgumentException("Progresso non valido: " + highestClearedLevel);
        this.winxes = List.copyOf(winxes);
        this.shop = shop;
        this.registry = registry;
        this.highestClearedLevel = highestClearedLevel;
    }

    public List<Winx> getWinxes() { return this.winxes; }

    public List<Level> getLevels() { return registry.getLevels(); }

    public int getHighestClearedLevel() { return this.highestClearedLevel; }

    public boolean isUnlocked(int levelId){
        return registry.getLevel(levelId).isPresent()
                && levelId <= highestClearedLevel + 1;
    }

    public boolean isCompleted() { return highestClearedLevel == registry.size(); }

    public boolean hasActiveBattle() { return activeBattle != null; }

    public Battle startLevel(int levelId){
        if(activeBattle != null)
            throw new IllegalStateException("C'è una battaglia già in corso");
        Level level = registry.getLevel(levelId)
                .orElseThrow(() -> new IllegalArgumentException("Livello inesistente: " + levelId));
        if(!isUnlocked(levelId))
            throw new IllegalStateException("Livello non ancora sbloccato: " + levelId);

        Battle battle = new Battle(winxes, level.createVillains(),
                level.createEvents(), level::isWon);
        battle.startBattle();

        this.activeLevel = level;
        this.activeBattle = battle;
        return battle;
    }

    public LevelOutcome completeLevel(){
        if(activeBattle == null)
            throw new IllegalStateException("Nessuna battaglia in corso");
        if(!activeBattle.isFinished())
            throw new IllegalStateException("La battaglia non è ancora finita");

        Battle battle = activeBattle;
        Level level = activeLevel;
        activeBattle = null;
        activeLevel = null;

        if(!battle.winxWon())
            return LevelOutcome.defeat(level.getId());

        int xp = battle.getVillains().stream().mapToInt(Villain::getXPReleased).sum();
        winxes.forEach(w -> w.getStatusManager().gainXP(xp));

        boolean firstClear = level.getId() > highestClearedLevel;
        OptionalInt unlocked = OptionalInt.empty();
        if(firstClear){
            winxes.forEach(Winx::levelUp);
            highestClearedLevel = level.getId();
            Optional<Level> next = registry.getNext(level.getId());
            if(next.isPresent()) unlocked = OptionalInt.of(next.get().getId());
        }
        return new LevelOutcome(level.getId(), true, xp, firstClear, unlocked);
    }

    public void abandonLevel(){
        activeBattle = null;
        activeLevel = null;
    }

    public List<WinxSpell> getAvailableSpells(Winx buyer) { return shop.getAvailableSpells(buyer); }

    public List<WinxSpell> getLockedSpells(Winx buyer) { return shop.getLockedSpells(buyer); }

    public PurchaseResult buySpell(Winx buyer, WinxSpell spell){
        if(!winxes.contains(buyer))
            throw new IllegalArgumentException("La Winx non fa parte di questa partita");
        if(activeBattle != null)
            throw new IllegalStateException("Lo shop non è usabile durante una battaglia");
        return shop.buy(buyer, spell);
    }
}
