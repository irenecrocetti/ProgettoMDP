package it.unicam.cs.mpgc.rpg130573.ui;

import it.unicam.cs.mpgc.rpg130573.model.Entity;
import it.unicam.cs.mpgc.rpg130573.model.winx.Winx;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Fornisce l'immagine di un personaggio. I file da cercare li decide
 * {@link PortraitKeys}; se nessuno esiste mostra un segnaposto.
 */
public class PortraitProvider {
    private static final String BASE_PATH = "/images/characters/";
    private static final double LOAD_HEIGHT = 300;

    /** Cache per singolo file: anche l'assenza di un file viene ricordata */
    private final Map<String, Optional<Image>> cache = new HashMap<>();

    /**
     * @param height altezza a cui mostrare il ritratto
     */
    public Node createPortrait(Entity entity, double height){
        Node portrait = findImage(entity)
                .<Node>map(img -> imageView(img, height))
                .orElseGet(() -> placeHolder(entity, height));
        StackPane box = new StackPane(portrait);
        box.setMinHeight(height);
        box.setPrefHeight(height);
        return box;
    }

    private Optional<Image> findImage(Entity entity){
        for(String key : PortraitKeys.candidates(entity)){
            Optional<Image> image = cache.computeIfAbsent(key, this::load);
            if(image.isPresent()) return image;
        }
        return Optional.empty();
    }

    private Optional<Image> load(String key){
        try (InputStream in = PortraitProvider.class.getResourceAsStream(BASE_PATH + key + ".png")){
            if(in == null) return Optional.empty();
            Image image = new Image(in, 0, LOAD_HEIGHT, true, true);
            return image.isError() ? Optional.empty() : Optional.of(image);
        } catch (IOException e){
            return Optional.empty();
        }
    }

    private static ImageView imageView(Image image, double height){
        ImageView view = new ImageView(image);
        view.setFitHeight(height);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        return view;
    }

    private static Node placeHolder(Entity entity, double height){
        Rectangle background = new Rectangle(height * 0.75, height);
        background.setArcHeight(16);
        background.setArcWidth(16);
        background.setFill(Color.web("#e0e0e0"));

        Label initial = new Label(entity.getName().substring(0,1));
        initial.setStyle("-fx-font-size: 48px; -fx-text-fill: #757575;");
        VBox content = new VBox(2, initial);
        content.setAlignment(Pos.CENTER);
        if(entity instanceof Winx winx){
            Label level = new Label("Liv. " + winx.getLevel());
            level.setStyle("-fx-text-fill: #757575;");
            content.getChildren().add(level);
        }
        return new StackPane(background, content);
    }
}
