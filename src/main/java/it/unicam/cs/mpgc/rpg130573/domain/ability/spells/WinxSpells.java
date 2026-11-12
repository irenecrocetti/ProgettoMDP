package it.unicam.cs.mpgc.rpg130573.domain.ability.spells;

import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.SpellType;
import it.unicam.cs.mpgc.rpg130573.domain.ability.enums.TargetType;

import static it.unicam.cs.mpgc.rpg130573.domain.ability.enums.CharacterAllocation.*;
import static it.unicam.cs.mpgc.rpg130573.domain.ability.spells.SpellBeahavior.*;
import static it.unicam.cs.mpgc.rpg130573.domain.effect.StatusEffects.*;

/**
 * Catalogo degli incantesimi delle Winx. Per aggiungerne uno basta una nuova costante
 * (e, se si compra nello shop, la riga in SpellShop.createDefault()).
 * Gli id compaiono nei salvataggi: non vanno mai cambiati.
 */
public final class WinxSpells {
    // Le tre fasce di costo oggi condivise da tutti gli incantesimi
    private static final SpellCost TIER_1 = new SpellCost(20,0,1);
    private static final SpellCost TIER_2 = new SpellCost(30,100,2);
    private static final SpellCost TIER_3 = new SpellCost(40,200,3);

    // Bloom
    public static final WinxSpell FLAME_SHIELD = selfSpell("FlameShield", "Flame Shield", BLOOM,
            SpellType.SHIELD, TIER_1, buffSelf(() -> shield(3, 75, 2)));
    public static final WinxSpell FIRE_BALL = attack("FireBall", "Fire Ball", BLOOM, TIER_1,
            damage(25).andThen(inflict(() -> burn(3, 5))));
    public static final WinxSpell HEAT_FLARE = attack("HeatFlare", "Heat Flare", BLOOM, TIER_2,
            damage(40).andThen(inflict(() -> burn(3, 6))));
    public static final WinxSpell DRAGON_FURY = attack("DragonFury", "Dragon Fury", BLOOM, TIER_3,
            damage(45).andThen(inflict(() -> burn(3, 7))));

    // Stella
    public static final WinxSpell RISING_SUN = attack("RisingSun", "Rising Sun", STELLA, TIER_1,
            damage(15).andThen(inflict(() -> blindness(2, 25))));
    public static final WinxSpell SUN_SHOWER = selfSpell("SunShower", "Sun Shower", STELLA,
            SpellType.SELFBUFF, TIER_1, healSelf(20));
    public static final WinxSpell SUN_BOLT = attack("SunBolt", "Sun Bolt", STELLA, TIER_2,
            damage(28).andThen(inflict(() -> blindness(2, 30))));
    public static final WinxSpell LIGHT_OCEAN = attack("LightOcean", "Light Ocean", STELLA, TIER_3,
            damage(30).andThen(inflict(() -> blindness(3, 35))));

    // Tecna
    public static final WinxSpell ELECTRO_RAY = attack("ElectroRay", "Electro Ray", TECNA, TIER_1,
            damage(20).andThen(inflict(() -> electrify(3, 4))));
    public static final WinxSpell FIREWALL = selfSpell("Firewall", "Firewall", TECNA,
            SpellType.SHIELD, TIER_1, buffSelf(() -> shield(3, 60, 3)));
    public static final WinxSpell GLITCH = attack("Glitch", "Glitch", TECNA, TIER_2,
            damage(32).andThen(inflict(() -> electrify(3, 4))));
    public static final WinxSpell ENERGY_SPHERE = attack("EnergySphere", "Energy Sphere", TECNA, TIER_3,
            damage(40).andThen(inflict(() -> electrify(3, 5))));


    private static WinxSpell attack(String id, String name, CharacterAllocation owner,
                                    SpellCost cost, SpellBeahavior beahavior) {
        return new StandardWinxSpell(
                new SpellProfile(id, name, owner, TargetType.ENEMY, SpellType.OFFENSE), cost, beahavior);
    }

    private static WinxSpell selfSpell(String id, String name, CharacterAllocation owner,
                                       SpellType type, SpellCost cost, SpellBeahavior beahavior){
        return new StandardWinxSpell(
                new SpellProfile(id, name, owner, TargetType.SELF, type), cost, beahavior);
    }
}
