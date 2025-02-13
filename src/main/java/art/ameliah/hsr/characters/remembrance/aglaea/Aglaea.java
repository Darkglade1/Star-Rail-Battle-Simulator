package art.ameliah.hsr.characters.remembrance.aglaea;

import art.ameliah.hsr.battleLogic.BattleEvents;
import art.ameliah.hsr.battleLogic.combat.ally.AttackLogic;
import art.ameliah.hsr.battleLogic.combat.ally.DelayAttack;
import art.ameliah.hsr.battleLogic.log.lines.character.DoMove;
import art.ameliah.hsr.characters.DamageType;
import art.ameliah.hsr.characters.ElementType;
import art.ameliah.hsr.characters.MoveType;
import art.ameliah.hsr.characters.Path;
import art.ameliah.hsr.characters.goal.shared.target.enemy.HighestEnemyTargetGoal;
import art.ameliah.hsr.characters.goal.shared.target.enemy.MiddleEnemyTargetGoal;
import art.ameliah.hsr.characters.goal.shared.turn.AlwaysBasicGoal;
import art.ameliah.hsr.characters.goal.shared.turn.SkillIfNoMemo;
import art.ameliah.hsr.characters.goal.shared.ult.AlwaysUltGoal;
import art.ameliah.hsr.characters.goal.shared.ult.DontUltWhenClose;
import art.ameliah.hsr.characters.goal.shared.ult.UltAtEndOfBattle;
import art.ameliah.hsr.characters.remembrance.Memomaster;
import art.ameliah.hsr.characters.remembrance.Memosprite;
import art.ameliah.hsr.enemies.AbstractEnemy;
import art.ameliah.hsr.metrics.BoolMetric;
import art.ameliah.hsr.powers.PermPower;
import art.ameliah.hsr.powers.PowerStat;
import art.ameliah.hsr.powers.TracePower;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;


public class Aglaea extends Memomaster<Aglaea> {

    public static final String NAME = "Aglaea";

    @Getter
    private final BoolMetric supremeStance = metricRegistry.register("Supreme Stance", BoolMetric.class);

    public final SupremeStanceEntity supremeStanceEntity = new SupremeStanceEntity(this);
    private Garmentmaker garmentmaker;


    public Aglaea() {
        super(NAME, 1242, 699, 485, 102, 80, ElementType.LIGHTNING, 350, 100, Path.REMEMBRANCE);

        this.isDPS = true;

        this.addPower(new TracePower()
                .setStat(PowerStat.LIGHTNING_DMG_BOOST, 22.4f)
                .setStat(PowerStat.CRIT_CHANCE, 12f)
                .setStat(PowerStat.DEF_PERCENT, 12f));

        //this.registerGoal(100, new UltAtEndOfBattle<>(this));
        this.registerGoal(0, new DontUltUnlessCloseToSupremeStance<>(this));
        this.registerGoal(10, new AlwaysUltGoal<>(this));
        this.registerGoal(10, new SkillIfNoMemo<>(this));
        this.registerGoal(0, new AlwaysBasicGoal<>(this));


        // Rosy-Fingered is applied before the attacks happens, so Aglaea doesn't have to target for it
        // This is how I'm reading the Talent for now. Feel free to correct me.
        this.registerGoal(0, new MiddleEnemyTargetGoal<>(this));
    }

    @Override
    public void onCombatStart() {
        this.supremeStanceEntity.setBattle(getBattle());
        if (this.currentEnergy.get() < this.maxEnergy * 0.5f) {
            this.currentEnergy.set(this.maxEnergy * 0.5f);
        }
    }

