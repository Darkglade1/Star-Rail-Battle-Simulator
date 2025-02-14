package art.ameliah.hsr.battleLogic;

import art.ameliah.hsr.battleLogic.combat.IAttack;
import art.ameliah.hsr.battleLogic.combat.result.HitResult;
import art.ameliah.hsr.battleLogic.log.DefaultLogger;
import art.ameliah.hsr.battleLogic.log.LogSupplier;
import art.ameliah.hsr.battleLogic.log.Loggable;
import art.ameliah.hsr.battleLogic.log.Logger;
import art.ameliah.hsr.battleLogic.log.lines.battle.AdvanceEntity;
import art.ameliah.hsr.battleLogic.log.lines.battle.BattleEnd;
import art.ameliah.hsr.battleLogic.log.lines.battle.CombatStart;
import art.ameliah.hsr.battleLogic.log.lines.battle.DelayEntity;
import art.ameliah.hsr.battleLogic.log.lines.battle.EntityJoinedBattle;
import art.ameliah.hsr.battleLogic.log.lines.battle.GenerateSkillPoint;
import art.ameliah.hsr.battleLogic.log.lines.battle.LeftOverAV;
import art.ameliah.hsr.battleLogic.log.lines.battle.SpeedAdvanceEntity;
import art.ameliah.hsr.battleLogic.log.lines.battle.SpeedDelayEntity;
import art.ameliah.hsr.battleLogic.log.lines.battle.TriggerTechnique;
import art.ameliah.hsr.battleLogic.log.lines.battle.TurnEnd;
import art.ameliah.hsr.battleLogic.log.lines.battle.TurnStart;
import art.ameliah.hsr.battleLogic.log.lines.battle.UseSkillPoint;
import art.ameliah.hsr.battleLogic.log.lines.metrics.BattleMetrics;
import art.ameliah.hsr.battleLogic.log.lines.metrics.EnemyMetrics;
import art.ameliah.hsr.battleLogic.log.lines.metrics.PostCombatPlayerMetrics;
import art.ameliah.hsr.battleLogic.log.lines.metrics.PreCombatPlayerMetrics;
import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.AbstractSummon;
import art.ameliah.hsr.characters.destruction.yunli.Yunli;
import art.ameliah.hsr.characters.hunt.march.SwordMarch;
import art.ameliah.hsr.enemies.AbstractEnemy;
import art.ameliah.hsr.metrics.CounterMetric;
import art.ameliah.hsr.metrics.DmgContributionMetric;
import art.ameliah.hsr.metrics.MetricRegistry;
import art.ameliah.hsr.powers.AbstractPower;
import art.ameliah.hsr.utils.Comparators;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Battle extends RngProvider implements IBattle {

    private static final int INITIAL_SKILL_POINTS = 3;
    public int MAX_SKILL_POINTS = 5;

    @Getter
    protected MetricRegistry metricRegistry = new MetricRegistry(this);
    protected CounterMetric<Float> totalPlayerDamage = metricRegistry.register(CounterMetric.newFloatCounter("battle-total-player-dmg", "Total player dmg"));
    protected CounterMetric<Integer> totalSkillPointsUsed = metricRegistry.register(CounterMetric.newIntegerCounter("battle-total-sp-used", "Total skill points used"));
    protected CounterMetric<Integer> totalSkillPointsGenerated = metricRegistry.register(CounterMetric.newIntegerCounter("battle-total-sp-generated", "Total skill points generated"));
    protected CounterMetric<Integer> currentSkillPoints = metricRegistry.register(CounterMetric.newIntegerCounter("battle-current-skill-points", "Current skill points", INITIAL_SKILL_POINTS));
    protected CounterMetric<Float> avLeftOver = metricRegistry.register(CounterMetric.newFloatCounter("battle-av-left-over", "AV left over"));
    protected CounterMetric<Float> avUsed = metricRegistry.register(CounterMetric.newFloatCounter("battle-av-used", "AV used"));
    protected DmgContributionMetric dmgContributionMetric = metricRegistry.register(new DmgContributionMetric(this, "battle-dmg-contribution", "Dmg Contributions"));

    protected final Deque<IAttack> queue = new LinkedList<>();


    public float initialBattleLength;
    public AbstractEntity currentUnit;
    public boolean usedEntryTechnique = false;
    public boolean isInCombat = false;
    public boolean lessMetrics = false;
    public int actionForwardPriorityCounter = AbstractEntity.SPEED_PRIORITY_DEFAULT;

    public HashMap<AbstractEntity, Float> actionValueMap = new HashMap<>();

    protected List<AbstractCharacter<?>> playerTeam = new ArrayList<>();
    protected Set<Consumer<AbstractCharacter<?>>> playerListeners = new HashSet<>();

    protected List<AbstractEnemy> enemyTeam = new ArrayList<>();
    protected Set<Consumer<AbstractEnemy>> enemyListeners = new HashSet<>();

    protected boolean activeAttack = false;

    @Getter
    private Logger logger;

    private boolean hasStarted = false;

    public Battle() {
        this.logger = new DefaultLogger(this);
    }

    public Battle(LogSupplier logger) {
        this.logger = logger.get(this);
    }

    public void setLogger(LogSupplier logger) {
        this.logger = logger.get(this);
    }

    @Override
    public String prefix() {
        return String.format("(%.2f AV) - ", this.initialLength() - this.battleLength());
    }

    @Override
    public void addToQueue(IAttack attack, boolean forceFirst) {
        synchronized (this.queue) {
            if (forceFirst) {
                this.queue.offerFirst(attack);
            } else {
                this.queue.offerLast(attack);
            }
        }
    }

    @Override
    public boolean isAttacking() {
        return this.activeAttack;
    }

    @Override
    public void setAttacking(boolean attacking) {
        this.activeAttack = attacking;
    }

    @Override
    public void setPlayerTeam(List<AbstractCharacter<?>> playerTeam) {
        this.playerTeam = playerTeam;
        this.playerTeam.forEach(character -> character.setBattle(this));
    }

    @Override
    public void setEnemyTeam(List<AbstractEnemy> enemyTeam) {
        this.enemyTeam = new ArrayList<>(enemyTeam);
        this.enemyTeam.forEach(enemy -> enemy.setBattle(this));
    }

    @Override
    public AbstractEnemy getRandomEnemy() {
        return this.enemyTeam.get(this.getRandomEnemyIdx());
    }

    @Override
    public int getRandomEnemyIdx() {
        return getRandomEnemyRng.nextInt(this.enemyTeam.size());
    }

    @Override
    public void enemyCallback(int idx, Consumer<AbstractEnemy> callback) {
        if (idx < 0 || idx >= enemyTeam.size()) {
            return;
        }
        AbstractEnemy target = this.enemyTeam.get(idx);
        if (target != null && callback != null) {
            callback.accept(target);
        }
    }

    @Override
    public void playerCallback(int idx, Consumer<AbstractCharacter<?>> callback) {
        if (idx < 0 || idx >= playerTeam.size()) {
            return;
        }

        AbstractCharacter<?> target = this.playerTeam.get(idx);
        if (target != null && callback != null) {
            callback.accept(target);
        }
    }

    @Override
    public final void removeEnemy(AbstractEnemy enemy) {
        int idx = this.enemyTeam.indexOf(enemy);
        if (idx == -1) {
            return;
        }
        this.enemyTeam.remove(idx);
        this.actionValueMap.remove(enemy);

        this.onEnemyRemove(enemy, idx);
        this.doOnceForPlayers(p -> p.emit(l -> l.onEnemyRemove(enemy, idx)));
        this.doOnceForEnemy(e -> e.emit(l -> l.onEnemyRemove(enemy, idx)));
    }

    @Override
    public void addPlayerAt(AbstractCharacter<?> ally, int idx, float initialAA) {
        if (idx >= this.playerTeam.size()) {
            this.playerTeam.add(ally);
        } else {
            this.playerTeam.add(idx, ally);
        }

        ally.setBattle(this);
        this.actionValueMap.put(ally, ally.getBaseAV());
        if (initialAA > 0) {
            this.AdvanceEntity(ally, initialAA);
        }

        addToLog(new EntityJoinedBattle(ally));

        this.playerListeners.forEach(listener -> listener.accept(ally));
        ally.emit(BattleEvents::onCombatStart);
        ally.OnCombatStart();
        this.actionValueMap.keySet().forEach(e -> e.emit(l -> l.onPlayerJoinCombat(ally)));
    }

    @Override
    public void addEnemyAt(AbstractEnemy enemy, int idx, float initialAA) {
        if (idx >= this.enemyTeam.size()) {
            this.enemyTeam.add(enemy);
        } else {
            this.enemyTeam.add(idx, enemy);
        }
        enemy.setBattle(this);

        this.actionValueMap.put(enemy, enemy.getBaseAV());
        if (initialAA > 0) {
            this.AdvanceEntity(enemy, initialAA);
        }

        addToLog(new EntityJoinedBattle(enemy));

        this.enemyListeners.forEach(l -> l.accept(enemy));
        enemy.emit(BattleEvents::onCombatStart);
        this.actionValueMap.keySet().forEach(e -> e.emit(l -> l.onEnemyJoinCombat(enemy)));
    }

    /**
     * Override to implement custom enemy leave actions.
     * I.E. For waves, pf, etc...
     * <p>
     * Default behavior, end battle if everyone dead
     */
    protected void onEnemyRemove(AbstractEnemy enemy, int idx) {
        if (!this.enemyTeam.isEmpty()) {
            return;
        }

        throw new ForceBattleEnd();
    }

    @Override
    public AbstractEntity getCurrentUnit() {
        return this.currentUnit;
    }

    @Override
    public void setCurrentUnit(AbstractEntity entity) {
        this.currentUnit = entity;
        this.actionValueMap.put(entity, 0.0F);
    }

    @Override
    public AbstractEntity getNextUnit(int index) {
        if (index >= this.actionValueMap.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for actionValueMap size " + this.actionValueMap.size());
        }

        return this.actionValueMap
                .entrySet()
                .stream()
                .sorted(Comparators::CompareSpd)
                .toList()
                .get(index)
                .getKey();
    }



    @Override
    public boolean isAboutToEnd() {
        AbstractEntity next = this.getNextUnit(0);
        return actionValueMap.get(next) > this.avLeftOver.get();
    }

    @Override
    public boolean inCombat() {
        return this.isInCombat;
    }

    @Override
    public void updateContribution(BattleParticipant source, HitResult hit) {
        this.dmgContributionMetric.record(source, hit);
    }

    @Override
    public void increaseTotalPlayerDmg(float dmg) {
        this.totalPlayerDamage.increase(dmg);
    }

    @Override
    public float initialLength() {
        return this.initialBattleLength;
    }

    @Override
    public float battleLength() {
        return this.avLeftOver.get();
    }

    @Override
    public int maxEnemiesOnField() {
        return 5;
    }

    @Override
    public void useSkillPoint(AbstractCharacter<?> character, int amount) {
        int cur = this.currentSkillPoints.get();
        this.currentSkillPoints.decrease(amount);
        this.totalSkillPointsUsed.increase(amount);

        addToLog(new UseSkillPoint(character, amount, cur, this.currentSkillPoints.get()));
        if (this.currentSkillPoints.get() < 0) {
            throw new RuntimeException("ERROR - SKILL POINTS WENT NEGATIVE");
        }
    }

    @Override
    public void generateSkillPoint(AbstractCharacter<?> character, int amount) {
        int cur = this.currentSkillPoints.get();
        this.currentSkillPoints.increase(amount, MAX_SKILL_POINTS);
        this.totalSkillPointsGenerated.increase(amount);

        addToLog(new GenerateSkillPoint(character, amount, cur, this.currentSkillPoints.get()));
    }

    @Override
    public void increaseMaxSkillPoints(int maxSkillPoints) {
        MAX_SKILL_POINTS += maxSkillPoints;
    }

    @Override
    public int getSkillPoints() {
        return this.currentSkillPoints.get();
    }

    /**
     * Method to override for extended function if enemies aren't present at their construction
     */
    public void onStart() {
    }

    @Override
    public void Start(float initialLength) {
        if (this.hasStarted) {
            throw new RuntimeException("Battle class is not re-usable");
        }
        this.hasStarted = true;

        this.initialBattleLength = initialLength;
        this.avLeftOver.set(initialLength);

        addToLog(new CombatStart());
        this.playerTeam.forEach(c -> addToLog(new PreCombatPlayerMetrics(c)));
        this.playerTeam.forEach(AbstractCharacter::SetPreCombatPowers);
        this.onStart();

        for (AbstractEnemy enemy : enemyTeam) {
            actionValueMap.put(enemy, enemy.getBaseAV());
            enemy.emit(BattleEvents::onCombatStart);
        }
        isInCombat = true;
        for (AbstractCharacter<?> character : playerTeam) {
            actionValueMap.put(character, character.getBaseAV());
            character.emit(BattleEvents::onCombatStart);
            character.OnCombatStart();
        }

        addToLog(new TriggerTechnique(this.playerTeam
                .stream()
                .filter(c -> c.useTechnique)
                .toList()));
        for (AbstractCharacter<?> character : new ArrayList<>(playerTeam)) {
            if (character.useTechnique) {
                character.useTechnique();
            }
        }

        Yunli yunli = getYunli();
        SwordMarch march = getSwordMarch();

        while (this.avLeftOver.get() > 0 && this.isInCombat) {
            try {
                // This represents on icon on the AV bar in game
                this.battleLoop(yunli, march); // TODO: THIS SHOULD NOT NEED CHARACTERS
            } catch (ForceBattleEnd forceBattleEnd) {
                this.isInCombat = false;
                String reason = forceBattleEnd.getMessage();
                if (reason == null || reason.isEmpty()) {
                    addToLog(new BattleEnd("Forcefully ended"));
                } else {
                    addToLog(new BattleEnd(reason));
                }
                break;
            }
        }
        this.generateMetrics();
    }

    private void battleLoop(Yunli yunli, SwordMarch march) {
        addToLog(new LeftOverAV(this));

        currentUnit = this.getNextUnit(0);
        float nextAV = actionValueMap.get(currentUnit);
        if (nextAV > this.avLeftOver.get()) {
            for (Map.Entry<AbstractEntity, Float> entry : actionValueMap.entrySet()) {
                float newAV = entry.getValue() - this.avLeftOver.get();
                entry.setValue(newAV);
            }
            addToLog(new BattleEnd());
            isInCombat = false;
            return;
        }


        // TODO: Find a way to remove Yunli logic from here
        // Ideally the battle loop does not care about the specifics of the characters
        if (yunli != null && yunli.getCurrentEnergy().get() >= yunli.ultCost) {
            yunli.tryUltimate();
            if (march != null && march.getChargeCount().get() >= SwordMarch.CHARGE_THRESHOLD) {
                currentUnit = march;
                nextAV = actionValueMap.get(currentUnit);
            }
        }

        this.avLeftOver.decrease(nextAV);
        this.avUsed.increase(nextAV);
        for (Map.Entry<AbstractEntity, Float> entry : actionValueMap.entrySet()) {
            float newAV = entry.getValue() - nextAV;
            entry.setValue(newAV);
        }


        this.onTurnStart();
        addToLog(new TurnStart(currentUnit, this.getActionValueUsed(), actionValueMap));
        currentUnit.emit(BattleEvents::onTurnStart);

        // need the AV reset to be after onTurnStart is emitted so Robin's AV is set properly after Concerto ends
        if (!(currentUnit instanceof AbstractSummon<?>)) {
            if (actionValueMap.get(currentUnit) <= 0) {
                actionValueMap.put(currentUnit, currentUnit.getBaseAV());
            }
        }

        currentUnit.takeTurn();

        // These are all the cards that pop up right of the character card
        while (!this.queue.isEmpty()) {
            IAttack attack = this.queue.poll();
            attack.execute();
        }

        // Character keep their buffs until the next card is at 0AV
        currentUnit.emit(BattleEvents::onEndTurn);
        this.addToLog(new TurnEnd(this.currentUnit, this.enemyTeam));
        this.onEndTurn();

        if (yunli != null && yunli.isParrying) {
            yunli.useSlash(getRandomEnemy());
        }

        this.tryUlts();
        this.tryUlts(); // Retry in case people got energy from last ult

        if (currentUnit instanceof AbstractSummon) {
            actionValueMap.put(currentUnit, currentUnit.getBaseAV());
        }
        this.tryUlts();


        while (!this.queue.isEmpty()) {
            IAttack attack = this.queue.poll();
            attack.execute();
        }
    }

    /**
     * Called after an entity's turn ends, before ult checks.
     * I.e. PF enemy add hook
     */
    protected void onEndTurn() {
    }

    protected void onTurnStart() {
    }

    /**
     * Have all players try their ultimates. Not sure why Yunli is filtered out, ask Darkglade
     */
    protected void tryUlts() {
        this.playerTeam.stream()
                .filter(p -> !(p instanceof Yunli))
                .forEach(AbstractCharacter::tryUltimate);
    }

    @Override
    public String metrics() {
        String avPerChar = "Leftover AV: " + this.actionValueMap
                .entrySet()
                .stream()
                .sorted(Comparators::CompareSpd)
                .map(e -> String.format("%s: %.2f", e.getKey().getName(), e.getValue()))
                .collect(Collectors.joining(" | "));
        String leftOverEnergy = "\nLeftover energy: " + this.playerTeam.stream()
                .sorted((p1, p2) -> Float.compare(p1.getCurrentEnergy().get(), p2.getCurrentEnergy().get()))
                .map(e -> String.format("%s: %.2f", e.getName(), e.getCurrentEnergy().get()))
                .collect(Collectors.joining(" | "));
        return this.metricRegistry.representation() + avPerChar + leftOverEnergy;
    }

    private void generateMetrics() {
        this.playerTeam.forEach(p -> addToLog(new PostCombatPlayerMetrics(p)));
        if (!lessMetrics) {
            this.enemyTeam.forEach(e -> addToLog(new EnemyMetrics(e)));
        }
        addToLog(new BattleMetrics(this));
    }

    private Yunli getYunli() {
        for (AbstractCharacter<?> character : playerTeam) {
            if (character instanceof Yunli) {
                return (Yunli) character;
            }
        }
        return null;
    }

    private SwordMarch getSwordMarch() {
        for (AbstractCharacter<?> character : playerTeam) {
            if (character instanceof SwordMarch) {
                return (SwordMarch) character;
            }
        }
        return null;
    }

    @Override
    public boolean getLessMetrics() {
        return this.lessMetrics;
    }

    @Override
    public boolean usedEntryTechnique() {
        return this.usedEntryTechnique;
    }

    @Override
    public void setUsedEntryTechnique(boolean usedEntryTechnique) {
        this.usedEntryTechnique = usedEntryTechnique;
    }

    @Override
    public void removeEntity(AbstractEntity entity) {
        if (entity instanceof AbstractCharacter<?> character) {
            this.playerTeam.remove(character);
        }
        if (entity instanceof AbstractEnemy enemy) {
            this.enemyTeam.remove(enemy);
        }

        this.actionValueMap.remove(entity);

        if (this.playerTeam.isEmpty()) {
            throw new ForceBattleEnd("No players left");
        }
    }

    @Override
    public List<AbstractCharacter<?>> getPlayers() {
        return this.playerTeam;
    }

    @Override
    public boolean hasCharacter(String name) {
        for (AbstractCharacter<?> character : playerTeam) {
            if (character.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void registerForPlayers(Consumer<AbstractCharacter<?>> consumer) {
        this.doOnceForPlayers(consumer);
        this.playerListeners.add(consumer);
    }

    @Override
    public void registerForEnemy(Consumer<AbstractEnemy> consumer) {
        this.doOnceForEnemy(consumer);
        this.enemyListeners.add(consumer);
    }

    @Override
    public void doOnceForPlayers(Consumer<AbstractCharacter<?>> consumer) {
        this.playerTeam.forEach(consumer);
    }

    @Override
    public void doOnceForEnemy(Consumer<AbstractEnemy> consumer) {
        this.enemyTeam.forEach(consumer);
    }

    @Override
    public int playerSize() {
        return this.playerTeam.size();
    }

    @Override
    public int enemiesSize() {
        return this.enemyTeam.size();
    }

    @Override
    public AbstractCharacter<?> getCharacter(String name) {
        for (AbstractCharacter<?> character : playerTeam) {
            if (character.getName().equals(name)) {
                return character;
            }
        }
        return null;
    }

    @Override
    public AbstractCharacter<?> getCharacter(int index) {
        if (index < 0 || index >= playerTeam.size()) {
            return null;
        }
        return playerTeam.get(index);
    }

    @Override
    public void characterCallback(String name, Consumer<AbstractCharacter<?>> callback) {
        AbstractCharacter<?> character = this.getCharacter(name);
        if (character != null && callback != null) {
            callback.accept(character);
        }
    }

    @Override
    public void characterCallback(int idx, Consumer<AbstractCharacter<?>> callback) {
        AbstractCharacter<?> character = this.getCharacter(idx);
        if (character != null && callback != null) {
            callback.accept(character);
        }
    }

    @Override
    public List<AbstractEnemy> getEnemies() {
        return this.enemyTeam;
    }

    @Override
    public AbstractEnemy getMiddleEnemy() {
        AbstractEnemy enemy;
        if (this.enemyTeam.size() >= 3) {
            int middleIndex = this.enemyTeam.size() / 2;
            enemy = this.enemyTeam.get(middleIndex);
        } else {
            enemy = this.enemyTeam.getFirst();
        }
        return enemy;
    }

    @Override
    public AbstractEnemy getEnemyWithHighestHP() {
        AbstractEnemy enemy = null;
        for (AbstractEnemy e : this.enemyTeam) {
            if (enemy == null || e.getCurrentHp().get() > enemy.getCurrentHp().get()) {
                enemy = e;
            }
        }

        return enemy;
    }

    @Override
    public void addToLog(Loggable addition) {
        this.logger.handle(addition);
    }

    @Override
    public HashMap<AbstractEntity, Float> getActionValueMap() {
        return this.actionValueMap;
    }

    @Override
    public int getTotalPlayerDmg() {
        Float dmg = this.totalPlayerDamage.get();
        return dmg == null ? 0 : (int)(float)dmg;
    }

    @Override
    public float getActionValueUsed() {
        return this.initialBattleLength - this.avLeftOver.get();
    }

    @Override
    public int getTotalSkillPointsUsed() {
        return this.totalSkillPointsUsed.get();
    }

    @Override
    public int getTotalSkillPointsGenerated() {
        return this.totalSkillPointsGenerated.get();
    }

    @Override
    public void AdvanceEntity(AbstractEntity entity, float advanceAmount) {
        for (Map.Entry<AbstractEntity, Float> entry : actionValueMap.entrySet()) {
            if (entry.getKey() == entity) {
                float baseAV = entity.getBaseAV();
                float AVDecrease = baseAV * (advanceAmount / 100);
                float originalAV = entry.getValue();
                float newAV = originalAV - AVDecrease;
                if (newAV < 0) {
                    newAV = 0;
                }
                entry.setValue(newAV);
                actionForwardPriorityCounter--;
                entity.speedPriority = actionForwardPriorityCounter;
                addToLog(new AdvanceEntity(entity, advanceAmount, originalAV, newAV));
            }
        }
    }

    @Override
    public void DelayEntity(AbstractEntity entity, float delayAmount) {
        for (Map.Entry<AbstractEntity, Float> entry : actionValueMap.entrySet()) {
            if (entry.getKey() == entity) {
                float baseAV = entity.getBaseAV();
                float AVIncrease = baseAV * (delayAmount / 100);
                float originalAV = entry.getValue();
                float newAV = originalAV + AVIncrease;
                entry.setValue(newAV);
                addToLog(new DelayEntity(entity, delayAmount, originalAV, newAV));
            }
        }
    }

    @Override
    public void IncreaseSpeed(AbstractEntity entity, AbstractPower speedPower) {
        float baseAV = entity.getBaseAV();
        Float currAV = actionValueMap.get(entity);
        if (currAV == null) {
            entity.addPower(speedPower);
            return;
        }
        float percentToNextAction = (baseAV - currAV) / baseAV;

        entity.addPower(speedPower);
        float newBaseAV = entity.getBaseAV();
        float newCurrAV = newBaseAV - (percentToNextAction * newBaseAV);
        actionValueMap.put(entity, newCurrAV);

        addToLog(new SpeedAdvanceEntity(entity, currAV, newCurrAV));
    }

    @Override
    public void DecreaseSpeed(AbstractEntity entity, AbstractPower speedPower) {
        float baseAV = entity.getBaseAV();
        Float currAV = actionValueMap.get(entity);
        if (currAV == null) {
            entity.removePower(speedPower);
            return;
        }
        float percentToNextAction = (baseAV - currAV) / baseAV;

        entity.removePower(speedPower);
        float newBaseAV = entity.getBaseAV();
        float newCurrAV = newBaseAV - (percentToNextAction * newBaseAV);
        actionValueMap.put(entity, newCurrAV);

        addToLog(new SpeedDelayEntity(entity, currAV, newCurrAV));
    }



    public static class ForceBattleEnd extends RuntimeException {
        public ForceBattleEnd() {
            super();
        }

        public ForceBattleEnd(String message) {
            super(message);
        }
    }
}
