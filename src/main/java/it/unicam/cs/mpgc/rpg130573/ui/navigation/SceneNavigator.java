package it.unicam.cs.mpgc.rpg130573.ui.navigation;

import it.unicam.cs.mpgc.rpg130573.application.GameService;
import it.unicam.cs.mpgc.rpg130573.application.battle.BattleController;
import it.unicam.cs.mpgc.rpg130573.ui.component.Dialogs;
import it.unicam.cs.mpgc.rpg130573.ui.view.BattleView;
import it.unicam.cs.mpgc.rpg130573.ui.view.LevelSelectView;
import it.unicam.cs.mpgc.rpg130573.ui.view.MainMenuView;
import it.unicam.cs.mpgc.rpg130573.ui.resource.PortraitProvider;
import it.unicam.cs.mpgc.rpg130573.ui.view.ShopView;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneNavigator implements Navigator{
    private static final double WIDTH = 1100;
    private static final double HEIGHT = 760;

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
       try {
           BattleController controller = new BattleController(gameService.getCurrentSession(), levelId);
           show(new BattleView(controller, this, portraits, gameService::autosave));
       } catch (IllegalStateException | IllegalArgumentException e){
           Dialogs.showError("Impossibile avviare il livello:\n" + e.getMessage());
           showLevelSelect();
       }
    }

    @Override
    public void showShop() {
        show(new ShopView(gameService, this, portraits));
    }

    private void show(Parent root){
        if(stage.getScene() == null){
            stage.setScene(new Scene(root, WIDTH, HEIGHT));
        } else {
            stage.getScene().setRoot(root);
        }
    }
}
