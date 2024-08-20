package battleLogic.log.lines.metrics;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerMetrics implements Loggable {

    private final AbstractCharacter player;
    private final Map<StatType, Float> outOfCombatStats = new HashMap<>();
    private final Map<String, Boolean> relicSets = new HashMap<>();
    private final int turnsTaken;
    private final int basics;
    private final int skills;
    private final int ultimates;
    private final List<AbstractCharacter.MoveType> rotation;
    private final Map<String, String> characterSpecificMetrics = new HashMap<>();

    public PlayerMetrics(AbstractCharacter player) {
        this.player = player;

        outOfCombatStats.put(StatType.ATK, player.getFinalAttack());
        outOfCombatStats.put(StatType.DEF, player.getFinalDefense());
        outOfCombatStats.put(StatType.HP, player.getFinalHP());
        outOfCombatStats.put(StatType.SPD, player.getFinalSpeed());
        outOfCombatStats.put(StatType.DMG, player.getTotalSameElementDamageBonus());
        outOfCombatStats.put(StatType.CRIT, player.getTotalCritChance());
        outOfCombatStats.put(StatType.CRITDMG, player.getTotalCritDamage());
        outOfCombatStats.put(StatType.BREAK, player.getTotalBreakEffect());
        outOfCombatStats.put(StatType.EHR, player.getTotalEHR());
        outOfCombatStats.put(StatType.ERR, player.getTotalERR());
        outOfCombatStats.put(StatType.RES, player.getTotalEffectRes());
        player.relicSetBonus.forEach(r -> relicSets.put(r.toString(), r.isFullSet()));

        this.turnsTaken = player.numTurnsMetric;
        this.basics = player.numBasicsMetric;
        this.skills = player.numSkillsMetric;
        this.ultimates = player.numUltsMetric;
        this.rotation = player.moveHistory;
        this.characterSpecificMetrics.putAll(player.getCharacterSpecificMetricMap());
    }

    @Override
    public String asString() {
        String statsString = "";
        String gearString = String.format("Metrics for %s \nLightcone: %s \nRelic Set Bonuses: ", player.name, player.lightcone);
        gearString += player.relicSetBonus;
        statsString = gearString + String.format("\nOut of combat stats \nAtk: %.3f \nDef: %.3f \nHP: %.3f \nSpeed: %.3f \nSame Element Damage Bonus: %.3f \nCrit Chance: %.3f%% \nCrit Damage: %.3f%% \nBreak Effect: %.3f%%",
                outOfCombatStats.get(StatType.ATK), outOfCombatStats.get(StatType.DEF), outOfCombatStats.get(StatType.HP), outOfCombatStats.get(StatType.SPD), outOfCombatStats.get(StatType.DMG), outOfCombatStats.get(StatType.CRIT), outOfCombatStats.get(StatType.CRITDMG), outOfCombatStats.get(StatType.BREAK));

        StringBuilder metrics = new StringBuilder(statsString + String.format("\nCombat Metrics \nTurns taken: %d \nBasics: %d \nSkills: %d \nUltimates: %d \nRotation: %s",
               turnsTaken, basics, skills, ultimates, rotation));
        HashMap<String, String> metricsMap = player.getCharacterSpecificMetricMap();
        for (Map.Entry<String, String> entry : this.characterSpecificMetrics.entrySet()) {
            metrics.append("\n").append(entry.getKey()).append(": ").append(metricsMap.get(entry.getValue()));
        }
        return metrics.toString();
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }

    public enum StatType {
        HP,
        DEF,
        ATK,
        SPD,
        CRIT,
        CRITDMG,
        EHR,
        ERR,
        RES,
        BREAK,
        DMG,
    }
}
