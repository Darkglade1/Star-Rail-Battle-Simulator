package powers;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;

public abstract class AbstractPower {

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
    public float defenseReduction;
    public float defenseIgnore;
    public float resPen;
    public float bonusCritChance;
    public float bonusCritDamage;
    public float bonusEnergyRegen;
    public int turnDuration;
    public boolean durationBasedOnSelfTurns = true;
    public boolean lastsForever = false;

    public float applyConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, AbstractCharacter.DamageType type, float calculatedDamage) {
        return calculatedDamage;
    }

    public void onAttacked(AbstractCharacter character, AbstractEnemy enemy, AbstractCharacter.DamageType type, float calculatedDamage) {

    }
}
