package battleLogic;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;

public class BattleHelpers {
    public static int calculateDamageAgainstEnemy(AbstractCharacter source, AbstractEnemy target, float baseDamage, AbstractCharacter.DamageType type, boolean sameElement) {
        float dmgMultiplier;
        if (sameElement) {
            dmgMultiplier = source.getTotalSameElementDamageBonus();
        } else {
            dmgMultiplier = source.getTotalOffElementDamageBonus();
        }
        float dmgMultiplierFloat = 1 + dmgMultiplier / 100;

        float enemyDefPercent = target.getFinalDefense();
        float defMultiplierFloat = (source.level + 20) / ((target.level + 20) * (1 + enemyDefPercent / 100) + (source.level + 20));

        float resPen = source.getTotalResPen();
        float resMultiplier = 100 - (target.getRes(source.elementType) - resPen);
        float resMultiplierFloat = resMultiplier / 100;

        float toughnessMultiplier = 0.9f;
        if (target.weaknessBroken) {
            toughnessMultiplier = 1.0f;
        }

        float critChance = source.getTotalCritChance();
        double roll = Math.random() * 100 + 1;
        float critMultiplierFloat = 1.0f;

        boolean wasCrit = false;
        if (roll < (double)critChance) {
            wasCrit = true;
            float critDamage = source.getTotalCritDamage();
            float critMultiplier = 100.0f + critDamage;
            critMultiplierFloat = critMultiplier / 100;
        }
        System.out.println(baseDamage);
        System.out.println(dmgMultiplierFloat);
        System.out.println(defMultiplierFloat);
        System.out.println(resMultiplierFloat);
        System.out.println(toughnessMultiplier);
        System.out.println(critMultiplierFloat);
        float calculatedDamage = baseDamage * dmgMultiplierFloat * defMultiplierFloat * resMultiplierFloat * toughnessMultiplier * critMultiplierFloat;
        for (AbstractPower power : source.powerList) {
            calculatedDamage = power.applyConditionalDamageBonus(source, target, type, calculatedDamage);
        }
        for (AbstractPower power : target.powerList) {
            calculatedDamage = power.applyConditionalDamageBonus(source, target, type, calculatedDamage);
        }

        int result = (int)calculatedDamage;
        if (wasCrit) {
            System.out.println(source.name + " CRITICALLY hit " + target.name + " for " + result);
        } else {
            System.out.println(source.name + " hit " + target.name + " for " + result);
        }
        return result;
    }

    public static void attackEnemy(AbstractCharacter source, AbstractEnemy target, float baseDamage, AbstractCharacter.DamageType type, boolean sameElement) {
        int calculatedDamage = calculateDamageAgainstEnemy(source, target, baseDamage, type, sameElement);
        Battle.battle.totalPlayerDamage += calculatedDamage;
        Battle.battle.updateContribution(source, calculatedDamage);

        for (AbstractPower power : target.powerList) {
            power.onAttacked(source, target, type, calculatedDamage);
        }
    }

    public static void attackEnemy(AbstractCharacter source, AbstractEnemy target, float baseDamage, AbstractCharacter.DamageType type) {
        attackEnemy(source, target, baseDamage, type, true);
    }

    public static void attackCharacter(AbstractEnemy source, AbstractCharacter target, int energyToGain) {
        System.out.println(source.name + " attacked " + target.name);
        target.onAttacked(source, energyToGain);
    }
}
