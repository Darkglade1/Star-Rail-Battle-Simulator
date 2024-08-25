package battleLogic;

import battleLogic.log.lines.character.Attacked;
import battleLogic.log.lines.character.BreakDamageHitResult;
import battleLogic.log.lines.character.CritHitResult;
import battleLogic.log.lines.character.TotalDamage;
import characters.AbstractCharacter;
import characters.moze.Moze;
import characters.march.SwordMarch;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PowerStat;

import java.util.ArrayList;
import java.util.HashMap;

public class BattleHelpers implements BattleParticipant {

    private final IBattle battle;

    public BattleHelpers(IBattle battle) {
        this.battle = battle;
    }

    @Override
    public IBattle getBattle() {
        return this.battle;
    }

    @Override
    public boolean inBattle() {
        return BattleParticipant.super.inBattle();
    }

    public enum MultiplierStat {
        ATK, HP, DEF
    }
    
    private float attackDamageTotal = 0;
    public ArrayList<AbstractEnemy> enemiesHit = new ArrayList<>();
    private HashMap<String, Float> damageBonusMultiConstituents = new HashMap<>();
    private HashMap<String, Float> defenseMultiConstituents = new HashMap<>();
    private HashMap<String, Float> resMultiConstituents = new HashMap<>();
    private HashMap<String, Float> damageVulnMultiConstituents = new HashMap<>();
    private HashMap<String, Float> critDmgMultiConstituents = new HashMap<>();

    private void clearConstituents() {
        damageBonusMultiConstituents.clear();
        defenseMultiConstituents.clear();
        resMultiConstituents.clear();
        damageVulnMultiConstituents.clear();
        critDmgMultiConstituents.clear();
    }
    
