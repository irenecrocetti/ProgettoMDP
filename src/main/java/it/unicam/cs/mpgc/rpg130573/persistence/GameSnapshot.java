package it.unicam.cs.mpgc.rpg130573.persistence;

import java.util.List;

/** Dati persistenti di una partita nei momenti in cui è salvabile (nessuna battaglia in corso) */
public record GameSnapshot(int version,
                           int highestClearedLevel,
                           List<WinxSnapshot> winxes) {
    public static final int CURRENT_VERSION = 1;
}
