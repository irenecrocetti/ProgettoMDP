package it.unicam.cs.mpgc.rpg130573.ui.resource;

import java.util.Objects;

public final class Theme {
    private static final String STYLESHEET_PATH = "/styles/winx.css";

    public static final String HP = "#c2185b";
    public static final String STAMINA = "#f06292";

    public static final String HIGHLIGHT_ACTIVE = "#ff4081";
    public static final String HIGHLIGHT_TARGET = "#880e4f";
    public static final String BORDER = "#f48fb1";
    public static final String CARD_BACKGROUND = "#fff5f8";

    public static final String SUCCESS = "#ad1457";
    public static final String AVAILABLE = "#ec407a";
    public static final String DANGER = "#c51162";
    public static final String SPECIAL = "#d81b60";

    public static final String TEXT_MUTED = "#8e5a70";
    public static final String PLACEHOLDER_BACKGROUND = "#f8bbd0";
    public static final String PLACEHOLDER_TEXT = "#ad1457";

    private Theme() {}

    /** @return l'URL del foglio di stile globale, pronto per getStylesheets().add(...) */
    public static String stylesheet() {
        return Objects.requireNonNull(Theme.class.getResource(STYLESHEET_PATH),
                "Foglio di stile mancante: " + STYLESHEET_PATH).toExternalForm();
    }
}
