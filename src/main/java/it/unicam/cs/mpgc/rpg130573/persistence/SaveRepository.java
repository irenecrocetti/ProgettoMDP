package it.unicam.cs.mpgc.rpg130573.persistence;

import java.util.Optional;

/**
 * Astrazione del supporto su cui si salva. Non dice né dove né in che formato.
 */
public interface SaveRepository {
    void save(GameSnapshot snapshot);
    Optional<GameSnapshot> load();
    boolean exists();
    void delete();
}
