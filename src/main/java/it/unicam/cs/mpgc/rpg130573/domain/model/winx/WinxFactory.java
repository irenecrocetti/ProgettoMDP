package it.unicam.cs.mpgc.rpg130573.domain.model.winx;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class WinxFactory {
    private final Map<CharacterAllocation, Supplier<Winx>> creators =
            new EnumMap<>(CharacterAllocation.class);

    public static WinxFactory createDefault(){
        return new WinxFactory()
                .register(CharacterAllocation.BLOOM, Bloom::new)
                .register(CharacterAllocation.STELLA, Stella::new)
                .register(CharacterAllocation.TECNA, Tecna::new);
    }

    public WinxFactory register(CharacterAllocation id, Supplier<Winx> creator){
        creators.put(id, creator);
        return this;
    }

    public Winx create(CharacterAllocation id){
        Supplier<Winx> creator = creators.get(id);
        if(creator == null)
            throw new IllegalArgumentException("Winx non registrata: " + id);
        return creator.get();
    }
}
