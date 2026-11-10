package it.unicam.cs.mpgc.rpg130573.app;

import it.unicam.cs.mpgc.rpg130573.application.GameService;
import it.unicam.cs.mpgc.rpg130573.domain.level.LevelRegistry;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.WinxFactory;
import it.unicam.cs.mpgc.rpg130573.persistence.GameSessionMapper;
import it.unicam.cs.mpgc.rpg130573.persistence.JsonSaveRepository;
import it.unicam.cs.mpgc.rpg130573.persistence.SaveService;
import it.unicam.cs.mpgc.rpg130573.domain.shop.SpellShop;
import it.unicam.cs.mpgc.rpg130573.ui.resource.PortraitProvider;
import it.unicam.cs.mpgc.rpg130573.ui.navigation.SceneNavigator;
import javafx.application.Application;
import javafx.stage.Stage;

import java.nio.file.Path;

/**
 * Punto d'ingresso dell'applicazione JavaFX.
 */
public class WinxApp extends Application {

    @Override
    public void start(Stage stage) {
        Path saveFile = Path.of(System.getProperty("user.home"), "winx.rpg", "save.json");

        GameSessionMapper mapper = new GameSessionMapper(
                WinxFactory.createDefault(),
                SpellShop.createDefault(),
                LevelRegistry.createDefault());
        SaveService saveService = new SaveService(new JsonSaveRepository(saveFile), mapper);
        GameService gameService = new GameService(saveService);

        SceneNavigator navigator = new SceneNavigator(stage, gameService, new PortraitProvider());
        stage.setTitle("Winx RPG");
        navigator.showMainMenu();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}