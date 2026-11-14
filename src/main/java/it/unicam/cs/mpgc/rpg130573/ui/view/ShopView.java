package it.unicam.cs.mpgc.rpg130573.ui.view;

import it.unicam.cs.mpgc.rpg130573.application.GameService;
import it.unicam.cs.mpgc.rpg130573.application.session.GameSession;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.Spell;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;
import it.unicam.cs.mpgc.rpg130573.domain.shop.PurchaseResult;
import it.unicam.cs.mpgc.rpg130573.ui.navigation.Navigator;
import it.unicam.cs.mpgc.rpg130573.ui.resource.PortraitProvider;
import it.unicam.cs.mpgc.rpg130573.ui.resource.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class ShopView extends BorderPane {
    private static final double PORTRAIT_HEIGHT = 200;

    private final GameService gameService;
    private final Navigator navigator;
    private final PortraitProvider portraits;

    private final TabPane tabs = new TabPane();
    private final Label feedback = new Label();

    public ShopView(GameService gameService, Navigator navigator, PortraitProvider portraits){
        this.gameService = gameService;
        this.navigator = navigator;
        this.portraits = portraits;

        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        setPadding(new Insets(24));
        setTop(createHeader());
        setCenter(tabs);
        setBottom(createFooter());
        rebuildTabs(0);
    }

    private Node createHeader(){
        Label title = new Label("Shop");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        Label subtitle = new Label("Ogni winx spende i propri XP per imparare nuovi incantesimi.");
        VBox header = new VBox(4, title, subtitle);
        header.setPadding(new Insets(0,0,16,0));
        return header;
    }

    private Node createFooter(){
        Button back = new Button("Torna ai livelli.");
        back.setOnAction(e -> navigator.showLevelSelect());
        feedback.setWrapText(true);
        HBox footer = new HBox(16, back, feedback);
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.setPadding(new Insets(16,0,0,0));
        return footer;
    }

    private void rebuildTabs(int selected){
        GameSession session = gameService.getCurrentSession();
        tabs.getTabs().clear();
        for (Winx winx : session.getWinxes()){
            tabs.getTabs().add(new Tab(winx.getName(), createWinxPane(session, winx)));
        }
        tabs.getSelectionModel().select(selected);
    }

    private Node createWinxPane(GameSession session, Winx winx){
        Label name = new Label(winx.getName());
        name.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label stats = new Label(String.format("Livello %d%nXP disponibili: %d",
                winx.getLevel(), winx.getXp()));
        VBox left = new VBox(8, portraits.createPortrait(winx, PORTRAIT_HEIGHT), name, stats);
        left.setAlignment(Pos.TOP_CENTER);
        left.setMinWidth(200);

        VBox right = new VBox(16,
                createOwned(winx),
                createAvailable(session, winx),
                createLocked(session, winx));
        right.setPadding(new Insets(0,12,0,0));
        ScrollPane scroll = new ScrollPane(right);
        scroll.setFitToWidth(true);
        HBox.setHgrow(scroll, Priority.ALWAYS);

        HBox pane = new HBox(24, left, scroll);
        pane.setPadding(new Insets(16));
        return pane;
    }

    private Node createOwned(Winx winx){
        VBox box = section("Incantesimi posseduti");
        for (Spell spell : winx.getSpells()){
            box.getChildren().add(new Label(spell.getName() + "  (" + staminaCost(spell) + " stamina)"));
        }
        return box;
    }

    private Node createAvailable(GameSession session, Winx winx){
        VBox box = section("Acquistabili");
        List<WinxSpell> available = session.getAvailableSpells(winx);
        List<WinxSpell> locked = session.getLockedSpells(winx);
        if (available.isEmpty() && locked.isEmpty()){
            box.getChildren().add(new Label("Hai già imparato tutti gli incantesimi"));
        } else if (available.isEmpty()){
            box.getChildren().add(new Label("Nessun incantesimo acquistabile per ora."));
        }
        for (WinxSpell spell : available){
            box.getChildren().add(createBuyRow(session, winx, spell));
        }
        return box;
    }

    private Node createBuyRow(GameSession session, Winx winx, WinxSpell spell){
        Label name = new Label(spell.getName());
        name.setStyle("-fx-font-weight: bold;");
        Label info = new Label(spell.getStaminaCost() + " stamina");

        boolean affordable = winx.getXp() >= spell.getXPCost();
        Button buy = new Button("Compra (" + spell.getXPCost() + " XP)");
        buy.setDisable(!affordable);
        buy.setOnAction(e -> onBuy(session, winx, spell));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox row = new HBox(12, name, info, spacer, buy);
        if (!affordable){
            Label missing = new Label("Mancano " + (spell.getXPCost() - winx.getXp()) + " XP");
            missing.setStyle("-fx-text-fill: " + Theme.DANGER + ";");
            row.getChildren().add(row.getChildren().size() - 1, missing);
        }
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private Node createLocked(GameSession session, Winx winx){
        VBox box = section("Da sbloccare");
        List<WinxSpell> locked = session.getLockedSpells(winx);
        if (locked.isEmpty()){
            box.getChildren().add(new Label("Niente da sbloccare."));
        }
        for (WinxSpell spell : locked){
            Label label = new Label(spell.getName() + ": livello " + spell.getUnlockLevel()
                    + " richiesto, costo " + spell.getXPCost() + " XP");
            label.setStyle("-fx-text-fill: " + Theme.TEXT_MUTED + ";");
            box.getChildren().add(label);
        }
        return box;
    }

    private static VBox section(String title){
        Label label = new Label(title);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        return new VBox(6, label);
    }

    private static int staminaCost(Spell spell){
        return spell instanceof WinxSpell ws ? ws.getStaminaCost() : 0;
    }

    private void onBuy(GameSession session, Winx winx, WinxSpell spell){
        PurchaseResult result = session.buySpell(winx, spell);
        String message = describe(result, winx, spell);
        if (result == PurchaseResult.SUCCESS && !gameService.autosave()){
            message += " (salvataggio automatico non riuscito)";
        }
        feedback.setText(message);
        rebuildTabs(tabs.getSelectionModel().getSelectedIndex());
    }

    private static String describe(PurchaseResult result, Winx winx, WinxSpell spell){
        return switch(result){
            case SUCCESS -> winx.getName() + " ha imparato " + spell.getName() + "!";
            case NOT_ENOUGH_XP -> "XP insufficienti per " + spell.getName() + ".";
            case LEVEL_TOO_LOW -> spell.getName() + " richiede il livello " + spell.getUnlockLevel();
            case ALREADY_OWNED -> winx.getName() + " conosce già " + spell.getName() + ".";
            case WRONG_CHARACTER -> spell.getName() + " non appartiene a " + winx.getName();
            case NOT_IN_SHOP -> spell.getName() + " non è in vendita.";
        };
    }
}
