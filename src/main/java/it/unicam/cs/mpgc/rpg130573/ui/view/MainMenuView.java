package it.unicam.cs.mpgc.rpg130573.ui.view;

import it.unicam.cs.mpgc.rpg130573.application.GameService;
import it.unicam.cs.mpgc.rpg130573.persistence.PersistenceException;
import it.unicam.cs.mpgc.rpg130573.ui.component.Dialogs;
import it.unicam.cs.mpgc.rpg130573.ui.navigation.Navigator;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class MainMenuView extends VBox {
    public static final double BUTTON_WIDTH = 220;

    private final GameService gameService;
    private final Navigator navigator;

    public MainMenuView(GameService gameService, Navigator navigator){
        super(16);
        this.gameService = gameService;
        this.navigator = navigator;

        Label title = new Label("Winx RPG");
        title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");

        Button newGame = createButton("Nuova partita", this::onNewGame);
        Button continueGame = createButton("Continua", this::onContinue);
        continueGame.setDisable(!gameService.hasSave());
        Button exit = createButton("Esci", Platform::exit);

        setAlignment(Pos.CENTER);
        getChildren().addAll(title, newGame, continueGame, exit);
    }

    private Button createButton(String text, Runnable action){
        Button button = new Button(text);
        button.setPrefWidth(BUTTON_WIDTH);
        button.setOnAction(e -> action.run());
        return button;
    }

    private void onNewGame(){
        if(gameService.hasSave() &&
                !Dialogs.confirm("Esiste già un salvataggio. Verrà sovrascritto al prossimo salvataggio. Continuare?"))
            return;
        gameService.startNewGame();
        navigator.showLevelSelect();
    }

    private void onContinue(){
        try{
            gameService.continueGame();
            navigator.showLevelSelect();
        } catch (PersistenceException e){
            Dialogs.showError("Impossibile caricare il salvataggio:\n" + e.getMessage());
        }
    }
}
