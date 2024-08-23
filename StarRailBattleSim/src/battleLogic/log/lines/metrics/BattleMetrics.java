package battleLogic.log.lines.metrics;

import battleLogic.AbstractEntity;
import battleLogic.IBattle;
import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class BattleMetrics implements Loggable {

    public final int totalPlayerDmg;
    public final float actionValueUsed;
    public final float finalDPAV;
    public final int totalSkillPointsUsed;
    public final int totalSkillPointsGenerated;
    public final Map<AbstractEntity, Float> finalActionValue;
    public final Map<AbstractCharacter, Float> leftOverEnergy;

    public BattleMetrics(IBattle battle) {
        this.totalPlayerDmg = battle.getTotalPlayerDmg();
        this.actionValueUsed = battle.getActionValueUsed();
        this.finalDPAV = battle.getFinalDPAV();
        this.totalSkillPointsUsed = battle.getTotalSkillPointsUsed();
        this.totalSkillPointsGenerated = battle.getTotalSkillPointsGenerated();
        this.finalActionValue = new HashMap<>(battle.getActionValueMap());
        this.leftOverEnergy = battle.getPlayers()
                .stream()
                .collect(HashMap::new,
                        (map, character) -> map.put(character, character.currentEnergy),
                        HashMap::putAll);
    }

    @Override
    public String asString() {
        String out = String.format("Total player team damage: %d \nAction Value used: %.1f\n", totalPlayerDmg, actionValueUsed);
        out += String.format("Final DPAV: %.3f\nSkill Points Used: %d\nSkill Points Generated: %d\n", finalDPAV, totalSkillPointsUsed, totalSkillPointsGenerated);
        out += "Leftover AV: " + finalActionValue
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(e -> String.format("%s: %.2f", e.getKey().name, e.getValue()))
                .collect(Collectors.joining(" | "))
                + "\n";
        out += "Leftover Energy: " + leftOverEnergy
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(e -> String.format("%s: %.2f", e.getKey().name, e.getValue()))
                .collect(Collectors.joining(" | "));
        return out;
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
