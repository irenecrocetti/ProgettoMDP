package it.unicam.cs.mpgc.rpg130573.ui.component;

import it.unicam.cs.mpgc.rpg130573.domain.effect.Effect;
import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;
import it.unicam.cs.mpgc.rpg130573.ui.resource.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.VBox;

import java.util.stream.Collectors;

public class CombatantCard extends VBox {

    public enum Highlight { NONE, ACTIVE, TARGETABLE }

    private static final double WIDTH = 160;

    public CombatantCard(Entity entity, Node portrait, Highlight highlight){
        super(4);
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(8));
        setMinWidth(WIDTH);
        setPrefWidth(WIDTH);
        setMaxWidth(WIDTH);
        setStyle(borderStyle(highlight));
        if(highlight == Highlight.TARGETABLE) setCursor(Cursor.HAND);

        if(!entity.isAlive()) dim(portrait);

        Label name = new Label(entity.isAlive() ? entity.getName() : entity.getName() + " (KO)");
        name.setStyle("-fx-font-weight: bold;");

        getChildren().addAll(portrait, name, createHealth(entity));
        if(entity instanceof Winx winx){
            getChildren().add(createBar("Stamina " + winx.getStamina() + " / " + winx.getMaxStamina(),
                    winx.getStamina(), winx.getMaxStamina(), Theme.STAMINA));

        }
        getChildren().add(createEffects(entity));
    }

    private static String borderStyle(Highlight highlight){
        String color = switch (highlight) {
            case ACTIVE -> Theme.HIGHLIGHT_ACTIVE;
            case TARGETABLE -> Theme.HIGHLIGHT_TARGET;
            case NONE -> Theme.BORDER;
        };
        int width = highlight == Highlight.NONE ? 1 : 3;
        return "-fx-background-color: " + Theme.CARD_BACKGROUND + ";"
                + " -fx-border-color: " + color + "; -fx-border-width: " + width
                + "; -fx-border-radius: 8; -fx-background-radius: 8;";
    }

    private static void dim(Node portrait){
        ColorAdjust gray = new ColorAdjust();
        gray.setSaturation(-1);
        gray.setBrightness(-0.3);
        portrait.setEffect(gray);
        portrait.setOpacity(0.6);
    }

    private static Node createHealth(Entity entity){
        if (entity.isInvulnerable()) {
            Label label = new Label("Invulnerabile");
            label.setStyle("-fx-text-fill: " + Theme.SPECIAL + "; -fx-font-style: italic;");
            return label;
        }
        return createBar("HP " + entity.getHp() + " / " + entity.getMaxHP(),
                entity.getHp(), entity.getMaxHP(), Theme.HP);
    }

    private static Node createBar(String text, int value, int max, String color){
        ProgressBar bar = new ProgressBar(max == 0 ? 0 : (double) value / max);
        bar.setPrefWidth(WIDTH - 16);
        bar.setStyle("-fx-accent: " + color + ";");
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 11px;");
        return new VBox(1, label, bar);
    }

    private static Node createEffects(Entity entity){
        String text = entity.getEffectManager().getActiveEffects().stream()
                .map(CombatantCard::describe)
                .collect(Collectors.joining(", "));
        Label label = new Label(text);
        label.setWrapText(true);
        label.setMinHeight(32);
        label.setMaxWidth(WIDTH - 16);
        label.setStyle("-fx-font-size: 11px; -fx-text-fill: " + Theme.TEXT_MUTED + ";");
        return label;
    }

    private static String describe(Effect effect){
        return effect.getName() + " (" + effect.getRemainingTurns() + ")";
    }
}
