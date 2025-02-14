package art.ameliah.hsr.enemies;

import art.ameliah.hsr.battleLogic.AbstractEntity;
import art.ameliah.hsr.battleLogic.BattleEvents;
import art.ameliah.hsr.battleLogic.BattleParticipant;
import art.ameliah.hsr.battleLogic.combat.ally.AttackLogic;
import art.ameliah.hsr.battleLogic.combat.enemy.EnemyAttack;
import art.ameliah.hsr.battleLogic.combat.enemy.EnemyDelayAttack;
import art.ameliah.hsr.battleLogic.combat.hit.Hit;
import art.ameliah.hsr.battleLogic.combat.result.HitResult;
import art.ameliah.hsr.battleLogic.log.lines.enemy.EnemyDied;
import art.ameliah.hsr.battleLogic.log.lines.enemy.ForcedAttack;
import art.ameliah.hsr.battleLogic.log.lines.enemy.ReduceToughness;
import art.ameliah.hsr.battleLogic.log.lines.enemy.RuanMeiDelay;
import art.ameliah.hsr.battleLogic.log.lines.enemy.WeaknessBreakRecover;
import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.ElementType;
import art.ameliah.hsr.characters.harmony.ruanmei.RuanMei;
import art.ameliah.hsr.enemies.action.EnemyActionSequence;
import art.ameliah.hsr.metrics.CounterMetric;
import art.ameliah.hsr.metrics.EnemyActionMetric;
import art.ameliah.hsr.powers.AbstractPower;
import art.ameliah.hsr.powers.PowerStat;
import art.ameliah.hsr.powers.TauntPower;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractEnemy extends AbstractEntity {
    public static final int DEFAULT_RES = 20;

    @Getter
    private final EnemyType type;

    @Getter
    protected final int level;
    protected final int baseHP;
    protected final int baseATK;
    protected final int baseDEF;
    protected final int toughness;

    protected EnemyActionMetric actionMetric = metricRegistry.register(new EnemyActionMetric("enemy-action-metric", "Action metrics"));
    protected CounterMetric<Integer> weaknessBreakMetric = metricRegistry.register(CounterMetric.newIntegerCounter("enemy-weakness-break-metric", "Times weakness broken"));
    protected CounterMetric<Integer> timesAttacked = metricRegistry.register(CounterMetric.newIntegerCounter("enemy-times-attack", "Times attacked"));



    protected final Map<ElementType, Integer> resMap = new HashMap<>();
    protected final Set<ElementType> weaknessMap = new HashSet<>();
    protected final EnemyActionSequence sequence;
    // Moc increases hp this way
    @Setter
    protected int HPMultiplier = 1;
    @Setter
    @Getter
    protected float currentToughness = 0;

    public AbstractEnemy(String name, EnemyType type, int baseHP, int baseATK, int baseDEF, float baseSpeed, int toughness, int level) {
        this.name = name;
        this.type = type;
        this.baseHP = baseHP;
        this.currentHp.set((float) this.baseHP);
        this.baseATK = baseATK;
        this.baseDEF = baseDEF;
        this.baseSpeed = baseSpeed;
        this.toughness = toughness;
        this.currentToughness = toughness;
        this.level = level;
        this.setUpDefaultRes();

        this.sequence = new EnemyActionSequence(this);
    }

    /**
     * Return a copy, edit will not do anything
     *
     * @return copy of the weakness map
     */
    public Collection<ElementType> getWeaknesses() {
        return new HashSet<>(this.weaknessMap);
    }

    public boolean isWeaknessBroken() {
        return this.currentToughness <= 0;
    }

    public float maxToughness() {
        return this.toughness;
    }

    public float maxHp() {
        return this.baseHP;
    }

    public float getBaseSpeed() {
        return baseSpeed;
    }

    public float getFinalAttack() {
        float totalBonusAtkPercent = 0;
        float totalBonusFlatAtk = 0;
        for (AbstractPower power : powerList) {
            totalBonusAtkPercent += power.getStat(PowerStat.ATK_PERCENT);
            totalBonusFlatAtk += power.getStat(PowerStat.FLAT_ATK);
        }
        return (int) ((float) this.baseATK * (1 + totalBonusAtkPercent / 100) + totalBonusFlatAtk);
    }

    public float getFinalDefense() {
        float totalDefenseBonus = 0;
        float totalDefenseReduction = 0;
        for (AbstractPower power : powerList) {
            totalDefenseBonus += power.getStat(PowerStat.DEF_PERCENT);
            totalDefenseReduction += power.getStat(PowerStat.DEFENSE_REDUCTION);
        }
        return totalDefenseBonus - totalDefenseReduction;
    }

    public void setUpDefaultRes() {
        resMap.put(ElementType.FIRE, DEFAULT_RES);
        resMap.put(ElementType.ICE, DEFAULT_RES);
        resMap.put(ElementType.WIND, DEFAULT_RES);
        resMap.put(ElementType.LIGHTNING, DEFAULT_RES);
        resMap.put(ElementType.PHYSICAL, DEFAULT_RES);
        resMap.put(ElementType.QUANTUM, DEFAULT_RES);
        resMap.put(ElementType.IMAGINARY, DEFAULT_RES);
    }

    public void setRes(ElementType elementType, int resValue) {
        resMap.put(elementType, resValue);
    }

    public int getRes(ElementType elementType) {
        return resMap.get(elementType);
    }

    public boolean addWeakness(ElementType elementType) {
        this.setRes(elementType, 0);
        return this.weaknessMap.add(elementType);
    }

    public void removeWeakness(ElementType elementType) {
        this.weaknessMap.remove(elementType);
        this.setRes(elementType, DEFAULT_RES);
    }

    public boolean hasWeakness(ElementType elementType) {
        return this.weaknessMap.contains(elementType);
    }

    protected int getRandomTargetPosition() {
        AbstractPower taunt = getPower(TauntPower.class.getSimpleName());

        if (taunt instanceof TauntPower) {
            AbstractCharacter<?> taunter = ((TauntPower) taunt).taunter;
            getBattle().addToLog(new ForcedAttack(this, taunter));
            for (int i = 0; i < getBattle().playerSize(); i++) {
                if (getBattle().getCharacter(i) == taunter) {
                    return i;
                }
            }

            throw new RuntimeException("Taunter not present in battle.");
        }


        // This code included a correcting from the previous code.
        // It would use the position of the target inside validTargets
        // This would be the wrong position to use later on.
        // We use the new AbstractCharacter#canBeAttacked method to make sure a character can be attacked
        // Currently only Moze.

        double totalWeight = getBattle().getPlayers().stream().mapToDouble(AbstractCharacter::getFinalTauntValue).sum();
        // TODO: Check if it actually checks like this. I'd think that this way the first position gets attacked lot more
        // even with same taunt. As it just gets checked first. Not sure.

        double r = getBattle().getEnemyTargetRng().nextDouble() * totalWeight;
        for (int pos = 0; pos < getBattle().getPlayers().size(); pos++) {
            AbstractCharacter<?> character = getBattle().getPlayers().get(pos);
            if (character.invincible()) {
                // Do not update r value if character cannot be attacked. See todo for concerns.
                continue;
            }

            r -= getBattle().getPlayers().get(pos).getFinalTauntValue();
            if (r <= 0) {
                return pos;
            }
        }

        // Attack last character is no character is found
        return getBattle().getPlayers().size() - 1;
    }

    protected AbstractCharacter<?> getRandomTarget() {
        return getBattle().getPlayers().get(getRandomTargetPosition());
    }

    protected List<AbstractCharacter<?>> getRandomTargets(int amount) {
        List<AbstractCharacter<?>> targets = new ArrayList<>();
        while (targets.size() < amount) {
            AbstractCharacter<?> target = getRandomTarget();
            if (targets.contains(target)) {
                continue;
            }
            targets.add(target);
        }

        return targets;
    }

    protected boolean successfulHit(AbstractCharacter<?> target, double chance) {
        double extraEHR = this.powerList.stream()
                .mapToDouble(p -> p.getStat(PowerStat.EFFECT_HIT))
                .sum();

        double effectRes = target.getTotalEffectRes();
        double realChance = chance / 100 * (1 + extraEHR / 100) * (1 - effectRes / 100);

        return getBattle().getEnemyEHRRng().nextDouble() < realChance;
    }

    // TODO: Implement this correctly with buffs etc.
    public float attackDmg() {
        //return this.getFinalAttack();
        return 0;
    }

    public HitResult hit(Hit hit) {
        if (this.isDead()) {
            return new HitResult(hit, this, 0, 0, false, false);
        }

        final float dmgToDeal = Math.min(hit.finalDmg(), this.currentHp.get());

        this.currentHp.decrease(dmgToDeal);

        if (this.isWeaknessBroken()) {
            return new HitResult(hit, this, dmgToDeal, 0, false, this.isDead());
        }
        return new HitResult(hit, this, dmgToDeal, this.decreaseToughness(hit.finalToughnessReduction(), hit.getSource()), this.isWeaknessBroken(), this.isDead());
    }

    public HitResult hitDirectly(Hit hit) {
        var res = this.hit(hit);
        if (this.currentHp.get() > 0) {
            return res;
        }

        getBattle().addToLog(new EnemyDied(this, "after a direct hit by " + hit.getSource()));
        getBattle().removeEnemy(this);
        this.emit(l -> l.onDeath(hit.getSource()));
        return res;
    }

    protected float decreaseToughness(float amount, BattleParticipant source) {
        float initialToughness = this.currentToughness;
        float toughnessToDeal = Math.min(amount, this.currentToughness);
        if (toughnessToDeal == 0) {
            return 0;
        }

        this.currentToughness -= toughnessToDeal;
        getBattle().addToLog(new ReduceToughness(this, amount, initialToughness, this.currentToughness));

        if (this.currentToughness == 0) {
            this.weaknessBreakMetric.increment();
            getBattle().DelayEntity(this, 25);

            if (source instanceof AbstractCharacter<?> character) {
                float extraDelay = character.elementType.getExtraDelay() * (1 + character.getTotalBreakEffect()/100);
                getBattle().DelayEntity(this, extraDelay);

                //TODO: Further implement missing weakness break mechanics
                // https://honkai-star-rail.fandom.com/wiki/Toughness#Weakness_Break
            }

            getBattle().getPlayers().forEach(p -> p.onWeaknessBreak(this));
            this.emit(l -> l.onWeaknessBreak(source));
        }

        return toughnessToDeal;
    }

    public boolean isDead() {
        return this.currentHp.get() <= 0;
    }

    @Override
    public void onCombatStart() {
        this.currentHp.set((float) (this.baseHP * this.HPMultiplier));
        this.currentToughness = this.toughness;
    }

    /**
     * Weakness bar logic
     */
    @Override
    public void onTurnStart() {
        if (!this.isWeaknessBroken()) {
            return;
        }

        RuanMei.RuanMeiUltDebuff ruanMeiDebuff = (RuanMei.RuanMeiUltDebuff) this.getPower(RuanMei.ULT_DEBUFF_NAME);
        if (ruanMeiDebuff != null && !ruanMeiDebuff.triggered) {
            getBattle().addToLog(new RuanMeiDelay());
            float delay = ruanMeiDebuff.owner.getTotalBreakEffect() * 0.2f + 10;
            getBattle().DelayEntity(this, delay);
            ruanMeiDebuff.triggered = true;
            //getBattle().getHelper().breakDamageHitEnemy(ruanMeiDebuff.owner, this, 0.5f);
            return;
        }

        this.removePower(RuanMei.ULT_DEBUFF_NAME);
        getBattle().addToLog(new WeaknessBreakRecover(this, this.currentToughness, this.toughness));
        this.currentToughness = this.toughness;
    }

    @Override
    public void takeTurn() {
        if (this.isWeaknessBroken()) {
            return;
        }

        this.act();
        this.turnsMetric.increment();
    }

    @Override
    public void afterAttacked(AttackLogic attack) {
        this.timesAttacked.increment();
        if (this.currentHp.get() > 0) {
            return;
        }

        getBattle().addToLog(new EnemyDied(this, attack.getSource()));
        getBattle().removeEnemy(this);
        this.emit(l -> l.onDeath(attack.getSource()));
    }

    public String getMetrics() {
        return String.format("Metrics for %s with %,.3f speed\n%s", this.getName(), this.getBaseSpeed(), this.metricRegistry.representation());
    }

    public EnemyAttack startAttack() {
        return new EnemyAttack(this);
    }

    public void doAttack(Consumer<EnemyDelayAttack> da) {
        this.startAttack().handle(da).execute();
    }

    @Override
    public String toString() {
        return this.getName() + "{" +
                "currentHp=" + currentHp.get() +
                ", currentToughness=" + currentToughness +
                ", hp%=" + currentHp.get() / maxHp() * 100 +
                '}';
    }

    /**
     * The next action returns true, to continue acting (i.e. double action)
     */
    abstract protected void act();
}
