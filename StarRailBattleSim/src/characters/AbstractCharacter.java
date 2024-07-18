package characters;

import battleLogic.AbstractEntity;
import battleLogic.Battle;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import relicSetBonus.AbstractRelicSetBonus;

import java.util.ArrayList;

public abstract class AbstractCharacter extends AbstractEntity {

    public enum ElementType {
        FIRE, ICE, WIND, LIGHTNING, PHYSICAL, QUANTUM, IMAGINARY
    }

    public enum DamageType {
        SKILL, BASIC, ULTIMATE, FOLLOW_UP, DOT, BREAK
    }

    public enum MoveType {
        SKILL, BASIC, ULTIMATE
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
    protected boolean firstMove = true;
    protected ArrayList<MoveType> moveHistory;

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

    public void increaseEnergy(float amount, boolean ERRAffected) {
        float initialEnergy = currentEnergy;
        float totalEnergyRegenBonus = 0;
        for (AbstractPower power : powerList) {
            totalEnergyRegenBonus += power.bonusEnergyRegen;
        }
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
    public String getMetrics() {
        return statsString + String.format("\nCombat Metrics \nTurns taken: %d \nBasics: %d \nSkills: %d \nUltimates: %d \nRotation: %s", numTurnsMetric, numBasicsMetric, numSkillsMetric, numUltsMetric, moveHistory);
    }

    public void generateStatsString() {
        String gearString = String.format("Metrics for %s \nLightcone: %s \nRelic Set Bonuses: ", name, lightcone);
        gearString += relicSetBonus;
        statsString = gearString + String.format("\nOut of combat stats \nAtk: %.3f \nDef: %.3f \nHP: %.3f \nSpeed: %.3f \nSame Element Damage Bonus: %.3f \nCrit Chance: %.3f \nCrit Damage: %.3f", getFinalAttack(), getFinalDefense(), getFinalHP(), getFinalSpeed(), getTotalSameElementDamageBonus(), getTotalCritChance(), getTotalCritDamage());
    }
}
