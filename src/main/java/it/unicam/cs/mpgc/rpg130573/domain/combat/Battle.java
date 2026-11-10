package it.unicam.cs.mpgc.rpg130573.domain.combat;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.SpellResult;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.domain.combat.events.BattleContext;
import it.unicam.cs.mpgc.rpg130573.domain.combat.events.BattleEvent;
import it.unicam.cs.mpgc.rpg130573.domain.combat.selectors.RandomSpellChoice;
import it.unicam.cs.mpgc.rpg130573.domain.combat.selectors.RandomTargeting;
import it.unicam.cs.mpgc.rpg130573.domain.combat.selectors.SpellChoiceStrategy;
import it.unicam.cs.mpgc.rpg130573.domain.combat.selectors.TargetingStrategy;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;
import it.unicam.cs.mpgc.rpg130573.domain.model.villains.Villain;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Battle {
    private final List<Winx> winxes;
    private final List<Villain> villains;
    private final BattleManager battleManager;
    private final SpellChoiceStrategy spellChoice;
    private final TargetingStrategy targetingStrategy;
    private final List<BattleEvent> pendingEvents;
    private final Predicate<List<Villain>> victoryCondition;

    private int currentWinxIndex = 0;

    /** Costruttore con comportamento di default. */
    public Battle(List<Winx> winxes, List<Villain> villains,
                  List<BattleEvent> events,
                  Predicate<List<Villain>> victoryCondition){
        this(winxes, villains, events, victoryCondition,
                new BattleManager(),
                new RandomSpellChoice(),
                new RandomTargeting());
    }

    public Battle(List<Winx> winxes, List<Villain> villains,
                  List<BattleEvent> events,
                  Predicate<List<Villain>> victoryCondition,
                  BattleManager battleManager,
                  SpellChoiceStrategy spellChoice,
                  TargetingStrategy targetingStrategy){
        this.winxes = new ArrayList<>(winxes);
        this.villains = new ArrayList<>(villains);
        this.pendingEvents = new ArrayList<>(events);
        this.victoryCondition = victoryCondition;
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

    /**
     * Inizia un nuovo round: controlla gli eventi, rigenera la stamina e
     * riporta il turno alla prima Winx viva. Gli eventi si controllano qui
     * perché è l'unico momento in cui è sicuro modificare le liste dei
     * combattenti (nessun turno in corso).
     *
     * @return gli eventi scattati, nell'ordine, perché la UI possa mostrarli
     */
    public List<BattleEvent> startNewRound(){
        List<BattleEvent> triggered = checkEvents();
        battleManager.startNewRound(winxes);
        currentWinxIndex = 0;
        skipDeadWinxes();
        return triggered;
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
     * @return i bersagli validi (vivi) per l'incantesimo indicato, lanciato dalla
     * Winx corrente. Lista vuota se nessuna Winx può agire o non possiede l'incantesimo.
     * Per gli incantesimi SELF restituisce la sola Winx corrente.
     */
    public List<Entity> getValidTargets(Spell spell){
        Winx winx = getCurrentWinx();
        if(winx == null || spell == null || !winx.hasSpell(spell))
            return List.of();
        if(spell.getTargetType() == TargetType.SELF)
            return List.of(winx);

        List<? extends Entity> candidates =
                (spell.getTargetType() == TargetType.ENEMY) ? villains : winxes;
        return candidates.stream()
                .filter(Entity::isAlive)
                .filter(e -> isValidTarget(spell, e))
                .map(e -> (Entity) e)
                .toList();
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
            if(!winxesAlive() || isWon()) break;

            Spell spell = spellChoice.chooseSpell(villain);
            Entity target = targetingStrategy.chooseTarget(spell, villain, winxes, villains);
            SpellResult result = spell.execute(villain, target);
            log.add(new ActionResult(villain, target, spell, result));

            villain.getEffectManager().onTurnEnd();
        }
        return log;
    }

    /**
     * La Winx corrente rinuncia a lanciare un incantesimo: recupera della
     * stamina e il turno passa alla Winx successiva. Evita che la battaglia
     * si blocchi quando la Winx non può lanciare nessun incantesimo
     *
     * @return true se il turno è stato passato, false se nessuna Winx può agire
     */
    public boolean passTurn(){
        Winx winx = getCurrentWinx();
        if(winx == null) return false;
        battleManager.restWinx(winx);
        winx.getEffectManager().onTurnEnd();
        advanceToNextWinx();
        return true;
    }

    /** @return true se la condizione di vittoria del livello è soddisfatta */
    public boolean isWon() { return victoryCondition.test(villains); }

    public boolean winxesAlive() { return winxes.stream().anyMatch(Winx::isAlive); }

    public boolean villainsAlive() { return villains.stream().anyMatch(Entity::isAlive); }

    public boolean isFinished() { return !winxesAlive() || isWon(); }

    public boolean winxWon() { return winxesAlive() && isWon(); }

    private void skipDeadWinxes(){
        while(currentWinxIndex < winxes.size() && !winxes.get(currentWinxIndex).isAlive())
            currentWinxIndex++;
    }

    /**
     * Il bersaglio deve esistere o comunque appartenere alla fazione richiesta e
     * se l'incantesimo è dannoso, non essere vulnerabile.
     */
    private boolean isValidTarget(Spell spell, Entity target){
        if(target == null) return false;
        if(isHarmful(spell) && target.isInvulnerable()) return false;
        return switch (spell.getTargetType()){
            case ENEMY -> villains.contains(target);
            case ALLY -> winxes.contains(target);
            case SELF -> true;
        };
    }

    private boolean isHarmful(Spell spell){
        return spell.getSpellType() == SpellType.OFFENSE
                || spell.getSpellType() == SpellType.DEBUFF;
    }

    private void advanceToNextWinx() {
        currentWinxIndex++;
        skipDeadWinxes();
    }

    private List<BattleEvent> checkEvents(){
        BattleContext context = new Context();
        List<BattleEvent> triggered = new ArrayList<>();
        for(BattleEvent event : List.copyOf(pendingEvents)){
            if(event.isTriggered(context)){
                event.trigger(context);
                pendingEvents.remove(event);
                triggered.add(event);
            }
        }
        return triggered;
    }

    private class Context implements BattleContext{

        @Override
        public List<Winx> getWinxes() { return Battle.this.getWinxes(); }

        @Override
        public List<Villain> getVillains() { return Battle.this.getVillains(); }

        @Override
        public void removeWinx(Winx winx) { winxes.remove(winx); }

        @Override
        public void addVillain(Villain villain) { villains.add(villain); }
    }
}
