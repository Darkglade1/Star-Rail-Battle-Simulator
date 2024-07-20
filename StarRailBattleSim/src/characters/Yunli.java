package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import lightcones.DanceAtSunset;
import powers.AbstractPower;
import powers.PermPower;
import powers.TauntPower;
import powers.TempPower;

import java.util.ArrayList;

public class Yunli extends AbstractCharacter {

    public boolean isParrying;
    AbstractPower cullPower;
    AbstractPower techniqueDamageBonus;
    AbstractPower tauntPower = new TauntPower(this);

    private int numNormalCounters = 0;
    private int num1StackCulls = 0;
    private int num2StackCulls = 0;
    public int numSlashes = 0;

    public Yunli() {
        super("Yunli", 1358, 679, 461, 94, 80, ElementType.PHYSICAL, 240, 125);
        this.ultCost = 120;
        this.isDPS = true;

        cullPower = new CullCritDamageBuff();

        techniqueDamageBonus = new PermPower();
        techniqueDamageBonus.bonusDamageBonus = 80;
        techniqueDamageBonus.name = "Technique Damage Bonus";

        PermPower tracesPower = new PermPower();
        tracesPower.name = "Traces Stat Bonus";
        tracesPower.bonusAtkPercent = 28;
        tracesPower.bonusCritChance = 6.7f;
        tracesPower.bonusHPPercent = 18;
        this.addPower(tracesPower);
    }

