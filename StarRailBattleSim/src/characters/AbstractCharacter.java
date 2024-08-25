package characters;

import battleLogic.AbstractEntity;
import battleLogic.BattleEvents;
import battleLogic.log.lines.character.GainEnergy;
import battleLogic.log.lines.character.DoMove;
import battleLogic.log.lines.character.TurnDecision;
import battleLogic.log.lines.character.UltDecision;
import characters.goal.TurnGoal;
import characters.goal.UltGoal;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import lightcones.DefaultLightcone;
import powers.AbstractPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class AbstractCharacter<C extends AbstractCharacter<C>>  extends AbstractEntity {

    public enum ElementType {
        FIRE, ICE, WIND, LIGHTNING, PHYSICAL, QUANTUM, IMAGINARY
    }

    public enum DamageType {
        SKILL, BASIC, ULTIMATE, FOLLOW_UP, DOT, BREAK, SUPER_BREAK
    }

    public enum MoveType {
        SKILL, BASIC, ENHANCED_BASIC, ULTIMATE, FOLLOW_UP
    }

    protected final Path path;

    protected int baseHP;
    protected int baseAtk;
    protected int baseDef;
    public int level;
    public float baseCritChance = 5.0f;
    public float baseCritDamage = 50.0f;

    public boolean usesEnergy = true;
    public float maxEnergy;
    public float currentEnergy;
    public float ultCost;
    protected int basicEnergyGain = 20;
    protected int skillEnergyGain = 30;
    protected int ultEnergyGain = 5;

    public int tauntValue;
    public ElementType elementType;
    public AbstractLightcone lightcone;
    public ArrayList<AbstractRelicSetBonus> relicSetBonus;
    public boolean useTechnique = true;

    public boolean isDPS = false;

    public int numSkillsMetric;
    public int numBasicsMetric;
    public int numUltsMetric;
    public String statsString;
    public String numTurnsMetricName = "Turns taken";
    public String numSkillsMetricName = "Skills Used";
    public String numBasicsMetricName = "Basic Attacks Used";
    public String numUltsMetricName = "Ultimates Used";
    public String leftoverAVMetricName = "Leftover AV";
    public String leftoverEnergyMetricName = "Leftover Energy";
    public boolean firstMove = true;
    public boolean hasAttackingUltimate;
    public ArrayList<MoveType> moveHistory;
    public HashMap<String, String> statsMap = new HashMap<>();
    public ArrayList<String> statsOrder = new ArrayList<>();
    protected float TOUGHNESS_DAMAGE_HALF_UNIT = 5;
    protected float TOUGHNESS_DAMAGE_SINGLE_UNIT = 10;
    protected float TOUGHNESS_DAMAGE_TWO_UNITS = 20;
    protected float TOUGHNESS_DAMAGE_THREE_UNITs = 30;

    public static final String ULT_ENERGY_GAIN = "from using Ultimate";
    public static final String SKILL_ENERGY_GAIN = "from using Skill";
    public static final String BASIC_ENERGY_GAIN = "from using Basic";
    public static final String EBA_ENERGY_GAIN = "from using Enhanced Basic";
    public static final String FUA_ENERGY_GAIN = "from using Follow up attack";
    public static final String TALENT_ENERGY_GAIN = "from Talent effect";
    public static final String TRACE_ENERGY_GAIN = "from Trace effect";
    public static final String LIGHTCONE_ENERGY_GAIN = "from Lightcone effect";
    public static final String ATTACKED_ENERGY_GAIN = "from being attacked";
    public static final String TECHNIQUE_ENERGY_GAIN = "from Technique effect";

    private final SortedMap<Integer, UltGoal<C>> ultGoals = new TreeMap<>();
    private final SortedMap<Integer, TurnGoal<C>> turnGoals = new TreeMap<>();

    public AbstractCharacter(String name, int baseHP, int baseAtk, int baseDef, int baseSpeed, int level, ElementType elementType, float maxEnergy, int tauntValue, Path path) {
        super();
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
        this.path = path;

        powerList = new ArrayList<>();
        relicSetBonus = new ArrayList<>();
        moveHistory = new ArrayList<>();

        this.lightcone = new DefaultLightcone(this);
    }

    public void registerGoal(int priority, UltGoal<C> ultGoal) {
        if (ultGoals.containsKey(priority)) {
            throw new IllegalArgumentException("Priority already exists, " + priority);
        }
        ultGoals.put(priority, ultGoal);
    }

    public void clearUltGoals() {
        ultGoals.clear();
    }

    public void registerGoal(int priority, TurnGoal<C> turnGoal) {
        if (turnGoals.containsKey(priority)) {
            throw new IllegalArgumentException("Priority already exists, " + priority);
        }
        turnGoals.put(priority, turnGoal);
    }

    public void clearTurnGoals() {
        turnGoals.clear();
    }

    public final void tryUltimate() {
        if (currentEnergy < ultCost) {
            return;
        }

        for (UltGoal<C> ultGoal : this.ultGoals.values()) {
            UltGoal.UltGoalResult result = ultGoal.determineAction();
            switch (result) {
                case DO: {
                    getBattle().addToLog(new UltDecision(this, ultGoal.getClass()));
                    this.ultimateSequence();
                    return;
                }
                case DONT: {
                    return;
                }
                case PASS: {
                    continue;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + result);
                }
            }
        }
    }

    @Override
    public final void takeTurn() {
        super.takeTurn();
        numTurnsMetric++;

        // TODO: Ulting at the start of the turn makes the dmg higher in some places, but lowers in a few
        // Should investigate why this is happening, and add the needed goals to make it increase at all times
        // Time to read a bunch of logs
        //tryUltimate();
        for (TurnGoal<C> turnGoal : this.turnGoals.values()) {
            TurnGoal.TurnGoalResult result = turnGoal.determineAction();
            switch (result) {
                case BASIC: {
                    getBattle().addToLog(new TurnDecision(this, turnGoal.getClass(), result));
                    this.basicSequence();
                    return;
                }
                case SKILL: {
                    getBattle().addToLog(new TurnDecision(this, turnGoal.getClass(), result));
                    this.skillSequence();
                    return;
                }
                case PASS: {
                    continue;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + result);
                }
            }
        }

        throw new IllegalStateException("No valid turn goal found for: " + this.name);
    }

    protected void skillSequence() {
        moveHistory.add(MoveType.SKILL);
        numSkillsMetric++;
        getBattle().addToLog(new DoMove(this, MoveType.SKILL));
        getBattle().useSkillPoint(this, 1);
        increaseEnergy(skillEnergyGain, SKILL_ENERGY_GAIN);
        this.emit(BattleEvents::onUseSkill);
        this.useSkill();
        this.emit(BattleEvents::afterUseSkill);
    }

    protected void basicSequence() {
        moveHistory.add(MoveType.BASIC);
        numBasicsMetric++;
        getBattle().addToLog(new DoMove(this, MoveType.BASIC));
        getBattle().generateSkillPoint(this, 1);
        increaseEnergy(basicEnergyGain, BASIC_ENERGY_GAIN);
        this.emit(BattleEvents::onUseBasic);
        this.useBasic();
        this.emit(BattleEvents::afterUseBasic);
    }

    protected void ultimateSequence() {
        moveHistory.add(MoveType.ULTIMATE);
        numUltsMetric++;
        float initialEnergy = currentEnergy;
        currentEnergy -= ultCost;
        getBattle().addToLog(new DoMove(this, MoveType.ULTIMATE, initialEnergy, currentEnergy));
        increaseEnergy(ultEnergyGain, ULT_ENERGY_GAIN);
        this.emit(BattleEvents::onUseUltimate);
        this.useUltimate();
        this.emit(BattleEvents::afterUseUltimate);
    }

    protected abstract void useSkill();
    protected abstract void useBasic();
    protected abstract void useUltimate();

    public void onAttacked(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyFromAttacked) {
        increaseEnergy(energyFromAttacked, ATTACKED_ENERGY_GAIN);
    }

    public Path getPath() {
        return path;
    }

    public float getFinalAttack() {
        int totalBaseAtk = baseAtk + lightcone.baseAtk;
        float totalBonusAtkPercent = 0;
        int totalBonusFlatAtk = 0;
        for (AbstractPower power : powerList) {
            totalBonusAtkPercent += power.getStat(PowerStat.ATK_PERCENT);
            totalBonusAtkPercent += power.getConditionalAtkBonus(this);
            totalBonusFlatAtk += power.getStat(PowerStat.FLAT_ATK);
        }
        return (totalBaseAtk * (1 + totalBonusAtkPercent / 100) + totalBonusFlatAtk);
    }

    public float getFinalDefense() {
        int totalBaseDef = baseDef + lightcone.baseDef;
        float totalBonusDefPercent = 0;
        int totalBonusFlatDef = 0;
        for (AbstractPower power : powerList) {
            totalBonusDefPercent += power.getStat(PowerStat.DEF_PERCENT);
            totalBonusDefPercent += power.getConditionalDefenseBonus(this);
            totalBonusFlatDef += power.getStat(PowerStat.FLAT_DEF);
        }
        return (totalBaseDef * (1 + totalBonusDefPercent / 100) + totalBonusFlatDef);
    }

    public float getFinalHP() {
        int totalBaseHP = baseHP + lightcone.baseHP;
        float totalBonusHPPercent = 0;
        int totalBonusFlatHP = 0;
        for (AbstractPower power : powerList) {
            totalBonusHPPercent += power.getStat(PowerStat.HP_PERCENT);
            totalBonusFlatHP += power.getStat(PowerStat.FLAT_HP);
        }
        return (totalBaseHP * (1 + totalBonusHPPercent / 100) + totalBonusFlatHP);
    }

    public float getFinalSpeed() {
        int totalBaseSpeed = baseSpeed;
        float totalBonusSpeedPercent = 0;
        float totalBonusFlatSpeed = 0;
        for (AbstractPower power : powerList) {
            totalBonusSpeedPercent += power.getStat(PowerStat.SPEED_PERCENT);
            totalBonusFlatSpeed += power.getStat(PowerStat.FLAT_SPEED);
        }
        return (totalBaseSpeed * (1 + totalBonusSpeedPercent / 100) + totalBonusFlatSpeed);
    }

    public float getTotalCritChance() {
        float totalCritChance = baseCritChance;
        for (AbstractPower power : powerList) {
            totalCritChance += power.getStat(PowerStat.CRIT_CHANCE);
        }
        if (totalCritChance > 100) {
            totalCritChance = 100;
        }
        return totalCritChance;
    }

    public float getTotalCritDamage() {
        float totalCritDamage = baseCritDamage;
        for (AbstractPower power : powerList) {
            totalCritDamage += power.getStat(PowerStat.CRIT_DAMAGE);
        }
        return totalCritDamage;
    }

    public float getTotalSameElementDamageBonus() {
        float totalSameElementDamageBonus = 0;
        float totalGlobalElementDamageBonus = 0;
        for (AbstractPower power : powerList) {
            totalSameElementDamageBonus += power.getStat(PowerStat.SAME_ELEMENT_DAMAGE_BONUS);
            totalGlobalElementDamageBonus += power.getStat(PowerStat.DAMAGE_BONUS);
        }
        return totalSameElementDamageBonus + totalGlobalElementDamageBonus;
    }

    public float getTotalOffElementDamageBonus() {
        float totalGlobalElementDamageBonus = 0;
        for (AbstractPower power : powerList) {
            totalGlobalElementDamageBonus += power.getStat(PowerStat.DAMAGE_BONUS);
        }
        return totalGlobalElementDamageBonus;
    }

    public float getTotalResPen() {
        int totalResPen = 0;
        for (AbstractPower power : powerList) {
            totalResPen += power.getStat(PowerStat.RES_PEN);
        }
        return totalResPen;
    }

    public float getFinalTauntValue() {
        int baseTauntValue = tauntValue;
        float totalBonusTauntValue = 0;
        for (AbstractPower power : powerList) {
            totalBonusTauntValue += power.getStat(PowerStat.TAUNT_VALUE);
        }
        return (baseTauntValue * (1 + totalBonusTauntValue / 100));
    }

    public float getTotalERR() {
        float totalEnergyRegenBonus = 0;
        for (AbstractPower power : powerList) {
            totalEnergyRegenBonus += power.getStat(PowerStat.ENERGY_REGEN);
            totalEnergyRegenBonus += power.getConditionalERR(this);
        }
        return totalEnergyRegenBonus;
    }

    public float getTotalEHR() {
        float totalEHR = 0;
        for (AbstractPower power : powerList) {
            totalEHR += power.getStat(PowerStat.EFFECT_HIT);
        }
        return totalEHR;
    }

    public float getTotalEffectRes() {
        float totalEffectRes = 0;
        for (AbstractPower power : powerList) {
            totalEffectRes += power.getStat(PowerStat.EFFECT_RES);
        }
        return totalEffectRes;
    }

    public float getTotalBreakEffect() {
        float totalBreakEffect = 0;
        for (AbstractPower power : powerList) {
            totalBreakEffect += power.getStat(PowerStat.BREAK_EFFECT);
            totalBreakEffect += power.getConditionalBreakEffectBonus(this);
        }
        return totalBreakEffect;
    }

    public float getTotalWeaknessBreakEff() {
        float totalWeaknessBreakEff = 0;
        for (AbstractPower power : powerList) {
            totalWeaknessBreakEff += power.getStat(PowerStat.WEAKNESS_BREAK_EFF);
        }
        return totalWeaknessBreakEff;
    }

    public void increaseEnergy(float amount, boolean ERRAffected, String source) {
        if (!this.usesEnergy) return;
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
        getBattle().addToLog(new GainEnergy(this, initialEnergy,this.currentEnergy, energyGained, source));
    }

    public void increaseEnergy(float amount, String source) {
        increaseEnergy(amount, true, source);
    }

    @Override
    public float getBaseAV() {
        float speed = getFinalSpeed();
        return 10000 / speed;
    }

    public void EquipLightcone(AbstractLightcone lightcone) {
        this.getListeners().remove(this.lightcone);
        this.lightcone = lightcone;
        this.lightcone.onEquip();
        this.getListeners().add(this.lightcone);
    }

    public void EquipRelicSet(AbstractRelicSetBonus relicSetBonus) {
        this.relicSetBonus.add(relicSetBonus);
        relicSetBonus.onEquip();
        this.getListeners().add(relicSetBonus);
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
        map = addLeftoverCharacterAVMetric(map);
        map = addLeftoverCharacterEnergyMetric(map);
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = new ArrayList<>();
        list.add(numTurnsMetricName);
        list.add(numSkillsMetricName);
        list.add(numBasicsMetricName);
        list.add(numUltsMetricName);
        list.add(leftoverAVMetricName);
        list.add(leftoverEnergyMetricName);
        return list;
    }

    public HashMap<String, String> addLeftoverCharacterAVMetric(HashMap<String, String> metricMap) {
        metricMap.put(leftoverAVMetricName, String.format("%.2f", getBattle().getActionValueMap().get(this)));
        return metricMap;
    }

    public HashMap<String, String> addLeftoverCharacterEnergyMetric(HashMap<String, String> metricMap) {
        metricMap.put(leftoverEnergyMetricName, String.format("%.2f", this.currentEnergy));
        return metricMap;
    }
}
