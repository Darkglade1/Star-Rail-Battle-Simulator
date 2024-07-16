package powers;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;

import java.util.ArrayList;

public abstract class AbstractPower {
    public String name;

    public int bonusFlatHP;
    public float bonusHPPercent;
    public int bonusFlatAtk;
    public float bonusAtkPercent;
    public int bonusFlatDef;
    public float bonusDefPercent;
    public int bonusFlatSpeed;
    public float bonusSpeedPercent;
    public float bonusSameElementDamageBonus;
    public float bonusDamageBonus;
    public float bonusDamageTaken;
    public float defenseReduction;
    public float defenseIgnore;
    public float resPen;
    public float bonusCritChance;
    public float bonusCritDamage;
    public float bonusEnergyRegen;
    public int turnDuration;
    public boolean durationBasedOnSelfTurns = true;
    public boolean lastsForever = false;
    public boolean stackable = false;
    public int maxStacks;
    public int stacks;

    public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    public float getConditionalDamageTaken(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    public void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types) {

    }
    public void onBeforeUseAttack(ArrayList<AbstractCharacter.DamageType> damageTypes) {

    }
    public void onEndTurn() {
        if (!lastsForever && durationBasedOnSelfTurns) {
            turnDuration--;
        }
    }
}
