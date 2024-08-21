package battleLogic.log.lines.metrics;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostCombatPlayerMetrics implements Loggable {

    private final AbstractCharacter player;
    private final Map<StatType, Float> finalStats = new HashMap<>();
    private final Map<String, Boolean> relicSets = new HashMap<>();
    private final int turnsTaken;
    private final int basics;
    private final int skills;
    private final int ultimates;
    private final List<AbstractCharacter.MoveType> rotation;
    private final Map<String, String> characterSpecificMetrics;

    public PostCombatPlayerMetrics(AbstractCharacter player) {
        this.player = player;

        finalStats.put(StatType.ATK, player.getFinalAttack());
        finalStats.put(StatType.DEF, player.getFinalDefense());
        finalStats.put(StatType.HP, player.getFinalHP());
        finalStats.put(StatType.SPD, player.getFinalSpeed());
        finalStats.put(StatType.DMG, player.getTotalSameElementDamageBonus());
        finalStats.put(StatType.CRIT, player.getTotalCritChance());
        finalStats.put(StatType.CRITDMG, player.getTotalCritDamage());
        finalStats.put(StatType.BREAK, player.getTotalBreakEffect());
        finalStats.put(StatType.EHR, player.getTotalEHR());
        finalStats.put(StatType.ERR, player.getTotalERR());
        finalStats.put(StatType.RES, player.getTotalEffectRes());
        player.relicSetBonus.forEach(r -> relicSets.put(r.toString(), r.isFullSet()));

        this.turnsTaken = player.numTurnsMetric;
        this.basics = player.numBasicsMetric;
        this.skills = player.numSkillsMetric;
        this.ultimates = player.numUltsMetric;
        this.rotation = player.moveHistory;
        this.characterSpecificMetrics = new HashMap<>(player.getCharacterSpecificMetricMap());
    }

    @Override
    public String asString() {
        String statsString = "";
        String gearString = String.format("Metrics for %s \nLightcone: %s \nRelic Set Bonuses: ", player.name, player.lightcone);
        gearString += player.relicSetBonus;
        statsString = gearString + String.format("\nAfter combat stats \nAtk: %.3f \nDef: %.3f \nHP: %.3f \nSpeed: %.3f \nSame Element Damage Bonus: %.3f \nCrit Chance: %.3f%% \nCrit Damage: %.3f%% \nBreak Effect: %.3f%%",
                finalStats.get(StatType.ATK), finalStats.get(StatType.DEF), finalStats.get(StatType.HP), finalStats.get(StatType.SPD), finalStats.get(StatType.DMG), finalStats.get(StatType.CRIT), finalStats.get(StatType.CRITDMG), finalStats.get(StatType.BREAK));

        StringBuilder metrics = new StringBuilder(statsString + String.format("\nCombat Metrics \nTurns taken: %d \nBasics: %d \nSkills: %d \nUltimates: %d \nRotation: %s",
               turnsTaken, basics, skills, ultimates, rotation));
        for (Map.Entry<String, String> entry : this.characterSpecificMetrics.entrySet()) {
            metrics.append("\n").append(entry.getKey()).append(": ").append(entry.getValue());
        }
        return metrics.toString();
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
