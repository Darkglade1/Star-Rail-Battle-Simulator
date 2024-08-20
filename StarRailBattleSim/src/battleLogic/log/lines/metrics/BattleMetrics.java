package battleLogic.log.lines.metrics;

import battleLogic.AbstractEntity;
import battleLogic.IBattle;
import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class BattleMetrics implements Loggable {

    private final int totalPlayerDmg;
    private final float actionValueUsed;
    private final float finalDPAV;
    private final float totalSkillPointsUsed;
    private final float totalSkillPointsGenerated;
    private final Map<AbstractEntity, Float> finalActionValue;
    private final Map<AbstractCharacter, Float> leftOverEnergy;
    private final Map<AbstractCharacter, Float> totalDamageDealt;

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
        this.totalDamageDealt = new HashMap<>(battle.getDamageContributionMap());
    }

    @Override
    public String asString() {
        String out = String.format("Total player team damage: %d \nAction Value used: %.1f\n", totalPlayerDmg, actionValueUsed);
        out += String.format("Final DPAV: %.1f\nSkill Points Used: %.1f\nSkill Points Generated: %.1f\n", finalDPAV, totalSkillPointsUsed, totalSkillPointsGenerated);
        out += "Leftover AV: " + finalActionValue + "\n";
        out += "Leftover Energy: " + leftOverEnergy
                .entrySet()
                .stream()
                .map(e -> String.format("| %s: %.2f | ", e.getKey().name, e.getValue()))
                .collect(Collectors.joining())
                + "\n";
        return out;
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
