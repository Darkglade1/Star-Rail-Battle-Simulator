package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import lightcones.destruction.DanceAtSunset;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TauntPower;
import powers.TempPower;
import powers.TracePower;

import java.util.ArrayList;
import java.util.HashMap;

public class Yunli extends AbstractCharacter {

    public boolean isParrying;
    AbstractPower cullPower = new CullCritDamageBuff();
    AbstractPower techniqueDamageBonus = PermPower.create(PowerStat.DAMAGE_BONUS, 80, "Technique Damage Bonus");
    AbstractPower tauntPower = new TauntPower(this);

    private int numNormalCounters = 0;
    private int num1StackCulls = 0;
    private int num2StackCulls = 0;
    public int numSlashes = 0;
    private String numNormalCountersMetricName = "Normal Counters";
    private String num1StackCullsMetricName = "Number of Culls (1 S1 stack)";
    private String num2StackCullsMetricName = "Number of Culls (2 S1 stacks)";
    private String numSlashesMetricName = "Number of Slashes";

    public Yunli() {
        super("Yunli", 1358, 679, 461, 94, 80, ElementType.PHYSICAL, 240, 125, Path.DESTRUCTION);

        this.ultCost = 120;
        this.isDPS = true;

        this.addPower(new TracePower()
                .setStat(PowerStat.ATK_PERCENT, 28)
                .setStat(PowerStat.CRIT_CHANCE, 6.7f)
                .setStat(PowerStat.HP_PERCENT, 18));
    }

    public void useSkill() {
        super.useSkill();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);
        BattleHelpers.PreAttackLogic(this, types);

        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex), 1.2f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex + 1), 0.6f, BattleHelpers.MultiplierStat.ATK,types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex - 1), 0.6f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        } else {
            AbstractEnemy enemy = Battle.battle.enemyTeam.get(0);
            BattleHelpers.hitEnemy(this, enemy, 1.2f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);
            if (Battle.battle.enemyTeam.size() == 2) {
                BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(1), 0.6f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
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
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex), 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        } else {
            AbstractEnemy enemy = Battle.battle.enemyTeam.get(0);
            BattleHelpers.hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
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
            BattleHelpers.hitEnemy(this, enemy, 1.2f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
            if (enemyIndex + 1 < Battle.battle.enemyTeam.size()) {
                BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex + 1), 0.6f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
            }
            if (enemyIndex - 1 >= 0) {
                BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex - 1), 0.6f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
            }
            BattleHelpers.PostAttackLogic(this, types);
            increaseEnergy(5);
        }
        increaseEnergy(15);
        super.onAttacked(enemy, energyFromAttacked);
    }

    public void takeTurn() {
        super.takeTurn();
        if (firstMove && Battle.battle.numSkillPoints > 0) {
            useSkill();
            firstMove = false;
        } else if (Battle.battle.numSkillPoints > 1) {
            useSkill();
        } else {
            useBasicAttack();
        }
    }

    public void useCull(AbstractEnemy enemy) {
        increaseEnergy(10);
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
        BattleHelpers.hitEnemy(this, enemy, 2.2f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);
        if (enemyIndex + 1 < Battle.battle.enemyTeam.size()) {
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex + 1), 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        }
        if (enemyIndex - 1 >= 0) {
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex - 1), 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        }

        int numBounces = 6;
        while (numBounces > 0) {
            BattleHelpers.hitEnemy(this, Battle.battle.getRandomEnemy(), 0.72f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_HALF_UNIT);
            numBounces--;
        }
        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useSlash(AbstractEnemy enemy) {
        increaseEnergy(10);
        numSlashes++;
        Battle.battle.addToLog(name + " used Slash");
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.FOLLOW_UP);
        types.add(DamageType.ULTIMATE);
        BattleHelpers.PreAttackLogic(this, types);

        int enemyIndex = Battle.battle.enemyTeam.indexOf(enemy);
        BattleHelpers.hitEnemy(this, enemy, 2.2f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);
        if (enemyIndex + 1 < Battle.battle.enemyTeam.size()) {
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex + 1), 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        }
        if (enemyIndex - 1 >= 0) {
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex - 1), 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
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
    }

    public AbstractPower getTrueSunderPower() {
        return TempPower.create(PowerStat.ATK_PERCENT, 30, 1, "True Sunder Atk Bonus");
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(numNormalCountersMetricName, String.valueOf(numNormalCounters));
        map.put(num1StackCullsMetricName, String.valueOf(num1StackCulls));
        map.put(num2StackCullsMetricName, String.valueOf(num2StackCulls));
        map.put(numSlashesMetricName, String.valueOf(numSlashes));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(numNormalCountersMetricName);
        list.add(num1StackCullsMetricName);
        list.add(num2StackCullsMetricName);
        list.add(numSlashesMetricName);
        return list;
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
