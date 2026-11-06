package it.unicam.cs.mpgc.rpg130573.combat;

import it.unicam.cs.mpgc.rpg130573.abilities.enums.SpellResult;
import it.unicam.cs.mpgc.rpg130573.abilities.enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.abilities.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.combat.villainSelectors.RandomSpellChoice;
import it.unicam.cs.mpgc.rpg130573.combat.villainSelectors.RandomTargeting;
import it.unicam.cs.mpgc.rpg130573.combat.villainSelectors.SpellChoiceStrategy;
import it.unicam.cs.mpgc.rpg130573.combat.villainSelectors.TargetingStrategy;
import it.unicam.cs.mpgc.rpg130573.model.Entity;
import it.unicam.cs.mpgc.rpg130573.model.winx.Winx;
import it.unicam.cs.mpgc.rpg130573.model.villains.Villain;

import java.util.ArrayList;
import java.util.List;

public class Battle {
    private final List<Winx> winxes;
    private final List<Villain> villains;
    private final BattleManager battleManager;
    private final SpellChoiceStrategy spellChoice;
    private final TargetingStrategy targetingStrategy;

    private int currentWinxIndex = 0;

    /** Costruttore con comportamento di default. */
    public Battle(List<Winx> winxes, List<Villain> villains){
        this(winxes, villains,
                new BattleManager(),
                new RandomSpellChoice(),
                new RandomTargeting());
    }

    public Battle(List<Winx> winxes, List<Villain> villains,
                  BattleManager battleManager,
                  SpellChoiceStrategy spellChoice,
                  TargetingStrategy targetingStrategy){
        this.winxes = new ArrayList<>(winxes);
        this.villains = new ArrayList<>(villains);
        this.battleManager = battleManager;
        this.spellChoice = spellChoice;
        this.targetingStrategy = targetingStrategy;
    }

    /** @return copia non modificabile della lista delle Winx in battaglia */
    public List<Winx> getWinxes() {return List.copyOf(winxes);}

    /** @return copia non modificabile della lista dei nemici in battaglia */
    public List<Villain> getVillains() {return List.copyOf(villains);}

    public void startBattle(){
        battleManager.prepareBattle(winxes, villains);
        startNewRound();
    }

    public void startNewRound(){
        battleManager.startNewRound(winxes);
        currentWinxIndex = 0;
        skipDeadWinxes();
    }

    /** @return la Winx che deve agire o null se tutte hanno giocato */
    public Winx getCurrentWinx(){
        return allWinxPlayed() ? null : winxes.get(currentWinxIndex);
    }

    public boolean allWinxPlayed(){
        return currentWinxIndex >= winxes.size();
    }

    /**
     * Esegue l'incantesimo della Winx corrente e ne conclude il turno.
     * Gli effetti attivi sulla Winx vengono "tickati" subito dopo il lancio,
     * quindi un effetto appena applicato a sé stessa (es. uno scudo) consuma
     * un turno di durata nello stesso turno in cui è stato creato.
     * Il comportamento è voluto: la durata include il turno del lancio.
     * Se il lancio non è possibile il turno NON viene sprecato.
     *
     * @param target ignorato per gli incantesimi di tipo SELF
     */
    public SpellResult executeWinxSpell(Spell spell, Entity target){
        Winx winx = getCurrentWinx();
        if(winx == null || spell == null || !winx.hasSpell(spell))
            return SpellResult.CANNOT_CAST;

        Entity actualTarget = (spell.getTargetType() == TargetType.SELF) ? winx : target;
        if(!isValidTarget(spell, actualTarget))
            return SpellResult.CANNOT_CAST;

        SpellResult result = spell.execute(winx, actualTarget);
        if(result == SpellResult.CANNOT_CAST) return result;

        winx.getEffectManager().onTurnEnd();
        advanceToNextWinx();
        return result;
    }

    /**
     * Fa agire tutti i nemici vivi.
     *
     * @return le azioni compiute, nell'ordine di esecuzione.
     */
    public List<ActionResult> executeEnemyTurn(){
        List<ActionResult> log = new ArrayList<>();
        for(Villain villain : villains){
            if(!villain.isAlive()) continue;
            if(!winxesAlive()) break;

            Spell spell = spellChoice.chooseSpell(villain);
            Entity target = targetingStrategy.chooseTarget(spell, villain, winxes, villains);
            SpellResult result = spell.execute(villain, target);
            log.add(new ActionResult(villain, target, spell, result));

            villain.getEffectManager().onTurnEnd();
        }
        return log;
    }

    public boolean winxesAlive() { return winxes.stream().anyMatch(Winx::isAlive); }

    public boolean villainsAlive() { return villains.stream().anyMatch(Entity::isAlive); }

    public boolean isFinished() { return !winxesAlive() || !villainsAlive(); }

    public boolean winxWon() { return winxesAlive() && !villainsAlive(); }

    private void skipDeadWinxes(){
        while(currentWinxIndex < winxes.size() && !winxes.get(currentWinxIndex).isAlive())
            currentWinxIndex++;
    }

    /** Il bersaglio deve esistere o comunque appartenere alla fazione richiesta */
    private boolean isValidTarget(Spell spell, Entity target){
        if(target == null) return false;
        return switch (spell.getTargetType()){
            case ENEMY -> villains.contains(target);
            case ALLY -> winxes.contains(target);
            case SELF -> true;
        };
    }

    private void advanceToNextWinx() {
        currentWinxIndex++;
        skipDeadWinxes();
    }
}
