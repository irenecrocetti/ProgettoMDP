package it.unicam.cs.mpgc.rpg130573.ui;

import it.unicam.cs.mpgc.rpg130573.abilities.enums.SpellResult;
import it.unicam.cs.mpgc.rpg130573.abilities.enums.TargetType;
import it.unicam.cs.mpgc.rpg130573.combat.ActionResult;
import it.unicam.cs.mpgc.rpg130573.combat.events.BattleEvent;
import it.unicam.cs.mpgc.rpg130573.game.TurnReport;

import java.util.ArrayList;
import java.util.List;

final class TurnReportFormatter {
    private TurnReportFormatter() {}

    static List<String> format(TurnReport report){
        List<String> lines = new ArrayList<>();

        if(report.action().isPresent()) {
            lines.add(describe(report.action().get()));
            if (!report.turnConsumed()) return lines;
        } else {
            lines.add(report.actor().getName() + " riposa e recupera stamina.");
        }

        report.enemyActions().forEach(a -> lines.add(describe(a)));
        report.events().stream().map(BattleEvent::getMessage).forEach(lines::add);
        return lines;
    }

    private static String describe(ActionResult action){
        String source = action.source().getName();
        String spell = action.spell().getName();
        if(action.result() == SpellResult.CANNOT_CAST)
            return source + " non può lanciare " + spell + ".";

        String base = action.spell().getTargetType() == TargetType.SELF
                ? source + " usa " + spell
                : source + " lancia " + spell + " su " + action.target().getName();
        return action.result() == SpellResult.MISS
                ? base + ", ma manca il bersaglio!"
                : base + ".";
    }
}
