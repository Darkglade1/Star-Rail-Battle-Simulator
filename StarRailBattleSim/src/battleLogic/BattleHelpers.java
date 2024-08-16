package battleLogic;

import characters.AbstractCharacter;
import characters.Moze;
import characters.SwordMarch;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

public class BattleHelpers {
    public enum MultiplierStat {
        ATK, HP, DEF
    }
    private static float attackDamageTotal = 0;
    public static ArrayList<AbstractEnemy> enemiesHit = new ArrayList<>();
    public static float calculateDamageAgainstEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier, MultiplierStat stat, ArrayList<AbstractCharacter.DamageType> types, AbstractCharacter.ElementType damageElement) {
        float statToUse;
        if (stat == MultiplierStat.ATK) {
            statToUse = source.getFinalAttack();
        } else if (stat == MultiplierStat.HP) {
            statToUse = source.getFinalHP();
        } else {
            statToUse = source.getFinalDefense();
        }
        float baseDamage = multiplier * statToUse;
        float dmgMultiplier;
        if (damageElement == source.elementType) {
            dmgMultiplier = source.getTotalSameElementDamageBonus();
        } else {
            dmgMultiplier = source.getTotalOffElementDamageBonus();
        }
        for (AbstractPower power : source.powerList) {
            dmgMultiplier += power.getConditionalDamageBonus(source, target, types);
        }
        for (AbstractPower power : target.powerList) {
            dmgMultiplier += power.receiveConditionalDamageBonus(source, target, types);
        }
        float dmgMultiplierFloat = 1 + dmgMultiplier / 100;

        float enemyDefPercent = target.getFinalDefense();
        for (AbstractPower power : source.powerList) {
            enemyDefPercent -= power.getConditionDefenseIgnore(source, target, types);
        }
        if (enemyDefPercent < -100) {
            enemyDefPercent = -100;
        }
        float defMultiplierFloat = (source.level + 20) / ((target.level + 20) * (1 + enemyDefPercent / 100) + (source.level + 20));

        float resPen = source.getTotalResPen();
        float resMultiplier = 100 - (target.getRes(damageElement) - resPen);
        float resMultiplierFloat = resMultiplier / 100;

        float damageTaken = 0;
        for (AbstractPower power : target.powerList) {
            damageTaken += power.getStat(PowerStat.DAMAGE_TAKEN);
            damageTaken += power.getConditionalDamageTaken(source, target, types);
        }
        float damageTakenMultiplier = 1 + damageTaken / 100;

        float toughnessMultiplier = 0.9f;
        if (target.weaknessBroken) {
            toughnessMultiplier = 1.0f;
        }

        float critChance = source.getTotalCritChance();
        for (AbstractPower power : source.powerList) {
            critChance += power.getConditionalCritRate(source, target, types);
        }
        for (AbstractPower power : source.powerList) {
            critChance = power.setFixedCritRate(source, target, types, critChance);
        }
        double roll = Battle.battle.critChanceRng.nextDouble() * 100 + 1;
        float critMultiplierFloat = 1.0f;

        boolean wasCrit = false;
        if (roll < (double)critChance) {
            wasCrit = true;
            float critDamage = source.getTotalCritDamage();
            for (AbstractPower power : source.powerList) {
                critDamage += power.getConditionalCritDamage(source, target, types);
            }
            for (AbstractPower power : target.powerList) {
                critDamage += power.receiveConditionalCritDamage(source, target, types);
            }
            for (AbstractPower power : source.powerList) {
                critDamage = power.setFixedCritDmg(source, target, types, critDamage);
            }
            float critMultiplier = 100.0f + critDamage;
            critMultiplierFloat = critMultiplier / 100;
        }

        float calculatedDamage = baseDamage * dmgMultiplierFloat * defMultiplierFloat * resMultiplierFloat * damageTakenMultiplier * toughnessMultiplier * critMultiplierFloat;
        if (wasCrit) {
            Battle.battle.addToLog(String.format("%s critically hit %s for %.3f damage - Base Damage: %.3f, Damage Multiplier: %.3f, Defense Multiplier: %.3f, Res Multiplier: %.3f, Damage Vuln Multiplier: %.3f, Toughness Multiplier: %.3f, Crit Damage Multiplier: %.3f",
                    source.name, target.name, calculatedDamage, baseDamage, dmgMultiplierFloat, defMultiplierFloat, resMultiplierFloat, damageTakenMultiplier, toughnessMultiplier, critMultiplierFloat));
        } else {
            Battle.battle.addToLog(String.format("%s hit %s for %.3f damage - Base Damage: %.3f, Damage Multiplier: %.3f, Defense Multiplier: %.3f, Res Multiplier: %.3f, Damage Vuln Multiplier: %.3f, Toughness Multiplier: %.3f",
                    source.name, target.name, calculatedDamage, baseDamage, dmgMultiplierFloat, defMultiplierFloat, resMultiplierFloat, damageTakenMultiplier, toughnessMultiplier));
        }
        return calculatedDamage;
    }

    public static float calculateBreakDamageAgainstEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier, AbstractCharacter.ElementType damageElement) {
        float maxToughnessMultiplier = 0.5f + (target.maxToughness / 40);
        float elementMultipler;
        if (damageElement == AbstractCharacter.ElementType.ICE || damageElement == AbstractCharacter.ElementType.LIGHTNING) {
            elementMultipler = 1;
        } else if (damageElement == AbstractCharacter.ElementType.PHYSICAL || damageElement == AbstractCharacter.ElementType.FIRE) {
            elementMultipler = 2;
        } else if (damageElement == AbstractCharacter.ElementType.QUANTUM || damageElement == AbstractCharacter.ElementType.IMAGINARY) {
            elementMultipler = 0.5f;
        } else {
            elementMultipler = 1.5f;
        }
        float baseDamage = multiplier * elementMultipler * 3767.5533f * maxToughnessMultiplier;
        float breakEffectMultiplier = source.getTotalBreakEffect();
        float breakEffectMultiplierFloat = 1 + breakEffectMultiplier / 100;

        float enemyDefPercent = target.getFinalDefense();
        float defMultiplierFloat = (source.level + 20) / ((target.level + 20) * (1 + enemyDefPercent / 100) + (source.level + 20));

        float resPen = source.getTotalResPen();
        float resMultiplier = 100 - (target.getRes(damageElement) - resPen);
        float resMultiplierFloat = resMultiplier / 100;

        ArrayList<AbstractCharacter.DamageType> types = new ArrayList<>();
        types.add(AbstractCharacter.DamageType.BREAK);
        float damageTaken = 0;
        for (AbstractPower power : target.powerList) {
            damageTaken += power.getStat(PowerStat.DAMAGE_TAKEN);
            damageTaken += power.getConditionalDamageTaken(source, target, types);
        }
        float damageTakenMultiplier = 1 + damageTaken / 100;

        float toughnessMultiplier = 0.9f;
        if (target.weaknessBroken) {
            toughnessMultiplier = 1.0f;
        }

        float calculatedDamage = baseDamage * breakEffectMultiplierFloat * defMultiplierFloat * resMultiplierFloat * damageTakenMultiplier * toughnessMultiplier;
        Battle.battle.addToLog(String.format("%s hit %s for %.3f Break damage - Base Damage: %.3f, Break Effect Multiplier: %.3f, Defense Multiplier: %.3f, Res Multiplier: %.3f, Damage Vuln Multiplier: %.3f, Toughness Multiplier: %.3f",
                source.name, target.name, calculatedDamage, baseDamage, breakEffectMultiplierFloat, defMultiplierFloat, resMultiplierFloat, damageTakenMultiplier, toughnessMultiplier));
        return calculatedDamage;
    }

    public static float calculateToughenssDamage(AbstractCharacter character, float toughnssDamage) {
        float weaknessBreakEff = character.getTotalWeaknessBreakEff();
        return toughnssDamage * (1 + weaknessBreakEff / 100);
    }

    public static void hitEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier, MultiplierStat stat, ArrayList<AbstractCharacter.DamageType> types, float toughnessDamage, AbstractCharacter.ElementType damageElement) {
        for (AbstractRelicSetBonus relicSetBonus : source.relicSetBonus) {
            relicSetBonus.onBeforeHitEnemy(source, target, types);
        }
        source.lightcone.onBeforeHitEnemy(source, target, types);
        for (AbstractPower power : target.powerList) {
            power.onBeforeHitEnemy(source, target, types);
        }
        float calculatedDamage = calculateDamageAgainstEnemy(source, target, multiplier, stat, types, damageElement);

        toughnessDamage = calculateToughenssDamage(source, toughnessDamage);
        if (target.weaknessMap.contains(damageElement) && toughnessDamage > 0) {
            target.reduceToughness(toughnessDamage);
        } else {
            if (source instanceof SwordMarch) {
                if (damageElement == source.elementType && ((SwordMarch) source).master != null && target.weaknessMap.contains(((SwordMarch) source).master.elementType)) {
                    target.reduceToughness(toughnessDamage);
                }
            }
        }
        Battle.battle.totalPlayerDamage += calculatedDamage;
        Battle.battle.updateContribution(source, calculatedDamage);
        attackDamageTotal += calculatedDamage;
        if (!enemiesHit.contains(target)) {
            enemiesHit.add(target);
        }
    }

    public static void hitEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier, MultiplierStat stat, ArrayList<AbstractCharacter.DamageType> types, float toughnessDamage) {
        hitEnemy(source, target, multiplier, stat, types, toughnessDamage, source.elementType);
    }

    public static void PreAttackLogic(AbstractCharacter character, ArrayList<AbstractCharacter.DamageType> types) {
        attackDamageTotal = 0;
        enemiesHit.clear();
        character.lightcone.onBeforeUseAttack(types);
        for (AbstractPower power : character.powerList) {
            power.onBeforeUseAttack(types);
        }
        for (AbstractRelicSetBonus relicSet : character.relicSetBonus) {
            relicSet.onBeforeUseAttack(types);
        }
    }

    public static void PostAttackLogic(AbstractCharacter character, ArrayList<AbstractCharacter.DamageType> types) {
        int damageTotal = (int) attackDamageTotal;
        Battle.battle.addToLog(String.format("Total Damage: %d", damageTotal));

        character.lightcone.onAttack(character, enemiesHit, types);
        ArrayList<AbstractPower> powersToTrigger = new ArrayList<>(character.powerList); // jank way to dodge comod exception lol
        for (AbstractPower power : powersToTrigger) {
            power.onAttack(character, enemiesHit, types);
        }
        for (AbstractEnemy enemy : enemiesHit) {
            ArrayList<AbstractPower> enemyPowersToTrigger = new ArrayList<>(enemy.powerList);
            for (AbstractPower power : enemyPowersToTrigger) {
                power.onAttacked(character, enemy, types);
            }
        }
    }

    public static void attackCharacter(AbstractEnemy source, AbstractCharacter target, int energyToGain) {
        if (target instanceof Moze) {
            if (((Moze) target).isDeparted) {
                return;
            }
        }
        Battle.battle.addToLog(source.name + " attacked " + target.name);
        target.onAttacked(source, energyToGain);
        ArrayList<AbstractPower> powersToTrigger = new ArrayList<>(target.powerList);
        for (AbstractPower power : powersToTrigger) {
            power.onAttacked(target, source, new ArrayList<>());
        }
    }

    public static void additionalDamageHitEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier, MultiplierStat stat) {
        float calculatedDamage = calculateDamageAgainstEnemy(source, target, multiplier, stat, new ArrayList<>(), source.elementType);
        Battle.battle.totalPlayerDamage += calculatedDamage;
        Battle.battle.updateContribution(source, calculatedDamage);
        attackDamageTotal += calculatedDamage;
    }

    public static void tingyunSkillHitEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier, MultiplierStat stat) {
        float calculatedDamage = calculateDamageAgainstEnemy(source, target, multiplier, stat, new ArrayList<>(),  AbstractCharacter.ElementType.LIGHTNING);
        Battle.battle.totalPlayerDamage += calculatedDamage;
        Battle.battle.updateContribution(source, calculatedDamage);
        attackDamageTotal += calculatedDamage;
    }
    public static void breakDamageHitEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier) {
        float calculatedDamage = calculateBreakDamageAgainstEnemy(source, target, multiplier, source.elementType);
        Battle.battle.totalPlayerDamage += calculatedDamage;
        Battle.battle.updateContribution(source, calculatedDamage);
        attackDamageTotal += calculatedDamage;
    }
}
