package it.unicam.cs.mpgc.rpg130573.ui;

import it.unicam.cs.mpgc.rpg130573.game.GameService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneNavigator implements Navigator{
    private static final double WIDTH = 1000;
    private static final double HEIGHT = 700;

    private final Stage stage;
    private final GameService gameService;

    public SceneNavigator(Stage stage, GameService gameService){
        this.stage = stage;
        this.gameService = gameService;
    }

    @Override
    public void showMainMenu() {
        show(new MainMenuView(gameService, this));
    }

    @Override
    public void showLevelSelect() {
        Label label = new Label("Selezione livelli");
        Button back = new Button("Torna al menu");
        back.setOnAction(e -> showMainMenu());
        VBox box = new VBox(16, label, back);
        box.setAlignment(Pos.CENTER);
        show(box);
    }

    private void show(Parent root){
        if(stage.getScene() == null){
            stage.setScene(new Scene(root, WIDTH, HEIGHT));
        } else {
            stage.getScene().setRoot(root);
        }
    }
}
