package it.unicam.cs.mpgc.rpg130573.Shop;

/**
 * Esito di un tentativo di acquisto nello shop
 */
public enum PurchaseResult {
    SUCCESS,
    NOT_IN_SHOP,
    WRONG_CHARACTER,
    ALREADY_OWNED,
    LEVEL_TOO_LOW,
    NOT_ENOUGH_XP
}
