package relics;

import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;
import java.util.HashMap;

public class RelicStats {

    public enum Stats {
        HP_FLAT, ATK_FLAT, DEF_FLAT, HP_PER, ATK_PER, DEF_PER, CRIT_RATE, CRIT_DAMAGE, EFFECT_HIT, EFFECT_RES, BREAK_EFFECT, SPEED, HEALING, ERR, ELEMENT_DAMAGE
    }

    private final HashMap<Stats, Float> mainStatValues = new HashMap<>();
    private final HashMap<Stats, Float> subStatValues = new HashMap<>();

    private ArrayList<Stats> usedMainStats = new ArrayList<>();
    private HashMap<Stats, Integer> usedSubStats = new HashMap<>();

    public RelicStats() {
        initializeValues();
        this.addMainStat(Stats.HP_FLAT).addMainStat(Stats.ATK_FLAT);
    }

    public void equipTo(AbstractCharacter character) {
        PermPower relicBonus = new PermPower();

        relicBonus.setStat(PowerStat.FLAT_HP, getTotalBonus(Stats.HP_FLAT));
        relicBonus.setStat(PowerStat.FLAT_ATK, getTotalBonus(Stats.ATK_FLAT));
        relicBonus.setStat(PowerStat.FLAT_DEF, getTotalBonus(Stats.DEF_FLAT));
        relicBonus.setStat(PowerStat.HP_PERCENT, getTotalBonus(Stats.HP_PER));
        relicBonus.setStat(PowerStat.ATK_PERCENT, getTotalBonus(Stats.ATK_PER));
        relicBonus.setStat(PowerStat.DEF_PERCENT, getTotalBonus(Stats.DEF_PER));
        relicBonus.setStat(PowerStat.CRIT_CHANCE, getTotalBonus(Stats.CRIT_RATE));
        relicBonus.setStat(PowerStat.CRIT_DAMAGE, getTotalBonus(Stats.CRIT_DAMAGE));
        relicBonus.setStat(PowerStat.EFFECT_HIT, getTotalBonus(Stats.EFFECT_HIT));
        relicBonus.setStat(PowerStat.EFFECT_RES, getTotalBonus(Stats.EFFECT_RES));
        relicBonus.setStat(PowerStat.BREAK_EFFECT, getTotalBonus(Stats.BREAK_EFFECT));
        relicBonus.setStat(PowerStat.FLAT_SPEED, getTotalBonus(Stats.SPEED));
        relicBonus.setStat(PowerStat.HEALING, getTotalBonus(Stats.HEALING));
        relicBonus.setStat(PowerStat.ENERGY_REGEN, getTotalBonus(Stats.ERR));
        relicBonus.setStat(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, getTotalBonus(Stats.ELEMENT_DAMAGE));
        relicBonus.name = "Relic Stats Bonuses";
        character.addPower(relicBonus);
    }

    public RelicStats addMainStat(Stats stat) {
        usedMainStats.add(stat);
        return this;
    }

    public RelicStats addSubStat(Stats stat, int numRolls) {
        usedSubStats.put(stat, numRolls);
        return this;
    }

    private float getTotalBonus(Stats stat) {
        float total = 0;
        for (Stats mainStat : usedMainStats) {
            if (mainStat == stat) {
                total += mainStatValues.get(stat);
            }
        }
        if (usedSubStats.containsKey(stat)) {
            total += subStatValues.get(stat) * usedSubStats.get(stat);
        }
        return total;
    }

    private void initializeValues() {
        mainStatValues.put(Stats.HP_FLAT, 705.0f);
        mainStatValues.put(Stats.ATK_FLAT, 352.0f);
        mainStatValues.put(Stats.HP_PER, 43.2f);
        mainStatValues.put(Stats.ATK_PER, 43.2f);
        mainStatValues.put(Stats.DEF_PER, 54.0f);
        mainStatValues.put(Stats.CRIT_RATE, 32.4f);
        mainStatValues.put(Stats.CRIT_DAMAGE, 64.8f);
        mainStatValues.put(Stats.EFFECT_HIT, 43.2f);
        mainStatValues.put(Stats.BREAK_EFFECT, 64.8f);
        mainStatValues.put(Stats.SPEED, 25.0f);
        mainStatValues.put(Stats.HEALING, 34.5f);
        mainStatValues.put(Stats.ERR, 19.4f);
        mainStatValues.put(Stats.ELEMENT_DAMAGE, 38.8f);

        subStatValues.put(Stats.HP_FLAT, 38.0f);
        subStatValues.put(Stats.ATK_FLAT, 19.0f);
        subStatValues.put(Stats.DEF_FLAT, 19.0f);
        subStatValues.put(Stats.HP_PER, 3.8f);
        subStatValues.put(Stats.ATK_PER, 3.8f);
        subStatValues.put(Stats.DEF_PER, 4.8f);
        subStatValues.put(Stats.CRIT_RATE, 2.9f);
        subStatValues.put(Stats.CRIT_DAMAGE, 5.8f);
        subStatValues.put(Stats.EFFECT_HIT, 3.8f);
        subStatValues.put(Stats.EFFECT_RES, 3.8f);
        subStatValues.put(Stats.BREAK_EFFECT, 5.8f);
        subStatValues.put(Stats.SPEED, 2.3f);
    }

}
