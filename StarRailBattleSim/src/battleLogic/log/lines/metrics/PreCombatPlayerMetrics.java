package battleLogic.log.lines.metrics;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

import java.util.HashMap;
import java.util.Map;

public class PreCombatPlayerMetrics implements Loggable {

    private final AbstractCharacter player;
    private final Map<StatType, Float> outOfCombatStats = new HashMap<>();
    private final Map<String, Boolean> relicSets = new HashMap<>();

    public PreCombatPlayerMetrics(AbstractCharacter player) {
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
    }

    @Override
    public String asString() {
        String statsString = "";
        String gearString = String.format("Metrics for %s \nLightcone: %s \nRelic Set Bonuses: ", player.name, player.lightcone);
        gearString += player.relicSetBonus;
        statsString = gearString + String.format("\nOut of combat stats \nAtk: %.3f \nDef: %.3f \nHP: %.3f \nSpeed: %.3f \nSame Element Damage Bonus: %.3f \nCrit Chance: %.3f%% \nCrit Damage: %.3f%% \nBreak Effect: %.3f%%",
                outOfCombatStats.get(StatType.ATK), outOfCombatStats.get(StatType.DEF), outOfCombatStats.get(StatType.HP), outOfCombatStats.get(StatType.SPD), outOfCombatStats.get(StatType.DMG), outOfCombatStats.get(StatType.CRIT), outOfCombatStats.get(StatType.CRITDMG), outOfCombatStats.get(StatType.BREAK));
        return statsString;
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
