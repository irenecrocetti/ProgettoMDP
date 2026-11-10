package it.unicam.cs.mpgc.rpg130573.ui.navigation;

/**
 * Cambia la schermata visualizzata. Le view dipendono da questa interfaccia
 * e non dalle altre view.
 */
public interface Navigator {
    void showMainMenu();
    void showLevelSelect();
    void showBattle(int levelId);
    void showShop();
}
