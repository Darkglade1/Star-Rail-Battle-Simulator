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
    }

    public void useSkill() {
        Battle.battle.addToLog(name + " used Skill");
        Battle.battle.useSkillPoint(this, 1);
        increaseEnergy(30);
    }
    public void useBasicAttack() {
        Battle.battle.addToLog(name + " used Basic");
        Battle.battle.generateSkillPoint(this, 1);
        increaseEnergy(20);
    }
    public void useUltimate() {
        Battle.battle.addToLog(name + " used Ultimate");
        currentEnergy -= ultCost;
        increaseEnergy(5);
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
        int totalBonusFlatSpeed = 0;
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

    public void increaseEnergy(int amount, boolean ERRAffected) {
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

    public void increaseEnergy(int amount) {
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

    public void useTechnique() {

    }
}
