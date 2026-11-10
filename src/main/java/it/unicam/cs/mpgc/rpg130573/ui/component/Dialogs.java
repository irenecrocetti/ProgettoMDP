package it.unicam.cs.mpgc.rpg130573.ui.component;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Classe di utilità per le finestre di dialogo, evita la duplicazione del codice
 */
public final class Dialogs {
    private Dialogs() {}

    public static void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static boolean confirm(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        return alert.showAndWait().filter(b -> (b == ButtonType.YES)).isPresent();
    }
}
