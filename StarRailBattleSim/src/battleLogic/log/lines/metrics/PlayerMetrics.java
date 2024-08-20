package battleLogic.log.lines.metrics;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

import java.util.HashMap;
import java.util.Map;

public class PlayerMetrics implements Loggable {

    private final AbstractCharacter player;
    private final Map<String, Object> metrics = new HashMap<>();

    public PlayerMetrics(AbstractCharacter player) {
        this.player = player;

        Map<StatType, Float> outOfCombatStats = new HashMap<>();
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

        Map<String, Boolean> relicSets = new HashMap<>();
        player.relicSetBonus.forEach(r -> relicSets.put(r.toString(), r.isFullSet()));

        Map<String, Object> combatMetrics = new HashMap<>();
        combatMetrics.put("turnsTaken", player.numTurnsMetric);
        combatMetrics.put("basics", player.numBasicsMetric);
        combatMetrics.put("skills", player.numSkillsMetric);
        combatMetrics.put("ultimates", player.numUltsMetric);
        combatMetrics.put("rotation", player.moveHistory);

        this.metrics.put("outOFCombatStats", outOfCombatStats);
        this.metrics.put("relicSets", relicSets);
        this.metrics.put("combatMetrics", combatMetrics);
    }

    @Override
    public String asString() {
        String statsString = "";
        String gearString = String.format("Metrics for %s \nLightcone: %s \nRelic Set Bonuses: ", player.name, player.lightcone);
        gearString += player.relicSetBonus;
        statsString = gearString + String.format("\nOut of combat stats \nAtk: %.3f \nDef: %.3f \nHP: %.3f \nSpeed: %.3f \nSame Element Damage Bonus: %.3f \nCrit Chance: %.3f%% \nCrit Damage: %.3f%% \nBreak Effect: %.3f%%", player.getFinalAttack(), player.getFinalDefense(), player.getFinalHP(), player.getFinalSpeed(), player.getTotalSameElementDamageBonus(), player.getTotalCritChance(), player.getTotalCritDamage(), player.getTotalBreakEffect());

        StringBuilder metrics = new StringBuilder(statsString + String.format("\nCombat Metrics \nTurns taken: %d \nBasics: %d \nSkills: %d \nUltimates: %d \nRotation: %s", player.numTurnsMetric, player.numBasicsMetric, player.numSkillsMetric, player.numUltsMetric, player.moveHistory));
        HashMap<String, String> metricsMap = player.getCharacterSpecificMetricMap();
        for (String metric : player.getOrderedCharacterSpecificMetricsKeys()) {
            metrics.append("\n").append(metric).append(": ").append(metricsMap.get(metric));
        }
        return metrics.toString();
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }

    public static class RelicSet {
        private final String name;
        private final boolean fullSet;

        public RelicSet(String name, boolean fullSet) {
            this.name = name;
            this.fullSet = fullSet;
        }
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
