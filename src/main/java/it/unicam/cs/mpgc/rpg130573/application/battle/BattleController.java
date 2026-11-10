package it.unicam.cs.mpgc.rpg130573.application.battle;

import it.unicam.cs.mpgc.rpg130573.application.session.GameSession;
import it.unicam.cs.mpgc.rpg130573.application.session.LevelOutcome;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.SpellResult;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.domain.combat.ActionResult;
import it.unicam.cs.mpgc.rpg130573.domain.combat.Battle;
import it.unicam.cs.mpgc.rpg130573.domain.combat.events.BattleEvent;
import it.unicam.cs.mpgc.rpg130573.domain.level.Level;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;
import it.unicam.cs.mpgc.rpg130573.domain.model.villains.Villain;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Orchestra una battaglia a turni dal punto di vista del giocatore.
 * Conosce la sequenza del turno ma non sa nulla di come viene mostrata,
 * quindi qualsiasi UI può usarla.
 */
public class BattleController {
    private final GameSession session;
    private final Level level;
    private final Battle battle;
    private LevelOutcome outcome;

    /**
     * Avvia il livello indicato.
     *
     * @throws IllegalArgumentException se il livello non esiste
     * @throws IllegalStateException    se è bloccato o c'è già una battaglia in corso
     */
    public BattleController(GameSession session, int levelId){
        this.session = Objects.requireNonNull(session);
        this.level = session.getLevels().stream()
                .filter(l -> l.getId() == levelId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Livello inesistente: " + levelId));
        this.battle = session.startLevel(levelId);
    }

    public int getLevelId() { return level.getId(); }

    public String getLevelName() { return level.getName(); }

    /** @return le Winx attualmente in battaglia (può cambiare: vedi l'evento di Darkar) */
    public List<Winx> getWinxes() { return battle.getWinxes(); }

    /** @return i nemici attualmente in battaglia */
    public List<Villain> getVillains() { return battle.getVillains(); }

    public boolean isFinished() { return battle.isFinished(); }

    /** @return la Winx che deve agire, null se la battaglia è finita */
    public Winx getCurrentWinx() {
        return battle.isFinished() ? null : battle.getCurrentWinx();
    }

    public boolean needsTarget(Spell spell) {
        return spell.getTargetType() != TargetType.SELF;
    }

    public List<Entity> getValidTargets(Spell spell){
        return battle.getValidTargets(spell);
    }

    /** @return gli incantesimi della Winx corrente, vuota se la battaglia è finita */
    public List<SpellOption> getSpellOptions(){
        Winx winx = getCurrentWinx();
        if(winx == null) return List.of();
        return winx.getSpells().stream().map(s -> toOption(winx, s)).toList();
    }

    private SpellOption toOption(Winx winx, Spell spell){
        int cost = (spell instanceof WinxSpell ws) ? ws.getStaminaCost() : 0;
        boolean hasTargets = !needsTarget(spell) || !battle.getValidTargets(spell).isEmpty();
        return new SpellOption(spell, cost, winx.getStamina() >= cost && hasTargets);
    }

    /**
     * La Winx corrente lancia un incantesimo.
     *
     * @param target ignorato per gli incantesimi su sé stessi
     * @return il resoconto. Se l'azione è rifiutata ({@link TurnReport#turnConsumed()}
     *         è false) lo stato della battaglia non cambia.
     */
    public TurnReport castSpell(Spell spell, Entity target){
        Objects.requireNonNull(spell);
        requireOngoing();
        Winx caster = battle.getCurrentWinx();
        Entity actualTarget = needsTarget(spell) ? target : caster;

        SpellResult result = battle.executeWinxSpell(spell, actualTarget);
        ActionResult action = new ActionResult(caster, actualTarget, spell, result);
        if(result == SpellResult.CANNOT_CAST)
            return new TurnReport(caster, Optional.of(action), List.of(), List.of(), false);
        return conclude(caster, Optional.of(action));
    }

    /** La Winx corrente riposa: recupera stamina e passa il turno. */
    public TurnReport rest(){
        requireOngoing();
        Winx caster = battle.getCurrentWinx();
        battle.passTurn();
        return conclude(caster, Optional.empty());
    }

    /**
     * Chiude il livello assegnando XP e progressi. Chiamarlo più volte restituisce
     * lo stesso esito senza assegnare due volte i premi.
     *
     * @throws IllegalStateException se la battaglia non è finita
     */
    public LevelOutcome complete(){
        if(outcome == null)
            outcome = session.completeLevel();
        return outcome;
    }

    /** Abbandona la battaglia senza ricompense. */
    public void abandon() {
        session.abandonLevel();
    }

    /**
     * Dopo un'azione: controlla se la battaglia è finita, altrimenti, se tutte
     * le Winx hanno giocato, fa agire i nemici e avvia il nuovo round.
     */
    private TurnReport conclude(Winx actor, Optional<ActionResult> action){
        List<ActionResult> enemyActions = List.of();
        List<BattleEvent> events = List.of();
        if(!battle.isFinished() && battle.allWinxPlayed()){
            enemyActions = battle.executeEnemyTurn();
            if(!battle.isFinished()) events = battle.startNewRound();
        }
        return new TurnReport(actor, action, enemyActions, events, battle.isFinished());
    }

    private void requireOngoing(){
        if(battle.isFinished())
            throw new IllegalStateException("La battaglia è già finita.");
    }
}
