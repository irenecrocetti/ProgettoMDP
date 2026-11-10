package it.unicam.cs.mpgc.rpg130573.domain.level;

import java.util.List;
import java.util.Optional;

/**
 * Elenco ordinato dei livelli di gioco. Unico punto da modificare
 * per aggiungere un livello.
 */
public class LevelRegistry {
    private final List<Level> levels;

    public LevelRegistry(List<Level> levels){
        this.levels = List.copyOf(levels);
    }

    /**
     * Crea il registro dei livelli del gioco di base.
     */
    public static LevelRegistry createDefault(){
        return new LevelRegistry(List.of(
                new TrixLevel(), new DarkarLevel(), new ValtorLevel()
        ));
    }

    public List<Level> getLevels() {
        return this.levels;
    }

    public int size(){
        return levels.size();
    }

    public Optional<Level> getLevel(int id){
        return levels.stream().filter(l -> l.getId() == id).findFirst();
    }

    /**
     * @return il livello successivo a quello indicato, vuoto se era l'ultimo.
     */
    public Optional<Level> getNext(int id){
        return getLevel(id + 1);
    }
}
