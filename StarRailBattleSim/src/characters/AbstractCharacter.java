package characters;

import battleLogic.AbstractEntity;
import battleLogic.Battle;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractCharacter extends AbstractEntity {

    public enum ElementType {
        FIRE, ICE, WIND, LIGHTNING, PHYSICAL, QUANTUM, IMAGINARY
    }

    public enum DamageType {
        SKILL, BASIC, ULTIMATE, FOLLOW_UP, DOT, BREAK
    }

    public enum MoveType {
        SKILL, BASIC, ENHANCED_BASIC, ULTIMATE, FOLLOW_UP
    }

    protected int baseHP;
    protected int baseAtk;
    protected int baseDef;
    public int level;
    public float baseCritChance = 5.0f;
    public float baseCritDamage = 50.0f;
    public float maxEnergy;
    public float currentEnergy;
    public float ultCost;
    public int tauntValue;
    public ElementType elementType;
    public AbstractLightcone lightcone;
    public ArrayList<AbstractRelicSetBonus> relicSetBonus;
    public boolean useTechnique = true;
    protected int basicEnergyGain = 20;
    protected int skillEnergyGain = 30;
    protected int ultEnergyGain = 5;
    public boolean isDPS = false;

    public int numTurnsMetric;
    public int numSkillsMetric;
    public int numBasicsMetric;
    public int numUltsMetric;
    public String statsString;
    public String numTurnsMetricName = "Turns taken";
    public String numSkillsMetricName = "Skills Used";
    public String numBasicsMetricName = "Basic Attacks Used";
    public String numUltsMetricName = "Ultimates Used";
    protected boolean firstMove = true;
    public boolean hasAttackingUltimate;
    protected ArrayList<MoveType> moveHistory;
    public HashMap<String, String> statsMap = new HashMap<>();
    public ArrayList<String> statsOrder = new ArrayList<>();
    protected float TOUGHNESS_DAMAGE_HALF_UNIT = 5;
    protected float TOUGHNESS_DAMAGE_SINGLE_UNIT = 10;
    protected float TOUGHNESS_DAMAGE_TWO_UNITS = 20;
    protected float TOUGHNESS_DAMAGE_THREE_UNITs = 30;

    public AbstractCharacter(String name, int baseHP, int baseAtk, int baseDef, int baseSpeed, int level, ElementType elementType, float maxEnergy, int tauntValue) {
        this.name = name;
        this.baseHP = baseHP;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;
        this.baseSpeed = baseSpeed;
        this.level = level;
        this.elementType = elementType;
        this.maxEnergy = maxEnergy;
        this.ultCost = maxEnergy;
        this.currentEnergy = maxEnergy / 2;
        this.tauntValue = tauntValue;
        powerList = new ArrayList<>();
        relicSetBonus = new ArrayList<>();
        moveHistory = new ArrayList<>();
    }

    public void useSkill() {
        moveHistory.add(MoveType.SKILL);
        numSkillsMetric++;
        Battle.battle.addToLog(name + " used Skill");
        Battle.battle.useSkillPoint(this, 1);
        increaseEnergy(skillEnergyGain);
        boolean hasSparkle = false;
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            if (character instanceof Sparkle) {
                hasSparkle = true;
                break;
            }
        }
        if (hasSparkle) {
            for (AbstractCharacter character : Battle.battle.playerTeam) {
                character.addPower(new Sparkle.SparkleTalentPower());
            }
        }
    }
    public void useBasicAttack() {
        moveHistory.add(MoveType.BASIC);
        numBasicsMetric++;
        Battle.battle.addToLog(name + " used Basic");
        Battle.battle.generateSkillPoint(this, 1);
        increaseEnergy(basicEnergyGain);
    }
    public void useUltimate() {
        moveHistory.add(MoveType.ULTIMATE);
        numUltsMetric++;
        float initialEnergy = currentEnergy;
        currentEnergy -= ultCost;
        Battle.battle.addToLog(String.format("%s used Ultimate (%.3f -> %.3f)", name, initialEnergy, currentEnergy));
        increaseEnergy(ultEnergyGain);
        this.lightcone.onUseUltimate();
        ArrayList<AbstractPower> powersToTrigger = new ArrayList<>(powerList); // jank way to dodge comod exception lol
        for (AbstractPower power : powersToTrigger) {
            power.onUseUltimate();
        }
    }

    public void onAttacked(AbstractEnemy enemy, int energyFromAttacked) {
        increaseEnergy(energyFromAttacked);
    }

    public float getFinalAttack() {
        int totalBaseAtk = baseAtk + lightcone.baseAtk;
        float totalBonusAtkPercent = 0;
        int totalBonusFlatAtk = 0;
        for (AbstractPower power : powerList) {
            totalBonusAtkPercent += power.bonusAtkPercent;
            totalBonusAtkPercent += power.getConditionalAtkBonus(this);
            totalBonusFlatAtk += power.bonusFlatAtk;
        }
        return (totalBaseAtk * (1 + totalBonusAtkPercent / 100) + totalBonusFlatAtk);
    }

    public float getFinalDefense() {
        int totalBaseDef = baseDef + lightcone.baseDef;
        float totalBonusDefPercent = 0;
        int totalBonusFlatDef = 0;
        for (AbstractPower power : powerList) {
            totalBonusDefPercent += power.bonusDefPercent;
            totalBonusFlatDef += power.bonusFlatDef;
        }
        return (totalBaseDef * (1 + totalBonusDefPercent / 100) + totalBonusFlatDef);
    }

    public float getFinalHP() {
        int totalBaseHP = baseHP + lightcone.baseHP;
        float totalBonusHPPercent = 0;
        int totalBonusFlatHP = 0;
        for (AbstractPower power : powerList) {
            totalBonusHPPercent += power.bonusHPPercent;
            totalBonusFlatHP += power.bonusFlatHP;
        }
        return (totalBaseHP * (1 + totalBonusHPPercent / 100) + totalBonusFlatHP);
    }

    public float getFinalSpeed() {
        int totalBaseSpeed = baseSpeed;
        float totalBonusSpeedPercent = 0;
        float totalBonusFlatSpeed = 0;
        for (AbstractPower power : powerList) {
            totalBonusSpeedPercent += power.bonusSpeedPercent;
            totalBonusFlatSpeed += power.bonusFlatSpeed;
        }
        return (totalBaseSpeed * (1 + totalBonusSpeedPercent / 100) + totalBonusFlatSpeed);
    }

    public float getTotalCritChance() {
        float totalCritChance = baseCritChance;
        for (AbstractPower power : powerList) {
            totalCritChance += power.bonusCritChance;
        }
        if (totalCritChance > 100) {
            totalCritChance = 100;
        }
        return totalCritChance;
    }

    public float getTotalCritDamage() {
        float totalCritDamage = baseCritDamage;
        for (AbstractPower power : powerList) {
            totalCritDamage += power.bonusCritDamage;
        }
        return totalCritDamage;
    }

    public float getTotalSameElementDamageBonus() {
        float totalSameElementDamageBonus = 0;
        float totalGlobalElementDamageBonus = 0;
        for (AbstractPower power : powerList) {
            totalSameElementDamageBonus += power.bonusSameElementDamageBonus;
            totalGlobalElementDamageBonus += power.bonusDamageBonus;
        }
        return totalSameElementDamageBonus + totalGlobalElementDamageBonus;
    }

    public float getTotalOffElementDamageBonus() {
        float totalGlobalElementDamageBonus = 0;
        for (AbstractPower power : powerList) {
            totalGlobalElementDamageBonus += power.bonusDamageBonus;
        }
        return totalGlobalElementDamageBonus;
    }

    public float getTotalResPen() {
        int totalResPen = 0;
        for (AbstractPower power : powerList) {
            totalResPen += power.resPen;
        }
        return totalResPen;
    }

    public float getFinalTauntValue() {
        int baseTauntValue = tauntValue;
        float totalBonusTauntValue = 0;
        for (AbstractPower power : powerList) {
            totalBonusTauntValue += power.bonusTauntValue;
        }
        return (baseTauntValue * (1 + totalBonusTauntValue / 100));
    }

    public float getTotalERR() {
        float totalEnergyRegenBonus = 0;
        for (AbstractPower power : powerList) {
            totalEnergyRegenBonus += power.bonusEnergyRegen;
        }
        return totalEnergyRegenBonus;
    }

    public float getTotalEHR() {
        float totalEHR = 0;
        for (AbstractPower power : powerList) {
            totalEHR += power.bonusEffectHit;
        }
        return totalEHR;
    }

    public float getTotalEffectRes() {
        float totalEffectRes = 0;
        for (AbstractPower power : powerList) {
            totalEffectRes += power.bonusEffectRes;
        }
        return totalEffectRes;
    }

    public float getTotalBreakEffect() {
        float totalBreakEffect = 0;
        for (AbstractPower power : powerList) {
            totalBreakEffect += power.bonusBreakEffect;
        }
        return totalBreakEffect;
    }

    public float getTotalWeaknessBreakEff() {
        float totalWeaknessBreakEff = 0;
        for (AbstractPower power : powerList) {
            totalWeaknessBreakEff += power.bonusWeaknessBreakEff;
        }
        return totalWeaknessBreakEff;
    }

    public void increaseEnergy(float amount, boolean ERRAffected) {
        float initialEnergy = currentEnergy;
        float totalEnergyRegenBonus = getTotalERR();
        float energyGained = amount;
        if (ERRAffected) {
            energyGained = amount * (1 + totalEnergyRegenBonus / 100);
        }
        currentEnergy += energyGained;
        if (currentEnergy > maxEnergy) {
            currentEnergy = maxEnergy;
        }
        Battle.battle.addToLog(String.format("%s gained %.3f Energy (%.3f -> %.3f)", name, energyGained, initialEnergy, currentEnergy));
    }

    public void increaseEnergy(float amount) {
        increaseEnergy(amount, true);
    }

    @Override
    public float getBaseAV() {
        float speed = getFinalSpeed();
        return 10000 / speed;
    }

    public void EquipLightcone(AbstractLightcone lightcone) {
        this.lightcone = lightcone;
        lightcone.onEquip();
    }

    public void EquipRelicSet(AbstractRelicSetBonus relicSetBonus) {
        this.relicSetBonus.add(relicSetBonus);
        relicSetBonus.onEquip();
    }

    public boolean lastMove(MoveType move) {
        for (int i = moveHistory.size() - 1; i > 0; i--) {
            MoveType previousMove = moveHistory.get(i);
            if (previousMove == MoveType.ULTIMATE) {
                continue;
            } else {
                return previousMove == move;
            }
        }
        return false;
    }

    public boolean lastMoveBefore(MoveType move) {
        boolean skippedYet = false;
        for (int i = moveHistory.size() - 1; i > 0; i--) {
            MoveType previousMove = moveHistory.get(i);
            if (previousMove == MoveType.ULTIMATE) {
                continue;
            } else {
                if (!skippedYet) {
                    skippedYet = true;
                } else {
                    return previousMove == move;
                }
            }
        }
        return false;
    }

    public void useTechnique() {

    }

    public void onCombatStart() {

    }

    @Override
    public void takeTurn() {
        numTurnsMetric++;
        speedPriority = 999; //reset speed priority if it was changed
    }

    public void onWeaknessBreak(AbstractEnemy enemy) {

    }

    public String getMetrics() {
        StringBuilder metrics = new StringBuilder(statsString + String.format("\nCombat Metrics \nTurns taken: %d \nBasics: %d \nSkills: %d \nUltimates: %d \nRotation: %s", numTurnsMetric, numBasicsMetric, numSkillsMetric, numUltsMetric, moveHistory));
        HashMap<String, String> metricsMap = getCharacterSpecificMetricMap();
        for (String metric : getOrderedCharacterSpecificMetricsKeys()) {
            metrics.append("\n").append(metric).append(": ").append(metricsMap.get(metric));
        }
        return metrics.toString();
    }

    public void generateStatsString() {
        String gearString = String.format("Metrics for %s \nLightcone: %s \nRelic Set Bonuses: ", name, lightcone);
        gearString += relicSetBonus;
        statsString = gearString + String.format("\nOut of combat stats \nAtk: %.3f \nDef: %.3f \nHP: %.3f \nSpeed: %.3f \nSame Element Damage Bonus: %.3f \nCrit Chance: %.3f%% \nCrit Damage: %.3f%% \nBreak Effect: %.3f%%", getFinalAttack(), getFinalDefense(), getFinalHP(), getFinalSpeed(), getTotalSameElementDamageBonus(), getTotalCritChance(), getTotalCritDamage(), getTotalBreakEffect());
    }

    public void generateStatsReport() {
        String lightcone = "Lightcone: ";
        String relicSets = "Relic Set Bonuses: ";
        String hp = "HP: ";
        String atk = "ATK: ";
        String def = "DEF: ";
        String spd = "SPD: ";
        String cr = "CRIT RATE: ";
        String cd = "CRIT DMG: ";
        String ehr = "Effect Hit Rate: ";
        String effectRes = "Effect RES: ";
        String breakEffect = "Break Effect: ";
        String element = "ELEMENT DMG: ";
        String err = "ERR: ";

        statsOrder.add(lightcone);
        statsOrder.add(relicSets);
        statsOrder.add(hp);
        statsOrder.add(atk);
        statsOrder.add(def);
        statsOrder.add(spd);
        statsOrder.add(cr);
        statsOrder.add(cd);
        statsOrder.add(ehr);
        statsOrder.add(effectRes);
        statsOrder.add(breakEffect);
        statsOrder.add(element);
        statsOrder.add(err);

        statsMap.put(lightcone, this.lightcone.toString());
        StringBuilder relicSetBonus = new StringBuilder("|");
        for (AbstractRelicSetBonus relic : this.relicSetBonus) {
            relicSetBonus.append(relic.toString()).append("|");
        }
        statsMap.put(relicSets, relicSetBonus.toString());
        statsMap.put(hp, String.valueOf(getFinalHP()));
        statsMap.put(atk, String.valueOf(getFinalAttack()));
        statsMap.put(def, String.valueOf(getFinalDefense()));
        statsMap.put(spd, String.valueOf(getFinalSpeed()));
        statsMap.put(cr, getTotalCritChance() + "%");
        statsMap.put(cd, getTotalCritDamage() + "%");
        statsMap.put(ehr, getTotalEHR() + "%");
        statsMap.put(effectRes, getTotalEffectRes() + "%");
        statsMap.put(breakEffect, getTotalBreakEffect() + "%");
        statsMap.put(element, getTotalSameElementDamageBonus() + "%");
        statsMap.put(err, getTotalERR() + "%");
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put(numTurnsMetricName, String.valueOf(numTurnsMetric));
        map.put(numSkillsMetricName, String.valueOf(numSkillsMetric));
        map.put(numBasicsMetricName, String.valueOf(numBasicsMetric));
        map.put(numUltsMetricName, String.valueOf(numUltsMetric));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = new ArrayList<>();
        list.add(numTurnsMetricName);
        list.add(numSkillsMetricName);
        list.add(numBasicsMetricName);
        list.add(numUltsMetricName);
        return list;
    }
}