    public float calculateDamageAgainstEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier, MultiplierStat stat, ArrayList<AbstractCharacter.DamageType> types, AbstractCharacter.ElementType damageElement) {
        clearConstituents();
        float statToUse;
        if (stat == MultiplierStat.ATK) {
            statToUse = source.getFinalAttack();
        } else if (stat == MultiplierStat.HP) {
            statToUse = source.getFinalHP();
        } else {
            statToUse = source.getFinalDefense();
        }
        float baseDamage = multiplier * statToUse;

        float dmgMultiplier = 0;
        if (damageElement == source.elementType) {
            for (AbstractPower power : source.powerList) {
                float sameBonus = power.getStat(PowerStat.SAME_ELEMENT_DAMAGE_BONUS);
                float globalBonus = power.getStat(PowerStat.DAMAGE_BONUS);
                dmgMultiplier += sameBonus;
                dmgMultiplier += globalBonus;
                if (sameBonus != 0) {
                    damageBonusMultiConstituents.put(power.name, sameBonus);
                }
                if (globalBonus != 0) {
                    damageBonusMultiConstituents.put(power.name, globalBonus);
                }
            }
        } else {
            for (AbstractPower power : source.powerList) {
                float bonus = power.getStat(PowerStat.DAMAGE_BONUS);
                dmgMultiplier += bonus;
                if (bonus != 0) {
                    damageBonusMultiConstituents.put(power.name, bonus);
                }
            }
        }
        for (AbstractPower power : source.powerList) {
            float bonus = power.getConditionalDamageBonus(source, target, types);
            dmgMultiplier += bonus;
            if (bonus != 0) {
                damageBonusMultiConstituents.put(power.name, bonus);
            }
        }
        for (AbstractPower power : target.powerList) {
            float bonus = power.receiveConditionalDamageBonus(source, target, types);
            dmgMultiplier += bonus;
            if (bonus != 0) {
                damageBonusMultiConstituents.put(power.name, bonus);
            }
        }
        float dmgMultiplierFloat = 1 + dmgMultiplier / 100;

        float enemyDefPercent = 0;
        for (AbstractPower power : target.powerList) {
            float bonus = power.getStat(PowerStat.DEF_PERCENT);
            float reduction = power.getStat(PowerStat.DEFENSE_REDUCTION);
            enemyDefPercent += bonus;
            enemyDefPercent -= reduction;
            if (bonus != 0) {
                defenseMultiConstituents.put(power.name, bonus);
            }
            if (reduction != 0) {
                defenseMultiConstituents.put(power.name, -reduction);
            }
        }
        for (AbstractPower power : source.powerList) {
            float ignore = power.getConditionDefenseIgnore(source, target, types);
            enemyDefPercent -= ignore;
            if (ignore != 0) {
                defenseMultiConstituents.put(power.name, -ignore);
            }
        }
        if (enemyDefPercent < -100) {
            enemyDefPercent = -100;
        }
        float defMultiplierFloat = (source.level + 20) / ((target.level + 20) * (1 + enemyDefPercent / 100) + (source.level + 20));

        float resPen = 0;
        for (AbstractPower power : source.powerList) {
            float bonus = power.getStat(PowerStat.RES_PEN);
            resPen += bonus;
            if (bonus != 0) {
                resMultiConstituents.put(power.name, bonus);
            }
        }
        if (target.getRes(damageElement) != 0) {
            resMultiConstituents.put("Enemy Res", (float)-target.getRes(damageElement));
        }
        float resMultiplier = 100 + (-target.getRes(damageElement) + resPen);
        float resMultiplierFloat = resMultiplier / 100;

        float damageTaken = 0;
        for (AbstractPower power : target.powerList) {
            float constBonus = power.getStat(PowerStat.DAMAGE_TAKEN);
            float condBonus = power.getConditionalDamageTaken(source, target, types);
            damageTaken += constBonus;
            damageTaken += condBonus;
            if (constBonus != 0) {
                damageVulnMultiConstituents.put(power.name, constBonus);
            }
            if (condBonus != 0) {
                damageVulnMultiConstituents.put(power.name, condBonus);
            }
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
        double roll = getBattle().getCritChanceRng().nextDouble() * 100 + 1;

        boolean wasCrit = false;
        if (roll < (double)critChance) {
            wasCrit = true;
        }
        float critDamage = source.baseCritDamage;
        for (AbstractPower power : source.powerList) {
            float bonus = power.getStat(PowerStat.CRIT_DAMAGE);
            critDamage += bonus;
            if (bonus != 0) {
                critDmgMultiConstituents.put(power.name, bonus);
            }
        }
        for (AbstractPower power : source.powerList) {
            float bonus = power.getConditionalCritDamage(source, target, types);
            critDamage += bonus;
            if (bonus != 0) {
                critDmgMultiConstituents.put(power.name, bonus);
            }
        }
        for (AbstractPower power : target.powerList) {
            float bonus = power.receiveConditionalCritDamage(source, target, types);
            critDamage += bonus;
            if (bonus != 0) {
                critDmgMultiConstituents.put(power.name, bonus);
            }
        }
        for (AbstractPower power : source.powerList) {
            float fixed = power.setFixedCritDmg(source, target, types, critDamage);
            if (fixed != critDamage) {
                critDmgMultiConstituents.clear();
                critDmgMultiConstituents.put(power.name, fixed);
            }
            critDamage = fixed;
        }
        float expectedCritMultiplier = (100.0f + critDamage * critChance * 0.01f) / 100;
        float critMultiplier = (100.0f + critDamage) / 100;

        float calculatedDamage = baseDamage * dmgMultiplierFloat * defMultiplierFloat * resMultiplierFloat * damageTakenMultiplier * toughnessMultiplier * expectedCritMultiplier;
        getBattle().addToLog(new CritHitResult(source, target, calculatedDamage, baseDamage, dmgMultiplierFloat, defMultiplierFloat, resMultiplierFloat, damageTakenMultiplier, toughnessMultiplier, critMultiplier, expectedCritMultiplier,
                damageBonusMultiConstituents, defenseMultiConstituents, resMultiConstituents, damageVulnMultiConstituents, critDmgMultiConstituents));
        return calculatedDamage;
    }

    public float calculateBreakDamageAgainstEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier, AbstractCharacter.ElementType damageElement) {
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
        getBattle().addToLog(new BreakDamageHitResult(source, target, calculatedDamage, baseDamage, breakEffectMultiplierFloat, defMultiplierFloat, resMultiplierFloat, damageTakenMultiplier, toughnessMultiplier));
        return calculatedDamage;
    }

    public float calculateToughenssDamage(AbstractCharacter character, float toughnssDamage) {
        float weaknessBreakEff = character.getTotalWeaknessBreakEff();
        return toughnssDamage * (1 + weaknessBreakEff / 100);
    }

    public void hitEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier, MultiplierStat stat, ArrayList<AbstractCharacter.DamageType> types, float toughnessDamage, AbstractCharacter.ElementType damageElement) {
        source.emit(l -> {
            l.onBeforeHitEnemy(source, target, types);
        });
        target.emit(l -> {
            l.onBeforeHitEnemy(source, target, types);
        });
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
        getBattle().increaseTotalPlayerDmg(calculatedDamage);
        getBattle().updateContribution(source, calculatedDamage);
        attackDamageTotal += calculatedDamage;
        if (!enemiesHit.contains(target)) {
            enemiesHit.add(target);
        }
    }

    public void hitEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier, MultiplierStat stat, ArrayList<AbstractCharacter.DamageType> types, float toughnessDamage) {
        hitEnemy(source, target, multiplier, stat, types, toughnessDamage, source.elementType);
    }

    public void PreAttackLogic(AbstractCharacter character, ArrayList<AbstractCharacter.DamageType> types) {
        attackDamageTotal = 0;
        enemiesHit.clear();
        character.emit(l -> {
            l.onBeforeUseAttack(types);
        });
    }

    public void PostAttackLogic(AbstractCharacter character, ArrayList<AbstractCharacter.DamageType> types) {
        int damageTotal = (int) attackDamageTotal;
        getBattle().addToLog(new TotalDamage(character, types, damageTotal));

        character.emit(l -> {
            l.onAttack(character, enemiesHit, types);
        });
        for (AbstractEnemy enemy : enemiesHit) {
            enemy.emit(l -> {
                l.onAttacked(character, enemy, types, 0);
            });
        }

        character.emit(l -> {
            l.afterAttackFinish(character, enemiesHit, types);
        });
    }

    public void attackCharacter(AbstractEnemy source, AbstractCharacter target, int energyToGain) {
        if (target instanceof Moze) {
            if (((Moze) target).isDeparted) {
                return;
            }
        }
        getBattle().addToLog(new Attacked(source, target));
        target.emit(l -> {
            l.onAttacked(target, source, new ArrayList<>(), energyToGain);
        });
    }

    public void additionalDamageHitEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier, MultiplierStat stat) {
        float calculatedDamage = calculateDamageAgainstEnemy(source, target, multiplier, stat, new ArrayList<>(), source.elementType);
        getBattle().increaseTotalPlayerDmg(calculatedDamage);
        getBattle().updateContribution(source, calculatedDamage);
        attackDamageTotal += calculatedDamage;
    }

    public void tingyunSkillHitEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier, MultiplierStat stat) {
        float calculatedDamage = calculateDamageAgainstEnemy(source, target, multiplier, stat, new ArrayList<>(),  AbstractCharacter.ElementType.LIGHTNING);
        getBattle().increaseTotalPlayerDmg(calculatedDamage);
        getBattle().updateContribution(source, calculatedDamage);
        attackDamageTotal += calculatedDamage;
    }
    public void breakDamageHitEnemy(AbstractCharacter source, AbstractEnemy target, float multiplier) {
        float calculatedDamage = calculateBreakDamageAgainstEnemy(source, target, multiplier, source.elementType);
        getBattle().increaseTotalPlayerDmg(calculatedDamage);
        getBattle().updateContribution(source, calculatedDamage);
        attackDamageTotal += calculatedDamage;
    }
}
