package it.unicam.cs.mpgc.rpg130573.ui;

import it.unicam.cs.mpgc.rpg130573.game.GameService;
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
    private final PortraitProvider portraits;

    public SceneNavigator(Stage stage, GameService gameService, PortraitProvider portraits){
        this.stage = stage;
        this.gameService = gameService;
        this.portraits = portraits;
    }

    @Override
    public void showMainMenu() {
        show(new MainMenuView(gameService, this));
    }

    @Override
    public void showLevelSelect() {
        show(new LevelSelectView(gameService, this, portraits));
    }

    @Override
    public void showBattle(int levelId) {
        //TODO
        show(placeholder("Battaglia, livello " + levelId, this::showLevelSelect));
    }

    @Override
    public void showShop() {
        //TODO
        show(placeholder("Shop ", this::showLevelSelect));
    }

    private Parent placeholder(String text, Runnable onBack){
        Button back = new Button("Indietro");
        back.setOnAction(e -> onBack.run());
        VBox box = new VBox(16, new Label(text), back);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void show(Parent root){
        if(stage.getScene() == null){
            stage.setScene(new Scene(root, WIDTH, HEIGHT));
        } else {
            stage.getScene().setRoot(root);
        }
    }
}
