package art.ameliah.hsr.characters.harmony.tribbie;

import art.ameliah.hsr.battleLogic.AbstractEntity;
import art.ameliah.hsr.battleLogic.combat.MultiplierStat;
import art.ameliah.hsr.battleLogic.combat.ally.AttackLogic;
import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.DamageType;
import art.ameliah.hsr.characters.ElementType;
import art.ameliah.hsr.characters.Path;
import art.ameliah.hsr.characters.goal.shared.turn.SkillCounterTurnGoal;
import art.ameliah.hsr.characters.goal.shared.ult.AlwaysUltGoal;
import art.ameliah.hsr.characters.goal.shared.ult.UltAtEndOfBattle;
import art.ameliah.hsr.characters.goal.shared.ult.UltMetricGoal;
import art.ameliah.hsr.enemies.AbstractEnemy;
import art.ameliah.hsr.metrics.CounterMetric;
import art.ameliah.hsr.powers.PermPower;
import art.ameliah.hsr.powers.PowerStat;
import art.ameliah.hsr.powers.TempPower;
import art.ameliah.hsr.powers.TracePower;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tribbie extends AbstractCharacter<Tribbie> implements SkillCounterTurnGoal.SkillCounterCharacter {

    public static final String NAME = "Tribbie";

    private final CounterMetric<Integer> numinosityCountdown = metricRegistry.register(CounterMetric.newIntegerCounter(NAME+"::numinosityCountdown"));
    private final CounterMetric<Integer> zoneCountdown = metricRegistry.register(CounterMetric.newIntegerCounter(NAME+"::zoneCountdown"));

    private final Set<AbstractEntity> fuaTrigger = new HashSet<>(3); // 3 other ults

    public Tribbie() {
        super(NAME, 1048, 524, 728, 96, 80, ElementType.QUANTUM, 120, 100, Path.HARMONY);

        this.addPower(new TracePower()
                .setStat(PowerStat.CRIT_DAMAGE, 37.3f)
                .setStat(PowerStat.CRIT_CHANCE, 12)
                .setStat(PowerStat.HP_PERCENT, 10));

        this.registerGoal(100, new UltAtEndOfBattle<>(this));
        this.registerGoal(50, new UltMetricGoal<>(this, this.zoneCountdown, 0));
        this.registerGoal(0, new AlwaysUltGoal<>(this));

        this.registerGoal(0, new SkillCounterTurnGoal<>(this));
    }

    @Override
    public void onCombatStart() {
        getBattle().registerForPlayers(p -> p.addPower(new TribbieTalentListener()));
        this.addPower(new GlassBallWithWings());
        this.increaseEnergy(30, "Pebble at Crossroads?");
    }

    @Override
    public void onTurnStart() {
        if (this.numinosityCountdown.decrease(1, 0) == 0) {
            getBattle().getPlayers().forEach(player -> player.removePower(Numinosity.NAME));
        }

        if (this.zoneCountdown.decrease(1, 0) == 0) {
            getBattle().getEnemies().forEach(e -> e.removePower(TribbieZoneDebuff.NAME));
            getBattle().getPlayers().forEach(p -> p.removePower(TribbieZoneListener.NAME));
        }
    }

    @Override
    public void useTechnique() {
        getBattle().registerForPlayers(player -> player.addPower(new Numinosity()));
        this.numinosityCountdown.set(3);
    }

    @Override
    protected void useBasic() {
        this.doAttack(DamageType.BASIC, dl -> {
            int idx = getBattle().getRandomEnemyIdx();
            dl.logic(idx-1, (e, al) -> al.hit(e, 0.15f, MultiplierStat.HP, 5));
            dl.logic(idx, (e, al) -> al.hit(e, 0.3f, MultiplierStat.HP, 10));
            dl.logic(idx+1, (e, al) -> al.hit(e, 0.15f, MultiplierStat.HP, 5));
        });
    }

    @Override
    protected void useSkill() {
        getBattle().getPlayers().forEach(player -> player.addPower(new Numinosity()));
        this.numinosityCountdown.set(3);
    }

    @Override
    protected void useUltimate() {
        this.doAttack(DamageType.ULTIMATE, dl -> {
            getBattle().getPlayers().forEach(p -> p.addPower(new TribbieZoneListener()));
            getBattle().getEnemies().forEach(e -> e.addPower(new TribbieZoneDebuff()));
            this.zoneCountdown.set(2);
            this.fuaTrigger.clear();

            dl.logic(getBattle().getEnemies(), (e, al) -> {
                al.hit(e, 0.3f, MultiplierStat.HP, 20);
            });
        });
    }

    @Override
    public int getSkillCounter() {
        return this.numinosityCountdown.get();
    }

    public static class GlassBallWithWings extends PermPower {

        public GlassBallWithWings() {
            this.setConditionalStat(PowerStat.FLAT_HP, this::hpBonus);
        }

        public float hpBonus(AbstractCharacter<?> character) {
            double total = getBattle().getPlayers().stream()
                    .filter(c -> c != owner).mapToDouble(AbstractCharacter::getFinalHP)
                    .sum();

            return (float) (total * 0.09f);
        }
    }

    public static class LambOutsideTheWall extends TempPower {
        public static final String NAME = "Lamb Outside the Wall...";

        public LambOutsideTheWall() {
            super(NAME);
            this.maxStacks = 3;
            this.turnDuration = 3;
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, List<DamageType> damageTypes) {
            return 72 * this.stacks;
        }
    }

    public class TribbieTalentListener extends PermPower {

        @Override
        public void afterUseUltimate() {
            if (Tribbie.this.fuaTrigger.contains(getOwner())) {
                return;
            }
            Tribbie.this.fuaTrigger.add(this.getOwner());

            Tribbie.this.doAttack(DamageType.FOLLOW_UP, dl -> {
                Tribbie.this.addPower(new LambOutsideTheWall());

                dl.logic(getBattle().getEnemies(), (e, al) -> {
                    al.hit(e, 0.18f, MultiplierStat.HP, 5);
                });
            });
        }

        @Override
        public void afterAttack(AttackLogic attack) {
            if (attack.getSource() != Tribbie.this) {
                Tribbie.this.increaseEnergy(1.5f*attack.getTargets().size(), "Pebble at Crossroads Attack energy");
            }
        }
    }

    public class TribbieZoneListener extends PermPower {
        public static final String NAME = "TribbieZoneListener";

        public TribbieZoneListener() {
            super(TribbieZoneListener.NAME);
        }

        @Override
        public void afterAttack(AttackLogic attack) {
            if (attack.getSource() != Tribbie.this) {
                Tribbie.this.doAttack(DamageType.ADDITIONAL_DAMAGE, dl -> {
                    int count = attack.getTargets().size();
                    for (int i = 0; i < count; i++) {
                        getBattle().getEnemies().stream()
                                .max(Comparator.comparing(e -> e.getCurrentHp().get()))
                                .ifPresent(target -> {
                                    dl.logic(target, al -> {
                                        al.hit(target, 0.12f, MultiplierStat.HP, 0);
                                    });
                                });
                    }
                });
            }
        }
    }

    public static class TribbieZoneDebuff extends PermPower {
        public static final String NAME = "TribbieZoneDebuff";

        public TribbieZoneDebuff() {
            super(TribbieZoneDebuff.NAME);

            this.type = PowerType.DEBUFF;
        }

        @Override
        public float getConditionalDamageTaken(AbstractCharacter<?> character, AbstractEnemy enemy, List<DamageType> damageTypes) {
            return 30;
        }
    }

    public static class Numinosity extends PermPower {
        public static final String NAME = "Numinosity";

        public Numinosity() {
            super(NAME);
            this.setStat(PowerStat.RES_PEN, 24);
        }
    }
}
