package battleLogic;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import relicSetBonus.AbstractRelicSetBonus;

import java.util.ArrayList;

public class BattleHelpers {
    private static float attackDamageTotal = 0;
    public static ArrayList<AbstractEnemy> enemiesHit = new ArrayList<>();
    public static float calculateDamageAgainstEnemy(AbstractCharacter source, AbstractEnemy target, float baseDamage, ArrayList<AbstractCharacter.DamageType> types, AbstractCharacter.ElementType damageElement) {
        float dmgMultiplier;
        if (damageElement == source.elementType) {
            dmgMultiplier = source.getTotalSameElementDamageBonus();
        } else {
            dmgMultiplier = source.getTotalOffElementDamageBonus();
        }
        for (AbstractPower power : source.powerList) {
            dmgMultiplier += power.getConditionalDamageBonus(source, target, types);
        }
        float dmgMultiplierFloat = 1 + dmgMultiplier / 100;

        float enemyDefPercent = target.getFinalDefense();
        float defMultiplierFloat = (source.level + 20) / ((target.level + 20) * (1 + enemyDefPercent / 100) + (source.level + 20));

        float resPen = source.getTotalResPen();
        float resMultiplier = 100 - (target.getRes(damageElement) - resPen);
        float resMultiplierFloat = resMultiplier / 100;

        float damageTaken = 0;
        for (AbstractPower power : target.powerList) {
            damageTaken += power.bonusDamageTaken;
            damageTaken += power.getConditionalDamageTaken(source, target, types);
        }
        float damageTakenMultiplier = 1 + damageTaken / 100;

        float toughnessMultiplier = 0.9f;
        if (target.weaknessBroken) {
            toughnessMultiplier = 1.0f;
        }

        float critChance = source.getTotalCritChance();
        double roll = Battle.battle.critChanceRng.nextDouble() * 100 + 1;
        float critMultiplierFloat = 1.0f;

        boolean wasCrit = false;
        if (roll < (double)critChance) {
            wasCrit = true;
            float critDamage = source.getTotalCritDamage();
            for (AbstractPower power : source.powerList) {
                critDamage += power.getConditionalCritDamage(source, target, types);
            }
            float critMultiplier = 100.0f + critDamage;
            critMultiplierFloat = critMultiplier / 100;
        }

        float calculatedDamage = baseDamage * dmgMultiplierFloat * defMultiplierFloat * resMultiplierFloat * toughnessMultiplier * critMultiplierFloat;
        if (wasCrit) {
            Battle.battle.addToLog(String.format("%s critically hit %s for %.3f damage - Base Damage: %.3f, Damage Multiplier: %.3f, Defense Multiplier: %.3f, Res Multiplier: %.3f, Damage Vuln Multiplier: %.3f, Toughness Multiplier: %.3f, Crit Damage Multiplier: %.3f",
                    source.name, target.name, calculatedDamage, baseDamage, dmgMultiplierFloat, defMultiplierFloat, resMultiplierFloat, damageTakenMultiplier, toughnessMultiplier, critMultiplierFloat));
        } else {
            Battle.battle.addToLog(String.format("%s hit %s for %.3f damage - Base Damage: %.3f, Damage Multiplier: %.3f, Defense Multiplier: %.3f, Res Multiplier: %.3f, Damage Vuln Multiplier: %.3f, Toughness Multiplier: %.3f",
                    source.name, target.name, calculatedDamage, baseDamage, dmgMultiplierFloat, defMultiplierFloat, resMultiplierFloat, damageTakenMultiplier, toughnessMultiplier));
        }
        return calculatedDamage;
    }

    public static void hitEnemy(AbstractCharacter source, AbstractEnemy target, float baseDamage, ArrayList<AbstractCharacter.DamageType> types, float toughnessDamage, AbstractCharacter.ElementType damageElement) {
        float calculatedDamage = calculateDamageAgainstEnemy(source, target, baseDamage, types, damageElement);
        if (target.weaknessMap.contains(damageElement)) {
            target.reduceToughness(toughnessDamage);
        }
        Battle.battle.totalPlayerDamage += calculatedDamage;
        Battle.battle.updateContribution(source, calculatedDamage);
        attackDamageTotal += calculatedDamage;
        if (!enemiesHit.contains(target)) {
            enemiesHit.add(target);
        }
    }

    public static void hitEnemy(AbstractCharacter source, AbstractEnemy target, float baseDamage, ArrayList<AbstractCharacter.DamageType> types, float toughnessDamage) {
        hitEnemy(source, target, baseDamage, types, toughnessDamage, source.elementType);
    }