    public void useSkill() {
        super.useSkill();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);
        BattleHelpers.PreAttackLogic(this, types);

        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex), 1.2f, BattleHelpers.MultiplierStat.ATK, types, 60);
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex + 1), 0.6f, BattleHelpers.MultiplierStat.ATK,types, 30);
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex - 1), 0.6f, BattleHelpers.MultiplierStat.ATK, types, 30);
        } else {
            AbstractEnemy enemy = Battle.battle.enemyTeam.get(0);
            BattleHelpers.hitEnemy(this, enemy, 1.2f, BattleHelpers.MultiplierStat.ATK, types, 60);
            if (Battle.battle.enemyTeam.size() == 2) {
                BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(1), 0.6f, BattleHelpers.MultiplierStat.ATK, types, 30);
            }
        }
        BattleHelpers.PostAttackLogic(this, types);
    }
    public void useBasicAttack() {
        super.useBasicAttack();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        BattleHelpers.PreAttackLogic(this, types);

        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex), 1.0f, BattleHelpers.MultiplierStat.ATK, types, 30);
        } else {
            AbstractEnemy enemy = Battle.battle.enemyTeam.get(0);
            BattleHelpers.hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.ATK, types, 30);
        }
        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        super.useUltimate();
        isParrying = true;
        addPower(cullPower);

        tauntPower.lastsForever = true; // used so I can manually remove later
        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            enemy.addPower(tauntPower);
        }
    }

    public void onAttacked(AbstractEnemy enemy, int energyFromAttacked) {
        addPower(getTrueSunderPower());
        if (isParrying) {
            useCull(enemy);
            removePower(cullPower);
            isParrying = false;
            for (AbstractEnemy e : Battle.battle.enemyTeam) {
                e.removePower(tauntPower);
            }
        } else {
            numNormalCounters++;
            Battle.battle.addToLog(name + " used Counter");
            ArrayList<DamageType> types = new ArrayList<>();
            types.add(DamageType.FOLLOW_UP);
            BattleHelpers.PreAttackLogic(this, types);

            int enemyIndex = Battle.battle.enemyTeam.indexOf(enemy);
            BattleHelpers.hitEnemy(this, enemy, 1.2f, BattleHelpers.MultiplierStat.ATK, types, 30);
            if (enemyIndex + 1 < Battle.battle.enemyTeam.size()) {
                BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex + 1), 0.6f, BattleHelpers.MultiplierStat.ATK, types, 30);
            }
            if (enemyIndex - 1 >= 0) {
                BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex - 1), 0.6f, BattleHelpers.MultiplierStat.ATK, types, 30);
            }
            BattleHelpers.PostAttackLogic(this, types);
        }
        increaseEnergy(25);
        super.onAttacked(enemy, energyFromAttacked);
    }

    public void takeTurn() {
        super.takeTurn();
        if (Battle.battle.numSkillPoints > 1) {
            useSkill();
        } else {
            useBasicAttack();
        }
    }

    public void useCull(AbstractEnemy enemy) {
        AbstractPower power = new DanceAtSunset.DanceAtSunsetDamagePower();
        AbstractPower sunsetPower = getPower(power.name);
        if (sunsetPower != null && sunsetPower.stacks == 2) {
            num2StackCulls++;
        } else {
            num1StackCulls++;
        }
        Battle.battle.addToLog(name + " used Cull");
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.FOLLOW_UP);
        types.add(DamageType.ULTIMATE);
        BattleHelpers.PreAttackLogic(this, types);

        int enemyIndex = Battle.battle.enemyTeam.indexOf(enemy);
        BattleHelpers.hitEnemy(this, enemy, 2.2f, BattleHelpers.MultiplierStat.ATK, types, 60);
        if (enemyIndex + 1 < Battle.battle.enemyTeam.size()) {
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex + 1), 1.1f, BattleHelpers.MultiplierStat.ATK, types, 30);
        }
        if (enemyIndex - 1 >= 0) {
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex - 1), 1.1f, BattleHelpers.MultiplierStat.ATK, types, 30);
        }

        int numBounces = 6;
        while (numBounces > 0) {
            BattleHelpers.hitEnemy(this, Battle.battle.getRandomEnemy(), 0.72f, BattleHelpers.MultiplierStat.ATK, types, 15);
            numBounces--;
        }
        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useSlash(AbstractEnemy enemy) {
        numSlashes++;
        Battle.battle.addToLog(name + " used Slash");
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.FOLLOW_UP);
        types.add(DamageType.ULTIMATE);
        BattleHelpers.PreAttackLogic(this, types);

        int enemyIndex = Battle.battle.enemyTeam.indexOf(enemy);
        BattleHelpers.hitEnemy(this, enemy, 2.2f, BattleHelpers.MultiplierStat.ATK, types, 60);
        if (enemyIndex + 1 < Battle.battle.enemyTeam.size()) {
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex + 1), 1.1f, BattleHelpers.MultiplierStat.ATK, types, 30);
        }
        if (enemyIndex - 1 >= 0) {
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex - 1), 1.1f, BattleHelpers.MultiplierStat.ATK, types, 30);
        }

        removePower(cullPower);
        isParrying = false;
        for (AbstractEnemy e : Battle.battle.enemyTeam) {
            e.removePower(tauntPower);
        }

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useTechnique() {
        addPower(getTrueSunderPower());
        addPower(techniqueDamageBonus);
        useCull(Battle.battle.getRandomEnemy());
        removePower(techniqueDamageBonus);
        increaseEnergy(10);
    }

    public AbstractPower getTrueSunderPower() {
        TempPower trueSunderAtkBonus = new TempPower();
        trueSunderAtkBonus.bonusAtkPercent = 30;
        trueSunderAtkBonus.turnDuration = 1;
        trueSunderAtkBonus.name = "True Sunder Atk Bonus";
        return trueSunderAtkBonus;
    }

    public String getMetrics() {
        String metrics = super.getMetrics();
        String charSpecificMetrics = String.format("\nNormal Counters: %d \n1 stack Culls: %d \n2 stack Culls: %d \nSlashes: %d", numNormalCounters, num1StackCulls, num2StackCulls, numSlashes);
        return metrics + charSpecificMetrics;
    }

    private static class CullCritDamageBuff extends AbstractPower {
        public CullCritDamageBuff() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    return 100;
                }
            }
            return 0;
        }
    }
}
