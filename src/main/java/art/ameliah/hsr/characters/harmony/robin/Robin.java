package art.ameliah.hsr.characters.harmony.robin;

import art.ameliah.hsr.battleLogic.AbstractEntity;
import art.ameliah.hsr.battleLogic.combat.ally.AttackLogic;
import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.DamageType;
import art.ameliah.hsr.characters.ElementType;
import art.ameliah.hsr.characters.MoveType;
import art.ameliah.hsr.characters.Path;
import art.ameliah.hsr.characters.goal.shared.target.enemy.HighestEnemyTargetGoal;
import art.ameliah.hsr.characters.goal.shared.turn.SkillCounterTurnGoal;
import art.ameliah.hsr.characters.goal.shared.ult.AlwaysUltGoal;
import art.ameliah.hsr.characters.goal.shared.ult.DontUltNumby;
import art.ameliah.hsr.characters.goal.shared.ult.DontUltWhenClose;
import art.ameliah.hsr.characters.goal.shared.ult.UltAtEndOfBattle;
import art.ameliah.hsr.enemies.AbstractEnemy;
import art.ameliah.hsr.metrics.CounterMetric;
import art.ameliah.hsr.powers.AbstractPower;
import art.ameliah.hsr.powers.PermPower;
import art.ameliah.hsr.powers.PowerStat;
import art.ameliah.hsr.powers.TracePower;
import art.ameliah.hsr.utils.Comparators;
import art.ameliah.hsr.utils.Randf;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class Robin extends AbstractCharacter<Robin> implements SkillCounterTurnGoal.SkillCounterCharacter {

    public static final String NAME = "Robin";
    public static final String ULT_POWER_NAME = "RobinUltPower";

    private final PermPower skillPower = PermPower.create(PowerStat.DAMAGE_BONUS, 50, "Robin Skill Power");
    private final RobinUltPower ultPower = new RobinUltPower();
    private final RobinFixedCritPower fixedCritPower = new RobinFixedCritPower();
    private final Concerto concerto = new Concerto(this);

    protected CounterMetric<Integer> allyAttacks = metricRegistry.register(CounterMetric.newIntegerCounter("robin-ally-attacks", "Number of Ally Attacks"));
    protected CounterMetric<Integer> concertoHits = metricRegistry.register(CounterMetric.newIntegerCounter("robin-concerto-hits", "Number of Concerto Hits"));

    private int skillCounter = 0;

    public Robin() {
        super(NAME, 1281, 640, 485, 102, 80, ElementType.PHYSICAL, 160, 100, Path.HARMONY);

        this.skillEnergyGain = 35;

        this.addPower(new TracePower()
                .setStat(PowerStat.ATK_PERCENT, 28)
                .setStat(PowerStat.HP_PERCENT, 18)
                .setStat(PowerStat.FLAT_SPEED, 5));

        this.registerGoal(0, new SkillCounterTurnGoal<>(this));

        this.registerGoal(0, new UltAtEndOfBattle<>(this));
        this.registerGoal(10, new Robin0AVUltGoal(this));
        this.registerGoal(20, new DontUltNumby<>(this));
        this.registerGoal(25, new DontWastedAAUltGoal(this));
        this.registerGoal(30, new RobinBroynaFeixiaoUltGoal(this));
        this.registerGoal(35, new RobinSundayDPSUltGoal(this));
        this.registerGoal(40, new RobinDPSUltGoal(this));
        this.registerGoal(100, new AlwaysUltGoal<>(this));

        this.registerGoal(0, new HighestEnemyTargetGoal<>(this));
    }

    public void useSkill() {
        skillCounter = 3;
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.addPower(skillPower);
        }
    }

    public void useBasic() {
        this.startAttack()
                .handle(dh -> {
                    AbstractEnemy target = this.getTarget(MoveType.BASIC);
                    dh.addTypes(DamageType.BASIC);
                    dh.logic(target, al -> al.hit(target, 1, TOUGHNESS_DAMAGE_SINGLE_UNIT));
                }).execute();
    }

    public void useUltimate() {
        var toAdvance = getBattle().getActionValueMap()
                .entrySet()
                .stream()
                .filter(e -> e.getKey() instanceof AbstractCharacter<?>)
                .filter(e -> !e.getKey().equals(this))
                .sorted(Comparators::CompareSpd)
                .map(Map.Entry::getKey)
                .toList()
                .reversed();

        for (var character : toAdvance) {
            getBattle().AdvanceEntity(character, 100);
        }


        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.addPower(ultPower);
        }
        this.addPower(fixedCritPower);
        getBattle().getActionValueMap().remove(this);
        getBattle().getActionValueMap().put(concerto, concerto.getBaseAV());

        increaseEnergy(60, "Test"); // fake hit energy to ensure ult
    }

    public void onCombatStart() {
        getBattle().AdvanceEntity(this, 25);
        getBattle().registerForPlayers(p -> p.addPower(new RobinTalentPower()));
    }

    public void onTurnStart() {
        if (skillCounter > 0) {
            skillCounter--;
            if (skillCounter <= 0) {
                for (AbstractCharacter<?> character : getBattle().getPlayers()) {
                    character.removePower(skillPower);
                }
            }
        }
    }

    public void useTechnique() {
        increaseEnergy(5, TECHNIQUE_ENERGY_GAIN);
    }

    public void addPower(@NotNull AbstractPower power) {
        super.addPower(power);
        ultPower.updateAtkBuff();
    }

    public void onConcertoEnd() {
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.removePower(ultPower);
        }
        this.removePower(fixedCritPower);
    }

    @Override
    public String leftOverAV() {
        Float leftoverAV = getBattle().getActionValueMap().get(this);
        if (leftoverAV == null) {
            return String.format("%.2f (Concerto)", getBattle().getActionValueMap().get(concerto));
        }
        return super.leftOverAV();
    }

    @Override
    public int getSkillCounter() {
        return skillCounter;
    }

    private static class RobinFixedCritPower extends AbstractPower {
        public RobinFixedCritPower() {
            this.setName(this.getClass().getSimpleName());
            lastsForever = true;
        }

        @Override
        public float setFixedCritRate(AbstractCharacter<?> character, AbstractEnemy enemy, List<DamageType> damageTypes, float currentCrit) {
            return 100;
        }

        @Override
        public float setFixedCritDmg(AbstractCharacter<?> character, AbstractEnemy enemy, List<DamageType> damageTypes, float currentCritDmg) {
            return 150;
        }
    }

    private class RobinTalentPower extends PermPower {
        public RobinTalentPower() {
            this.setName(this.getClass().getSimpleName());
            this.setStat(PowerStat.CRIT_DAMAGE, 20);
        }

        @Override
        public void beforeAttack(AttackLogic attack) {
            Robin.this.increaseEnergy(2, TALENT_ENERGY_GAIN);
            Robin.this.allyAttacks.increment();
        }
    }

    private class RobinUltPower extends AbstractPower {
        public RobinUltPower() {
            this.setName(ULT_POWER_NAME);
            lastsForever = true;
        }

        public void updateAtkBuff() {
            float atk = getFinalAttackWithoutConcerto();
            this.setStat(PowerStat.FLAT_ATK, (int) (0.228 * atk) + 200);
        }

        private float getFinalAttackWithoutConcerto() {
            return Robin.this.getFinalAttack() - this.getStat(PowerStat.FLAT_ATK);
        }

        @Override
        public void afterAttack(AttackLogic attack) {
            AbstractEnemy target = Randf.rand(attack.getTargets(), getBattle().getGetRandomEnemyRng());
            if (target == null) {
                return;
            }

            attack.additionalDmg(Robin.this, target, 1.2f);
            Robin.this.concertoHits.increment();
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter<?> character, AbstractEnemy enemy, List<DamageType> damageTypes) {
            if (damageTypes.contains(DamageType.FOLLOW_UP)) {
                return 25;
            }
            return 0;
        }
    }
}
