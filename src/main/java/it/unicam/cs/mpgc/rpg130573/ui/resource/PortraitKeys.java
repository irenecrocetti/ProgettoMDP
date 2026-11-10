package it.unicam.cs.mpgc.rpg130573.ui.resource;

import it.unicam.cs.mpgc.rpg130573.domain.model.Entity;
import it.unicam.cs.mpgc.rpg130573.domain.model.managers.WinxStatusManager;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Decide quali file immagine cercare per un personaggio, in ordine di preferenza.
 * Per una Winx si parte dalla forma del suo livello e si scende fino a quella
 * base, così le evoluzioni mancanti non lasciano un buco.
 * Non dipende da JavaFX, quindi è testabile.
 */
final class PortraitKeys {
    private PortraitKeys() {}

    /**
     * @return i nomi dei file (senza estensione) da provare, dal più specifico al più generico
     */
    static List<String> candidates(Entity entity){
        String base = basekey(entity);
        List<String> keys = new ArrayList<>();
        if(entity instanceof Winx winx){
            for(int level = winx.getLevel(); level >= WinxStatusManager.MIN_LEVEL; level--){
                keys.add(base + "_" + level);
            }
        }
        keys.add(base);
        return keys;
    }

    private static String basekey(Entity entity){
        return entity.getName().toLowerCase(Locale.ROOT).replace(' ', '_');
    }
}
