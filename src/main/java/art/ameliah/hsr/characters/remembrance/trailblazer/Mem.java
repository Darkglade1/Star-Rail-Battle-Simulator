package art.ameliah.hsr.characters.remembrance.trailblazer;

import art.ameliah.hsr.battleLogic.combat.hit.EnemyHit;
import art.ameliah.hsr.battleLogic.combat.result.EnemyHitResult;
import art.ameliah.hsr.battleLogic.combat.result.HitResult;
import art.ameliah.hsr.battleLogic.log.lines.entity.GainCharge;
import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.DamageType;
import art.ameliah.hsr.characters.ElementType;
import art.ameliah.hsr.characters.Path;
import art.ameliah.hsr.characters.goal.shared.target.ally.DpsAllyTargetGoal;
import art.ameliah.hsr.characters.remembrance.Memomaster;
import art.ameliah.hsr.characters.remembrance.Memosprite;
import art.ameliah.hsr.metrics.CounterMetric;
import art.ameliah.hsr.powers.PermPower;
import art.ameliah.hsr.powers.PowerStat;
import art.ameliah.hsr.powers.TempPower;

import java.util.List;

/**
 * Mem currently cannot disappear, "No... Regrets" is not implemented. There is no ally dmg
 */
public class Mem extends Memosprite<Mem, Trailblazer> {
    public static final String NAME = "Mem";

    protected CounterMetric<Integer> charge = metricRegistry.register(CounterMetric.newIntegerCounter("mem-charge"));

    public Mem(Trailblazer trailblazer) {
        super(trailblazer, NAME,
                (int)(640 + 0.8*trailblazer.getFinalHP()),
                130,
                90,
                ElementType.ICE,
                0,
                100,
                Path.REMEMBRANCE);

        this.usesEnergy = false;

        this.registerGoal(0, new DpsAllyTargetGoal<>(this));
    }

    public void increaseCharge(int amount) {
        int initialCharge = this.charge.get();
        this.charge.increase(amount, 100);
        getBattle().addToLog(new GainCharge(this, amount, initialCharge, this.charge.get()));

        if (this.charge.get() == 100) {
            getBattle().AdvanceEntity(this, 100);
        }
    }

    @Override
    public void onCombatStart() {
        this.increaseCharge(50);
        getBattle().registerForPlayers(p -> {
            float cd = 24f + 0.12f * this.getTotalCritDamage();
            p.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, cd, "Friends! Together!"));
        });
    }

    @Override
    public EnemyHitResult hit(EnemyHit hit) {
        super.getMaster().increaseEnergy(hit.energy(), ATTACKED_ENERGY_GAIN);
        return new EnemyHitResult(hit.dmg());
    }

    @Override
    public float getFinalSpeed() {
        return this.baseSpeed;
    }

    @Override
    protected void memoSkill() {
        if (this.charge.get() == 100) {
            this.lemmeHelpYou();
            return;
        }

        this.baddiesTrouble();
    }

    private void lemmeHelpYou() {
        AbstractCharacter<?> ally = this.getAllyTarget();
        if (ally != this) {
            getBattle().AdvanceEntity(ally, 100);
        }
        ally.addPower(new TrueDmgPower());

        if (ally instanceof Memomaster<?> memomaster) {
            Memosprite<?, ?> memosprite = memomaster.getMemo();
            if (memosprite != null) {
                memosprite.addPower(new TrueDmgPower());
            }
        }
        this.charge.set(0);
        this.emit(l -> l.afterUseOnAlly(List.of(ally)));
    }

    private void baddiesTrouble() {
        this.startAttack()
                .handle(DamageType.MEMOSPRITE_DAMAGE,al -> {

                    for (int i = 0; i < 4; i++) {
                        al.logic(getBattle().getRandomEnemy(), (e, dl) ->
                                dl.hit(e, 0.36f, 5));
                    }

                    al.logic(getBattle().getEnemies(), (e, dl) ->
                            dl.hit(e, 0.9f, 10));
                })
                .afterAttackHook(() -> {
                    super.getMaster().increaseEnergy(10, "Mem Baddies! Trouble!");
                    this.increaseCharge(5);
                })
                .execute();
    }

    public class TrueDmgPower extends TempPower {

        public TrueDmgPower() {
            super(3, "TrueDmgPower");

            this.setStat(PowerStat.CRIT_CHANCE, 10);
        }

        @Override
        public void afterDoHit(HitResult hit) {
            float dmg = hit.getHit().finalDmg();
            float mul = 0.28f;

            var character = (AbstractCharacter<?>)this.getOwner();

            if (character.usesEnergy && character.maxEnergy > 100) {
                float excess = character.maxEnergy - 100;
                double increase = Math.floor(excess/10);
                mul += (float) Math.min(increase * 0.02f, 0.2f);
            }

            if (!character.usesEnergy) {
                mul += 0.06f;
            }

            float trueDmg = dmg * mul;

            hit.getHit().getAttackLogic().hitFixed(Mem.this, hit.getEnemy(), trueDmg);
        }
    }
}
