package it.unicam.cs.mpgc.rpg130573.application.session;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.spells.WinxSpell;
import it.unicam.cs.mpgc.rpg130573.domain.level.LevelRegistry;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.Winx;
import it.unicam.cs.mpgc.rpg130573.domain.model.winx.WinxFactory;
import it.unicam.cs.mpgc.rpg130573.domain.shop.SpellShop;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Assembla le partite dai contenuti del gioco (Winx, shop, livelli). È l'unico punto
 * che sa come si costruisce una {@link GameSession}, sia nuova sia ripristinata da
 * un salvataggio.
 * Gli incantesimi del catalogo sono condivisi da tutte le partite create da questa
 * factory, quindi devono essere privi di stato.
 */
public class GameSessionFactory {
    private final WinxFactory winxFactory;
    private final List<CharacterAllocation> party;
    private final SpellShop shop;
    private final LevelRegistry registry;

    /**
     * @param party le Winx con cui inizia una nuova partita, nell'ordine di turno
     */
    public GameSessionFactory(WinxFactory winxFactory, List<CharacterAllocation> party,
                              SpellShop shop, LevelRegistry registry){
        this.winxFactory = Objects.requireNonNull(winxFactory);
        this.party = List.copyOf(party);
        this.shop = Objects.requireNonNull(shop);
        this.registry = Objects.requireNonNull(registry);
        if (this.party.isEmpty())
            throw new IllegalArgumentException("Il gruppo iniziale non può essere vuoto");
    }

    /** Contenuti del gioco di base. */
    public static GameSessionFactory createDefault(){
        return new GameSessionFactory(
                WinxFactory.createDefault(),
                List.of(CharacterAllocation.BLOOM, CharacterAllocation.STELLA,
                        CharacterAllocation.TECNA),
                SpellShop.createDefault(),
                LevelRegistry.createDefault());
    }

    /** Nuova partita: Winx livello 1, nessun livello superato. */
    public GameSession newGame() {
        return restoreGame(party.stream().map(winxFactory::create).toList(), 0);
    }

    /**
     * Partita con Winx e progresso già noti (usata dal caricamento).
     *
     * @throws  IllegalArgumentException se il progresso non è valido
     */
    public GameSession restoreGame(List<Winx> winxes, int highestClearedLevel){
        return new GameSession(winxes, shop, registry, highestClearedLevel);
    }

    /** @throws IllegalArgumentException se la Winx non è registrata */
    public Winx createWinx(CharacterAllocation id){
        return winxFactory.create(id);
    }

    public Optional<WinxSpell> findSpell(String id){
        return shop.findById(id);
    }
}
