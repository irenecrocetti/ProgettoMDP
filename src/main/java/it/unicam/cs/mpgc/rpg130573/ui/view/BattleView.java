package it.unicam.cs.mpgc.rpg130573.ui.view;

import it.unicam.cs.mpgc.rpg130573.application.battle.BattleController;
import it.unicam.cs.mpgc.rpg130573.application.battle.SpellOption;
import it.unicam.cs.mpgc.rpg130573.application.battle.TurnReport;
import it.unicam.cs.mpgc.rpg130573.application.session.LevelOutcome;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;
import it.unicam.cs.mpgc.rpg130573.ui.component.CombatantCard;
import it.unicam.cs.mpgc.rpg130573.ui.component.Dialogs;
import it.unicam.cs.mpgc.rpg130573.ui.navigation.Navigator;
import it.unicam.cs.mpgc.rpg130573.ui.resource.PortraitProvider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;

import java.util.List;
import java.util.function.Supplier;

public class BattleView extends BorderPane {
    private static final double PORTRAIT_HEIGHT = 150;

    private final BattleController controller;
    private final Navigator navigator;
    private final PortraitProvider portraits;

    private final HBox winxRow = new HBox(10);
    private final HBox villainRow = new HBox(10);
    private final VBox actionPanel = new VBox();
    private final TextArea log = new TextArea();

    private Spell pendingSpell;
    private LevelOutcome outcome;

    public BattleView(BattleController controller, Navigator navigator, PortraitProvider portraits){
        this.controller = controller;
        this.navigator = navigator;
        this.portraits = portraits;

        setPadding(new Insets(16));
        setTop(createTitle());
        setCenter(createBattlefield());
        setBottom(createBottomArea());

        appendLog("Inizia la battaglia: " + controller.getLevelName());
        refresh();
    }

    private Node createTitle() {
        Label title = new Label((controller.getLevelName()));
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");
        title.setPadding(new Insets(0,0,12,0));
        return title;
    }

    private Node createBattlefield() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox field = new HBox(16, winxRow, spacer, villainRow);
        field.setAlignment(Pos.TOP_CENTER);
        return field;
    }

    private Node createBottomArea() {
        actionPanel.setMinWidth(380);
        actionPanel.setPrefWidth(380);

        log.setEditable(false);
        log.setWrapText(true);
        log.setPrefRowCount(9);
        HBox.setHgrow(log, Priority.ALWAYS);

        HBox bottom = new HBox(16, actionPanel, log);
        bottom.setPadding(new Insets(12,0,0,0));
        return bottom;
    }

    private void refresh() {
        Winx current = controller.getCurrentWinx();
        List<Entity> targets = pendingSpell == null ? List.of() : controller.getValidTargets(pendingSpell);

        winxRow.getChildren().setAll(createCards(controller.getWinxes(), current, targets));
        villainRow.getChildren().setAll(createCards(controller.getVillains(), current, targets));
        actionPanel.getChildren().setAll(outcome == null ? createTurnPanel() : createOutcomePanel());
    }

    private List<CombatantCard> createCards(List<? extends Entity> entities, Winx current, List<Entity> targets){
        return entities.stream().map(e -> createCard(e, current, targets)).toList();
    }

    private CombatantCard createCard(Entity entity, Winx current, List<Entity> targets){
        CombatantCard.Highlight highlight = targets.contains(entity) ? CombatantCard.Highlight.TARGETABLE
                : entity == current ? CombatantCard.Highlight.ACTIVE
                : CombatantCard.Highlight.NONE;
        CombatantCard card = new CombatantCard(entity,
                portraits.createPortrait(entity, PORTRAIT_HEIGHT), highlight);
        if(highlight == CombatantCard.Highlight.TARGETABLE) {
            card.setOnMouseClicked(e -> onTargetClicked(entity));
        }
        return card;
    }

    private Node createTurnPanel(){
        Winx winx = controller.getCurrentWinx();

        Label turn = new Label("Turno di " + winx.getName());
        turn.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label hint = new Label(pendingSpell == null
                ? "Scegli un incantesimo."
                : "Scegli un bersaglio per " + pendingSpell.getName()
                + " cliccando un personaggio evidenziato.");
        hint.setWrapText(true);

        FlowPane spells = new FlowPane(8,8);
        spells.setPrefWrapLength(360);
        for (SpellOption option : controller.getSpellOptions()) {
            spells.getChildren().add(createSpellButton(option));
        }

        Button rest = new Button("Riposa (+ stamina)");
        rest.setOnAction(e -> perform(controller::rest));
        Button abandon = new Button("Abbandona");
        abandon.setOnAction(e -> onAbandon());

        return new VBox(10, turn, hint, spells, new HBox(8, rest, abandon));
    }

    private Button createSpellButton(SpellOption option){
        Spell spell = option.spell();
        Button button = new Button(spell.getName() + "\n" + option.staminaCost() + " stamina");
        button.setPrefWidth(170);
        button.setDisable(!option.castable());
        if (spell == pendingSpell) {
            button.setStyle("-fx-border-color: #f9a825; -fx-border-width: 3; -fx-border-radius: 4;");
        }
        button.setOnAction(e -> onSpellClicked(spell));
        return button;
    }

    private Node createOutcomePanel() {
        VBox box = new VBox(10);
        if (outcome.won()) {
            Label title = new Label("Vittoria!");
            title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
            box.getChildren().addAll(title, new Label("Ogni Winx guadagna " + outcome.xpGained() + " XP."));
            if (outcome.firstClear())
                box.getChildren().add(new Label("Prima vittoria su questo livello!"));
            outcome.unlockedLevelId().ifPresent(id ->
                    box.getChildren().add(new Label("Hai sbloccato il livello " + id + ".")));
            Button next = new Button("Continua");
            next.setOnAction(e -> navigator.showLevelSelect());
            box.getChildren().add(next);
        } else {
            Label title = new Label("Sconfitta...");
            title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #c62828;");
            Button retry = new Button("Riprova");
            retry.setOnAction(e -> navigator.showBattle(controller.getLevelId()));
            Button back = new Button("Torna ai livelli");
            back.setOnAction(e -> navigator.showLevelSelect());
            box.getChildren().addAll(title, new HBox(8, retry, back));
        }
        return box;
    }

    private void onSpellClicked(Spell spell) {
        if (!controller.needsTarget(spell)) {
            perform(() -> controller.castSpell(spell, null));
            return;
        }
        pendingSpell = (pendingSpell == spell) ? null : spell;
        refresh();
    }

    private void onTargetClicked(Entity target){
        Spell spell = pendingSpell;
        if (spell == null) return;
        perform(() -> controller.castSpell(spell, target));
    }

    private void onAbandon() {
        if (Dialogs.confirm("Abbandonare la battaglia? Non otterrai XP.")) {
            controller.abandon();
            navigator.showLevelSelect();
        }
    }

    private void perform(Supplier<TurnReport> action){
        pendingSpell = null;
        TurnReport report = action.get();
        TurnReportFormatter.format(report).forEach(this::appendLog);
        if (report.battleFinished()) {
            outcome = controller.complete();
            appendLog(outcome.won() ? "Le Winx hanno vinto!" : "Le Winx sono state sconfitte...");
        }
        refresh();
    }

    private void appendLog(String line){
        log.appendText(line + "\n");
    }
}