    public static void PreAttackLogic(AbstractCharacter character, ArrayList<AbstractCharacter.DamageType> types) {
        attackDamageTotal = 0;
        enemiesHit.clear();
        for (AbstractPower power : character.powerList) {
            power.onBeforeUseAttack(types);
        }
        for (AbstractRelicSetBonus relicSet : character.relicSetBonus) {
            relicSet.onBeforeUseAttack(types);
        }
    }

    public static void PostAttackLogic(AbstractCharacter character, ArrayList<AbstractCharacter.DamageType> types) {
        character.lightcone.onAttack(character, enemiesHit, types);
        for (AbstractPower power : character.powerList) {
            power.onAttack(character, enemiesHit, types);
        }
        for (AbstractEnemy enemy : enemiesHit) {
            for (AbstractPower power : enemy.powerList) {
                power.onAttacked(character, enemy, types);
            }
        }

        int damageTotal = (int) attackDamageTotal;
        Battle.battle.addToLog(String.format("Total Damage: %d", damageTotal));
    }

    public static void attackCharacter(AbstractEnemy source, AbstractCharacter target, int energyToGain) {
        Battle.battle.addToLog(source.name + " attacked " + target.name);
        target.onAttacked(source, energyToGain);
    }

    public static float calculateDamageAgainstEnemyFixedCrit(AbstractCharacter source, AbstractEnemy target, float baseDamage, float fixedCritChance, float fixedCritDamage) {
        float dmgMultiplier;
        dmgMultiplier = source.getTotalSameElementDamageBonus();
        float dmgMultiplierFloat = 1 + dmgMultiplier / 100;

        float enemyDefPercent = target.getFinalDefense();
        float defMultiplierFloat = (source.level + 20) / ((target.level + 20) * (1 + enemyDefPercent / 100) + (source.level + 20));

        float resPen = source.getTotalResPen();
        float resMultiplier = 100 - (target.getRes(source.elementType) - resPen);
        float resMultiplierFloat = resMultiplier / 100;

        float damageTaken = 0;
        for (AbstractPower power : target.powerList) {
            damageTaken += power.bonusDamageTaken;
        }
        float damageTakenMultiplier = 1 + damageTaken / 100;

        float toughnessMultiplier = 0.9f;
        if (target.weaknessBroken) {
            toughnessMultiplier = 1.0f;
        }

        double roll = Battle.battle.critChanceRng.nextDouble() * 100 + 1;
        float critMultiplierFloat = 1.0f;

        boolean wasCrit = false;
        if (roll < (double) fixedCritChance) {
            wasCrit = true;
            float critMultiplier = 100.0f + fixedCritDamage;
            critMultiplierFloat = critMultiplier / 100;
        }

        float calculatedDamage = baseDamage * dmgMultiplierFloat * defMultiplierFloat * resMultiplierFloat * toughnessMultiplier * critMultiplierFloat;
        if (wasCrit) {
            Battle.battle.addToLog(String.format("%s critically hit %s for %.3f damage - Base Damage: %.3f, Damage Multiplier: %.3f, Defense Multiplier: %.3f, Res Multiplier: %.3f, Damage Vuln Multiplier: %.3f, Toughness Multiplier: %.3f, Crit Damage Multiplier: %.3f",
                    source.name, target.name, calculatedDamage, baseDamage, dmgMultiplierFloat, defMultiplierFloat, resMultiplierFloat, damageTakenMultiplier, toughnessMultiplier, critMultiplierFloat));
        } else {
            Battle.battle.addToLog(String.format("%s hit %s for %.3f damage - Base Damage: %.3f, Damage Multiplier: %.3f, Defense Multiplier: %.3f, Res Multiplier: %.3f, Damage Vuln Multiplier: %.3f, Toughness Multiplier: %.3f",
                    source.name, target.name, calculatedDamage, baseDamage, dmgMultiplierFloat, defMultiplierFloat, resMultiplierFloat, damageTakenMultiplier, toughnessMultiplier));
        }
        return calculatedDamage;
    }

    public static void robinHitEnemy(AbstractCharacter source, AbstractEnemy target, float baseDamage, float fixedCritChance, float fixedCritDamage) {
        float calculatedDamage = calculateDamageAgainstEnemyFixedCrit(source, target, baseDamage, fixedCritChance, fixedCritDamage);
        Battle.battle.totalPlayerDamage += calculatedDamage;
        Battle.battle.updateContribution(source, calculatedDamage);
        attackDamageTotal += calculatedDamage;
    }

    public static void tingyunSkillHitEnemy(AbstractCharacter source, AbstractEnemy target, float baseDamage) {
        float calculatedDamage = calculateDamageAgainstEnemy(source, target, baseDamage, new ArrayList<>(),  AbstractCharacter.ElementType.LIGHTNING);
        Battle.battle.totalPlayerDamage += calculatedDamage;
        Battle.battle.updateContribution(source, calculatedDamage);
        attackDamageTotal += calculatedDamage;
    }


}
