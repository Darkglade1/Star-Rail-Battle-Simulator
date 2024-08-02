package powers;

import battleLogic.AbstractEntity;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;

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
}
