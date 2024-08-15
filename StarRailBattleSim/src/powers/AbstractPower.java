package powers;

import battleLogic.AbstractEntity;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import relics.RelicStats;

import java.util.ArrayList;

public abstract class AbstractPower {

    public enum PowerType {
        BUFF, DEBUFF
    }

    public String name;

    public float bonusFlatHP;
    public float bonusHPPercent;
    public float bonusFlatAtk;
    public float bonusAtkPercent;
    public float bonusFlatDef;
    public float bonusDefPercent;
    public float bonusFlatSpeed;
    public float bonusSpeedPercent;
    public float bonusSameElementDamageBonus;
    public float bonusEffectHit;
    public float bonusEffectRes;
    public float bonusBreakEffect;
    public float bonusHealing;
    public float bonusDamageBonus;
    public float bonusDamageTaken;
    public float defenseReduction;
    public float defenseIgnore;
    public float resPen;
    public float bonusCritChance;
    public float bonusCritDamage;
    public float bonusEnergyRegen;
    public float bonusTauntValue;
    public float bonusWeaknessBreakEff;
    public int turnDuration;
    public PowerType type = PowerType.BUFF;
    public boolean durationBasedOnSelfTurns = true;
    public boolean lastsForever = false;
    public boolean justApplied = false;
    public int maxStacks = 0;
    public int stacks = 1;
    public AbstractEntity owner;

    public float getConditionalAtkBonus(AbstractCharacter character) {
        return 0;
    }

    public float getConditionalERR(AbstractCharacter character) {
        return 0;
    }

    public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    public float getConditionalDamageTaken(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    public float getConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }
    public float getConditionalCritRate(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }
    public float receiveConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    public float getConditionDefenseIgnore(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    public float setFixedCritRate(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes, float currentCrit) {
        return currentCrit;
    }

    public float setFixedCritDmg(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes, float currentCritDmg) {
        return currentCritDmg;
    }

    public void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types) {

    }

    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {

    }
    public void onBeforeUseAttack(ArrayList<AbstractCharacter.DamageType> damageTypes) {

    }

    public void onBeforeHitEnemy(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {

    }
    public void onEndTurn() {
        if (!lastsForever && durationBasedOnSelfTurns) {
            if (justApplied) {
                justApplied = false;
            } else {
                turnDuration--;
            }
        }
    }

    public void onTurnStart() {

    }

    public void onUseUltimate() {

    }

    public void onRemove() {

    }

    public float getStat(PowerStat stat) {
        switch (stat) {
            case FLAT_HP:
                return this.bonusFlatHP;
            case HP_PERCENT:
                return this.bonusHPPercent;
            case FLAT_ATK:
                return this.bonusFlatAtk;
            case ATK_PERCENT:
                return this.bonusAtkPercent;
            case FLAT_DEF:
                return this.bonusFlatDef;
            case DEF_PERCENT:
                return this.bonusDefPercent;
            case FLAT_SPEED:
                return this.bonusFlatSpeed;
            case SPEED_PERCENT:
                return this.bonusSpeedPercent;
            case SAME_ELEMENT_DAMAGE_BONUS:
                return this.bonusSameElementDamageBonus;
            case EFFECT_HIT:
                return this.bonusEffectHit;
            case EFFECT_RES:
                return this.bonusEffectRes;
            case BREAK_EFFECT:
                return this.bonusBreakEffect;
            case HEALING:
                return this.bonusHealing;
            case DAMAGE_BONUS:
                return this.bonusDamageBonus;
            case DAMAGE_TAKEN:
                return this.bonusDamageTaken;
            case DEFENSE_REDUCTION:
                return this.defenseReduction;
            case DEFENSE_IGNORE:
                return this.defenseIgnore;
            case RES_PEN:
                return this.resPen;
            case CRIT_CHANCE:
                return this.bonusCritChance;
            case CRIT_DAMAGE:
                return this.bonusCritDamage;
            case ENERGY_REGEN:
                return this.bonusEnergyRegen;
            case TAUNT_VALUE:
                return this.bonusTauntValue;
            case WEAKNESS_BREAK_EFF:
                return this.bonusWeaknessBreakEff;
            default:
                throw new IllegalArgumentException("Unknown stat: " + stat);
        }
    }

    public void setStat(PowerStat stat, float value) {
        switch (stat) {
            case FLAT_HP:
                this.bonusFlatHP = value;
                break;
            case HP_PERCENT:
                this.bonusHPPercent = value;
                break;
            case FLAT_ATK:
                this.bonusFlatAtk = value;
                break;
            case ATK_PERCENT:
                this.bonusAtkPercent = value;
                break;
            case FLAT_DEF:
                this.bonusFlatDef = value;
                break;
            case DEF_PERCENT:
                this.bonusDefPercent = value;
                break;
            case FLAT_SPEED:
                this.bonusFlatSpeed = value;
                break;
            case SPEED_PERCENT:
                this.bonusSpeedPercent = value;
                break;
            case SAME_ELEMENT_DAMAGE_BONUS:
                this.bonusSameElementDamageBonus = value;
                break;
            case EFFECT_HIT:
                this.bonusEffectHit = value;
                break;
            case EFFECT_RES:
                this.bonusEffectRes = value;
                break;
            case BREAK_EFFECT:
                this.bonusBreakEffect = value;
                break;
            case HEALING:
                this.bonusHealing = value;
                break;
            case DAMAGE_BONUS:
                this.bonusDamageBonus = value;
                break;
            case DAMAGE_TAKEN:
                this.bonusDamageTaken = value;
                break;
            case DEFENSE_REDUCTION:
                this.defenseReduction = value;
                break;
            case DEFENSE_IGNORE:
                this.defenseIgnore = value;
                break;
            case RES_PEN:
                this.resPen = value;
                break;
            case CRIT_CHANCE:
                this.bonusCritChance = value;
                break;
            case CRIT_DAMAGE:
                this.bonusCritDamage = value;
                break;
            case ENERGY_REGEN:
                this.bonusEnergyRegen = value;
                break;
            case TAUNT_VALUE:
                this.bonusTauntValue = value;
                break;
            case WEAKNESS_BREAK_EFF:
                this.bonusWeaknessBreakEff = value;
                break;
            default:
                throw new IllegalArgumentException("Unknown stat: " + stat);
        }
    }

}
