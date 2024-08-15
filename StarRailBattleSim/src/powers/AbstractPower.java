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