    @Override
    protected void summonMemo() {
        this.garmentmaker = new Garmentmaker(this);
        garmentmaker.addPower(this.getPower("Traces Stat Bonus"));
        garmentmaker.addPower(this.getPower("RelicStatsBonuses"));
        garmentmaker.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 16, "The Wondrous Banan Amusement Park CD boost"));
        int idx = getBattle().getPlayers().indexOf(this);
        getBattle().addPlayerAt(this.garmentmaker, idx+1);
        getBattle().AdvanceEntity(this.garmentmaker, 100); // The Speeding Summer

        this.emit(l -> l.afterSummon(this.garmentmaker));
    }

    @Override
    public @Nullable Memosprite<?> getMemo() {
        return this.garmentmaker;
    }

    @Override
    public void useTechnique() {
        this.summonMemo();
        this.increaseEnergy(30, "Meteoric Sunder");

        getBattle().getRandomEnemy().addPower(new SeamStitch());

        this.doAttack(DamageType.TECHNIQUE_DAMAGE, dl -> {
            dl.logic(getBattle().getEnemies(), (e, al) -> {
                al.hit(e, 1);
            });
        });
    }

    @Override
    protected void basicSequence() {
        if (!this.supremeStance.value()) {
            super.basicSequence();
            return;
        }

        this.actionMetric.record(MoveType.ENHANCED_BASIC);

        getBattle().addToLog(new DoMove(this, MoveType.ENHANCED_BASIC));
        this.emit(BattleEvents::onUseBasic);
        this.useEnhancedBasic();
        this.emit(BattleEvents::afterUseBasic);
        increaseEnergy(basicEnergyGain, BASIC_ENERGY_GAIN);
    }

    protected void useEnhancedBasic() {
        this.doAttack(DamageType.BASIC, dl -> {
            int idx = this.getTargetIdx(MoveType.ENHANCED_BASIC);
            AbstractEnemy enemy = getBattle().getEnemies().get(idx);
            this.rosyFingered(enemy);

            dl.logic(idx-1, (e, al) -> al.hit(e, 0.9f, 10));
            dl.logic(idx, (e, al) -> al.hit(e, 2, 20));
            dl.logic(idx+1, (e, al) -> al.hit(e, 0.9f, 10));

            this.garmentmaker.doAttack(DamageType.BASIC, dl2 -> {
                dl2.logic(idx-1, (e, al) -> al.hit(e, 0.9f));
                dl2.logic(idx, (e, al) -> al.hit(e, 2));
                dl2.logic(idx+1, (e, al) -> al.hit(e, 0.9f));
            });
        });
    }

    @Override
    protected void useBasic() {
        this.doAttack(DamageType.BASIC, MoveType.BASIC, (e, al) -> {
            this.rosyFingered(e);
            al.hit(e, 1, 10);
        });
    }

    private void rosyFingered(AbstractEnemy target) {
        if (this.garmentmaker == null) {
            return;
        }

        getBattle().getEnemies().forEach(e -> e.removePower(SeamStitch.NAME));
        target.addPower(new SeamStitch());
    }

    @Override
    protected void useSkill() {
        if (this.garmentmaker == null) {
            this.summonMemo();
            getBattle().AdvanceEntity(this.garmentmaker, 100);
            return;
        }

        float amount = 0.5f * this.garmentmaker.getFinalHP();
        this.garmentmaker.getCurrentHp().increase(amount);
    }

    @Override
    protected void useUltimate() {
        if (this.garmentmaker == null) {
            this.summonMemo();
        } else {
            this.garmentmaker.getCurrentHp().set(this.garmentmaker.getFinalHP());
        }

        getBattle().removeEntity(this.supremeStanceEntity);
        getBattle().getActionValueMap().put(this.supremeStanceEntity, this.supremeStanceEntity.getBaseAV());
        this.supremeStance.set(true);

        var power = (Garmentmaker.ABodyBrewedByTears) this.garmentmaker.getPower(Garmentmaker.ABodyBrewedByTears.NAME);
        var dancePower = new DanceDestinedWeaveress();
        dancePower.stacks = power.stacks;
        getBattle().IncreaseSpeed(this, dancePower);

        var majorTracePower = new TheMyopicsDoom(this, this.garmentmaker);
        this.addPower(majorTracePower);
        this.garmentmaker.addPower(majorTracePower);
        getBattle().AdvanceEntity(this, 100);

        increaseEnergy(200, "Test"); // fake energy to ensure ult chaining
    }

    public static class TheMyopicsDoom extends PermPower {
        public static final String NAME = "The Myopic's Doom";

        private final Aglaea aglaea;
        private final Garmentmaker garmentmaker;

        public TheMyopicsDoom(Aglaea aglaea, Garmentmaker garmentmaker) {
            this.aglaea = aglaea;
            this.garmentmaker = garmentmaker;

            this.setConditionalStat(PowerStat.FLAT_ATK, _ -> {
                return 7.2f * this.aglaea.getFinalSpeed() + 3.6f * this.garmentmaker.getFinalSpeed();
            });
        }
    }

    public static class DanceDestinedWeaveress extends PermPower {
        public static final String NAME = "Dance, Destined Weaveress";

        public DanceDestinedWeaveress() {
            super(NAME);
            this.maxStacks = 6;
            this.setConditionalStat(PowerStat.SPEED_PERCENT, _ -> stacks * 15f);
        }
    }

    public class SeamStitch extends PermPower {
        public static final String NAME = "Seam Stitch";

        public SeamStitch() {
            super(NAME);
        }

        @Override
        public void afterAttacked(AttackLogic attack) {
            if (this.getOwner() instanceof AbstractEnemy enemy && attack.getSource() instanceof Aglaea) {
                attack.additionalDmg(Aglaea.this, enemy, 0.3f, ElementType.LIGHTNING);
            }
        }
    }
}
