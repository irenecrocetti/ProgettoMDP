package it.unicam.cs.mpgc.rpg130573.ui;

import it.unicam.cs.mpgc.rpg130573.game.GameService;
import it.unicam.cs.mpgc.rpg130573.game.GameSession;
import it.unicam.cs.mpgc.rpg130573.game.LevelStatus;
import it.unicam.cs.mpgc.rpg130573.levels.Level;
import it.unicam.cs.mpgc.rpg130573.model.winx.Winx;
import it.unicam.cs.mpgc.rpg130573.persistence.PersistenceException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import javafx.scene.control.Label;

public class LevelSelectView extends BorderPane {
    private static final double PARTY_PORTRAIT_HEIGHT = 90;

    private final GameService gameService;
    private final Navigator navigator;
    private final Label feedback = new Label();
    private final PortraitProvider portraits;

    public LevelSelectView(GameService gameService, Navigator navigator, PortraitProvider portraits){
        this.gameService = gameService;
        this.navigator = navigator;
        this.portraits = portraits;
        GameSession session = gameService.getCurrentSession();

        setPadding(new Insets(24));
        setTop(createHeader(session));
        setCenter(createLevelList(session));
        setRight(createPartyPanel(session));
        setBottom(createFooter());
    }

    private Node createHeader(GameSession session){
        Label title = new Label("Seleziona livello");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        VBox header = new VBox(4, title);
        if(session.isCompleted()){
            header.getChildren()
                    .add(new Label("Hai completato tutti i livelli! Puoi rigiocarli per ottenere XP"));
        }
        header.setPadding(new Insets(0,0,16,0));
        return header;
    }

    private Node createLevelList(GameSession session){
        VBox list = new VBox(12);
        for(Level level : session.getLevels()){
            list.getChildren().add(createLevelRow(session, level));

        }
        return list;
    }

    private Node createLevelRow(GameSession session, Level level){
        Label name = new Label(level.getId() + ". " + level.getName());
        name.setStyle("-fx-font-size: 18px;");

        LevelStatus status = session.getLevelStatus(level.getId());
        Label statusLabel = new Label(statusText(status));
        statusLabel.setStyle("-fx-text-fill: " + statusColor(status) + ";");

        Button play = new Button(buttonText(status));
        play.setPrefWidth(110);
        play.setDisable(status == LevelStatus.LOCKED);
        play.setOnAction(e -> navigator.showBattle(level.getId()));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(16, name, statusLabel, play, spacer);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12));
        row.setStyle("-fx-border-color: #bbbbbb; -fx-border-radius: 6;");
        return row;
    }

    private static String statusText(LevelStatus status){
        return switch(status) {
            case COMPLETED -> "Completato";
            case LOCKED -> "Bloccato";
            case AVAILABLE -> "Disponibile";
        };
    }

    private static String statusColor(LevelStatus status){
        return switch(status) {
            case COMPLETED -> "#2e7d32";
            case AVAILABLE -> "#1565c0";
            case LOCKED -> "#757575";
        };
    }

    private static String buttonText(LevelStatus status){
        return switch(status) {
            case COMPLETED -> "Rigioca";
            case LOCKED -> "Bloccato";
            case AVAILABLE -> "Gioca";
        };
    }

    private Node createPartyPanel(GameSession session){
        Label title = new Label("Le Winx");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox panel = new VBox(10, title);
        for(Winx winx : session.getWinxes()){
            Label name = new Label(winx.getName());
            name.setStyle("-fx-font-weight: bold;");
            Label stats = new Label(String.format("Livello %d   XP %d%nHP %d   Stamina %d",
                    winx.getLevel(), winx.getXp(), winx.getMaxHP(), winx.getMaxStamina()));

            HBox row = new HBox(10,
                    portraits.createPortrait(winx, PARTY_PORTRAIT_HEIGHT),
                    new VBox(2, name, stats));
            row.setAlignment(Pos.CENTER_LEFT);
            panel.getChildren().add(row);
        }

        panel.setPadding(new Insets(0,0,0,24));
        panel.setMinWidth(280);
        return panel;
    }

    private Node createFooter(){
        Button save = new Button("Salva partita");
        save.setOnAction(e -> onSave());

        Button shop = new Button("Shop");
        shop.setOnAction(e -> navigator.showShop());

        Button back = new Button("Menu principale");
        back.setOnAction(e -> onBackToMenu());

        HBox buttons = new HBox(12, save, shop, back, feedback);
        buttons.setAlignment(Pos.CENTER_LEFT);
        buttons.setPadding(new Insets(16,0,0,0));
        return buttons;
    }

    private void onSave(){
        try{
            gameService.saveCurrent();
            feedback.setText("Partita salvata.");
        } catch (PersistenceException e){
            feedback.setText("");
            Dialogs.showError("Impossibile salvare la partita:\n" + e.getMessage());
        }
    }

    private void onBackToMenu(){
        if(Dialogs.confirm("I progressi non salvati andranno persi. Tornare al menu?")) {
            navigator.showMainMenu();
        }
    }
}
