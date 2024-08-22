package characters.topaz;

import battleLogic.AbstractSummon;
import battleLogic.BattleHelpers;
import battleLogic.Numby;
import characters.AbstractCharacter;
import characters.AbstractSummoner;
import characters.Path;
import characters.goal.shared.AlwaysUltGoal;
import characters.goal.shared.DontUltMissingPowerGoal;
import characters.robin.Robin;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TracePower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Topaz extends AbstractSummoner<Topaz> {
    AbstractPower proofOfDebt = new ProofOfDebt();
    Numby numby;
    PermPower stonksPower;
    int ultCounter = 0;
    private boolean techniqueActive = false;

    public int numbyAttacksMetrics = 0;
    public int numbyAdvancedTimesMetrics = 0;
    public int actualNumbyAdvanceMetric = 0;
    public int wastedNumbyAdvances = 0;
    private String numbyAttacksMetricName = "Numby Attacks";
    private String numbyAdvancedTimesMetricName = "Number of Useful Numby Advances";
    private String actualNumbyAdvanceMetricName = "Amount of AV Advanced by Numby";
    private String wastedNumbyAdvancesMetricName = "Number of Wasted Numby Advances";
    private String leftoverAVNumbyMetricName = "Leftover AV (Numby)";
    private String leftoverUltChargesMetricName = "Leftover Ult Charges";
    public static final String NAME = "Topaz";

    public Topaz() {
        super(NAME, 931, 621, 412, 110, 80, ElementType.FIRE, 130, 75, Path.HUNT);

        this.addPower(new TracePower()
                .setStat(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 22.4f)
                .setStat(PowerStat.CRIT_CHANCE, 12.0f)
                .setStat(PowerStat.HP_PERCENT, 10));

        numby = new Numby(this);
        stonksPower = new PermPower();

        this.registerGoal(0, DontUltMissingPowerGoal.robin(this));
        this.registerGoal(10, new TopazUltGoal(this));
        this.registerGoal(20, new AlwaysUltGoal<>(this));
        this.registerGoal(0, new TopazTurnGoal(this));
    }

    public void useSkill() {

        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);

        AbstractEnemy target = getBattle().getMiddleEnemy();
        target.addPower(proofOfDebt);

        for (AbstractEnemy enemy : getBattle().getEnemies()) {
            if (enemy.hasPower(proofOfDebt.name) && enemy != target) {
                enemy.removePower(proofOfDebt);
                break;
            }
        }

        numbyAttack(types);
    }
    public void useBasic() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        types.add(DamageType.FOLLOW_UP);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy target = null;
        for (AbstractEnemy enemy : getBattle().getEnemies()) {
            if (enemy.hasPower(proofOfDebt.name)) {
                target = enemy;
                break;
            }
        }
        getBattle().getHelper().hitEnemy(this, target, 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        this.stonksPower = PermPower.create(PowerStat.CRIT_DAMAGE, 25, "Topaz Ult Power");
        ultCounter = 2;
        this.addPower(this.stonksPower);
    }

    public void onCombatStart() {
        this.addPower(new FireWeaknessBonusDamage());
        getBattle().getActionValueMap().put(numby, numby.getBaseAV());
        getBattle().getRandomEnemy().addPower(proofOfDebt);
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
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy target = null;
        for (AbstractEnemy enemy : getBattle().getEnemies()) {
            if (enemy.hasPower(proofOfDebt.name)) {
                target = enemy;
                break;
            }
        }

        for (int i = 0; i < 7; i++) {
            getBattle().getHelper().hitEnemy(this, target, multiplier / 7, BattleHelpers.MultiplierStat.ATK, types, toughnessDamage);
        }
        if (ultCounter > 0) {
            getBattle().getHelper().hitEnemy(this, target, 0.9f, BattleHelpers.MultiplierStat.ATK, types, toughnessDamage);
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

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useTechnique() {
        techniqueActive = true;
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(leftoverAVNumbyMetricName, String.valueOf(getBattle().getActionValueMap().get(numby)));
        map.put(leftoverUltChargesMetricName, String.valueOf(ultCounter));
        map.put(numbyAttacksMetricName, String.valueOf(numbyAttacksMetrics));
        map.put(numbyAdvancedTimesMetricName, String.valueOf(numbyAdvancedTimesMetrics));
        map.put(actualNumbyAdvanceMetricName, String.valueOf(actualNumbyAdvanceMetric));
        map.put(wastedNumbyAdvancesMetricName, String.valueOf(wastedNumbyAdvances));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(leftoverAVNumbyMetricName);
        list.add(leftoverUltChargesMetricName);
        list.add(numbyAttacksMetricName);
        list.add(numbyAdvancedTimesMetricName);
        list.add(actualNumbyAdvanceMetricName);
        list.add(wastedNumbyAdvancesMetricName);
        return list;
    }

    @Override
    public List<AbstractSummon<Topaz>> getSummons() {
        return Collections.singletonList(numby);
    }

    private class ProofOfDebt extends AbstractPower {
        public ProofOfDebt() {
            this.name = this.getClass().getSimpleName();
            lastsForever = true;
            this.type = PowerType.DEBUFF;
        }

        @Override
        public float getConditionalDamageTaken(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    return 50;
                }
            }
            return 0;
        }

        @Override
        public void onAttacked(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyFromAttacked) {
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

        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (enemy.weaknessMap.contains(ElementType.FIRE)) {
                return 15;
            }
            return 0;
        }
    }
}
