package battleLogic.log.lines.metrics;

import battleLogic.IBattle;
import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class FinalDmgMetrics implements Loggable {

    private final int totalPlayerDmg;
    private final float actionValueUsed;
    private final Map<AbstractCharacter, Float> totalDamageDealt;

    public FinalDmgMetrics(IBattle battle) {
        this.totalPlayerDmg = battle.getTotalPlayerDmg();
        this.actionValueUsed = battle.getActionValueUsed();
        this.totalDamageDealt = new HashMap<>(battle.getDamageContributionMap());
    }

    @Override
    public String asString() {
        StringBuilder log = new StringBuilder();
        totalDamageDealt.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> {
                    float percent = entry.getValue() / totalPlayerDmg * 100;
                    log.append(String.format("%s: %.3f DPAV (%.3f%%) | ", entry.getKey().name, entry.getValue() / actionValueUsed, percent));
                });

        return "Damage Contribution: | " + log + " | Total Damage: " + totalPlayerDmg;
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
