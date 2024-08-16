package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import battleLogic.Numby;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TracePower;

import java.util.ArrayList;
import java.util.HashMap;

public class Topaz extends AbstractCharacter {
    AbstractPower proofOfDebt = new ProofOfDebt();
    Numby numby;
    PermPower stonksPower;
    private int ultCounter = 0;
    private boolean techniqueActive = false;

    public int numbyAttacksMetrics = 0;
    public int numbyAdvancedTimesMetrics = 0;
    public int actualNumbyAdvanceMetric = 0;
    public int wastedNumbyAdvances = 0;
    private String numbyAttacksMetricName = "Numby Attacks";
    private String numbyAdvancedTimesMetricName = "Number of Useful Numby Advances";
    private String actualNumbyAdvanceMetricName = "Amount of AV Advanced by Numby";
    private String wastedNumbyAdvancesMetricName = "Number of Wasted Numby Advances";

    public Topaz() {
        super("Topaz", 931, 621, 412, 110, 80, ElementType.FIRE, 130, 75, Path.HUNT);

        this.addPower(new TracePower()
                .addStat(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 22.4f)
                .addStat(PowerStat.CRIT_CHANCE, 12.0f)
                .addStat(PowerStat.HP_PERCENT, 10));

        this.addPower(new FireWeaknessBonusDamage());

        numby = new Numby(this);
        stonksPower = new PermPower();
    }

    public void useSkill() {
        super.useSkill();

        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);

        AbstractEnemy target;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            target = Battle.battle.enemyTeam.get(middleIndex);
            target.addPower(proofOfDebt);
        } else {
            target = Battle.battle.enemyTeam.get(0);
            target.addPower(proofOfDebt);
        }

        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            if (enemy.hasPower(proofOfDebt.name) && enemy != target) {
                enemy.removePower(proofOfDebt);
                break;
            }
        }

        numbyAttack(types);
    }
    public void useBasicAttack() {
        super.useBasicAttack();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        types.add(DamageType.FOLLOW_UP);
        BattleHelpers.PreAttackLogic(this, types);

        AbstractEnemy target = null;
        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            if (enemy.hasPower(proofOfDebt.name)) {
                target = enemy;
                break;
            }
        }
        BattleHelpers.hitEnemy(this, target, 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        // only ult if numby isn't about to spin so we don't waste action forward as much
        if (Battle.battle.actionValueMap.get(numby) >= numby.getBaseAV() * 0.25) {
            super.useUltimate();
            this.stonksPower = PermPower.create(PowerStat.CRIT_DAMAGE, 25, "Topaz Ult Power");
            ultCounter = 2;
            this.addPower(this.stonksPower);
        }
    }

    public void onCombatStart() {
        Battle.battle.actionValueMap.put(numby, numby.getBaseAV());
        Battle.battle.getRandomEnemy().addPower(proofOfDebt);
    }

    public void takeTurn() {
        super.takeTurn();
        if (Battle.battle.numSkillPoints > 0) {
            if (firstMove) {
                useSkill();
                firstMove = false;
            } else if (Battle.battle.numSkillPoints <= 3 || ultCounter > 0) {
                useBasicAttack();
            } else {
                useSkill();
            }
        } else {
            useBasicAttack();
        }
    }

    public void numbyAttack(ArrayList<DamageType> types) {
        float multiplier;
        float toughnessDamage;
        if (ultCounter > 0) {
            multiplier = 2.1f;
            toughnessDamage = TOUGHNESS_DAMAGE_TWO_UNITS / 8;
        } else {
            multiplier = 1.5f;
            toughnessDamage = TOUGHNESS_DAMAGE_TWO_UNITS / 7;
        }
        types.add(DamageType.FOLLOW_UP);
        BattleHelpers.PreAttackLogic(this, types);

        AbstractEnemy target = null;
        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            if (enemy.hasPower(proofOfDebt.name)) {
                target = enemy;
                break;
            }
        }

        for (int i = 0; i < 7; i++) {
            BattleHelpers.hitEnemy(this, target, multiplier / 7, BattleHelpers.MultiplierStat.ATK, types, toughnessDamage);
        }
        if (ultCounter > 0) {
            BattleHelpers.hitEnemy(this, target, 0.9f, BattleHelpers.MultiplierStat.ATK, types, toughnessDamage);
            increaseEnergy(10);
            ultCounter--;
            if (ultCounter <= 0) {
                if (types.contains(DamageType.SKILL)) {
                    numby.AdvanceForward(); //manually advance numby when topaz skills with last charge of ult
                }
                removePower(stonksPower);
            }
        }

        if (techniqueActive) {
            techniqueActive = false;
            increaseEnergy(60);
        }

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useTechnique() {
        techniqueActive = true;
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(numbyAttacksMetricName, String.valueOf(numbyAttacksMetrics));
        map.put(numbyAdvancedTimesMetricName, String.valueOf(numbyAdvancedTimesMetrics));
        map.put(actualNumbyAdvanceMetricName, String.valueOf(actualNumbyAdvanceMetric));
        map.put(wastedNumbyAdvancesMetricName, String.valueOf(wastedNumbyAdvances));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(numbyAttacksMetricName);
        list.add(numbyAdvancedTimesMetricName);
        list.add(actualNumbyAdvanceMetricName);
        list.add(wastedNumbyAdvancesMetricName);
        return list;
    }

    private class ProofOfDebt extends AbstractPower {
        public ProofOfDebt() {
            this.name = this.getClass().getSimpleName();
            lastsForever = true;
            this.type = PowerType.DEBUFF;
        }

        @Override
        public float getConditionalDamageTaken(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    return 50;
                }
            }
            return 0;
        }

        @Override
        public void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types) {
            for (AbstractCharacter.DamageType type : types) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    Topaz.this.numby.AdvanceForward();
                    break;
                }
            }
            if (ultCounter > 0) {
                for (AbstractCharacter.DamageType type : types) {
                    if (type == DamageType.BASIC || type == DamageType.SKILL || type == DamageType.ULTIMATE) {
                        Topaz.this.numby.AdvanceForward();
                        break;
                    }
                }
            }
        }
    }

    private static class FireWeaknessBonusDamage extends AbstractPower {
        public FireWeaknessBonusDamage() {
            this.name = this.getClass().getSimpleName();
            lastsForever = true;
        }

        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (enemy.weaknessMap.contains(ElementType.FIRE)) {
                return 15;
            }
            return 0;
        }
    }
}
