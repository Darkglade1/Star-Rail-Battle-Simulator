package battleLogic.log;

import battleLogic.BattleParticipant;
import battleLogic.IBattle;
import battleLogic.log.lines.battle.*;
import battleLogic.log.lines.character.*;
import battleLogic.log.lines.character.aventurine.UseBlindBet;
import battleLogic.log.lines.character.hanya.BurdenLog;
import battleLogic.log.lines.character.lingsha.FuYuanGain;
import battleLogic.log.lines.character.lingsha.FuYuanLose;
import battleLogic.log.lines.character.lingsha.HitSinceLastHeal;
import battleLogic.log.lines.character.lingsha.ResetTracker;
import battleLogic.log.lines.character.yunli.UseCull;
import battleLogic.log.lines.character.yunli.UseSlash;
import battleLogic.log.lines.enemy.*;
import battleLogic.log.lines.entity.*;
import battleLogic.log.lines.metrics.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for logging battle events.
 * Overriding the log method will by default do the same thing for every LogLine.
 * You may override a specific handle method to handle a specific LogLine differently.
 */
public abstract class Logger implements BattleParticipant {

    protected final IBattle battle;
    protected final PrintStream out;
    protected List<Loggable> events = new ArrayList<>();

    public Logger(IBattle battle, PrintStream out) {
        this.battle = battle;
        this.out = out;
    }

    public Logger(IBattle battle) {
        this(battle, System.out);
    }

    @Override
    public IBattle getBattle() {
        return battle;
    }

    public List<Loggable> getEvents() {
        return events;
    }

    public final void handle(Loggable loggable) {
        events.add(loggable);
        loggable.handle(this);
    }

    protected abstract void log(Loggable loggable);

    public void handle(UseSkillPoint useSkillPoint) {
        log(useSkillPoint);
    }

    public void handle(GenerateSkillPoint generateSkillPoint) {
        log(generateSkillPoint);
    }

    public void handle(TriggerTechnique triggerTechnique) {
        log(triggerTechnique);
    }

    public void handle(BattleEnd battleEnd) {
        log(battleEnd);
    }

    public void handle(LeftOverAV leftOverAV) {
        log(leftOverAV);
    }

    public void handle(TurnStart turnStart) {
        log(turnStart);
    }

    public void handle(ConcertoEnd concertoEnd) {
        log(concertoEnd);
    }

    public void handle(AdvanceEntity advanceEntity) {
        log(advanceEntity);
    }

    public void handle(DelayEntity delayEntity) {
        log(delayEntity);
    }

    public void handle(SpeedAdvanceEntity speedAdvanceEntity) {
        log(speedAdvanceEntity);
    }

    public void handle(SpeedDelayEntity speedDelayEntity) {
        log(speedDelayEntity);
    }

    public void handle(StackPower power) {
        log(power);
    }

    public void handle(RefreshPower power) {
        log(power);
    }

    public void handle(GainPower power) {
        log(power);
    }

    public void handle(LosePower power) {
        log(power);
    }

    public void handle(DoMove doMove) {
        log(doMove);
    }

    public void handle(GainEnergy gainEnergy) {
        log(gainEnergy);
    }

    public void handle(EnemyAction enemyAction) {
        log(enemyAction);
    }

    public void handle(SecondAction secondAction) {
        log(secondAction);
    }

    public void handle(ForcedAttack forcedAttack) {
        log(forcedAttack);
    }

    public void handle(ReduceToughness reduceToughness) {
        log(reduceToughness);
    }

    public void handle(WeaknessBreakRecover weaknessBreakRecover) {
        log(weaknessBreakRecover);
    }

    public void handle(RuanMeiDelay ruanMeiDelay) {
        log(ruanMeiDelay);
    }

    public void handle(CritHitResult hit) {
        log(hit);
    }

    public void handle(BreakDamageHitResult hit) {
        log(hit);
    }

    public void handle(TotalDamage totalDamage) {
        log(totalDamage);
    }

    public void handle(Attacked attack) {
        log(attack);
    }

    public void handle(UseCounter counter) {
        log(counter);
    }

    public void handle(UseCull cull) {
        log(cull);
    }

    public void handle(UseSlash slash) {
        log(slash);
    }

    public void handle(GainCharge gainCharge) {
        log(gainCharge);
    }

    public void handle(ExtraHits extraHits) {
        log(extraHits);
    }

    public void handle(LoseCharge loseCharge) {
        log(loseCharge);
    }

    public void handle(FuYuanGain fuYuanGain) {
        log(fuYuanGain);
    }

    public void handle(FuYuanLose fuYuanLose) {
        log(fuYuanLose);
    }

    public void handle(ResetTracker resetTracker) {
        log(resetTracker);
    }
    
    public void handle(EmergencyHeal emergencyHeal) {
        log(emergencyHeal);
    }

    public void handle(HitSinceLastHeal hitSinceLastHeal) {
        log(hitSinceLastHeal);
    }

    public void handle(BurdenLog burdenLog) {
        log(burdenLog);
    }

    public void handle(UseBlindBet blindBet) {
        log(blindBet);
    }

    public void handle(PreCombatPlayerMetrics preCombatPlayerMetrics) {
        log(preCombatPlayerMetrics);
    }

    public void handle(PostCombatPlayerMetrics postCombatPlayerMetrics) {
        log(postCombatPlayerMetrics);
    }

    public void handle(EnemyMetrics enemyMetrics) {
        log(enemyMetrics);
    }

    public void handle(BattleMetrics battleMetrics) {
        log(battleMetrics);
    }

    public void handle(CombatStart combatStart) {
        log(combatStart);
    }

    public void handle(FinalDmgMetrics finalDmgMetrics) {
        log(finalDmgMetrics);
    }

    public void handle(UltDecision ultDecision) {
        log(ultDecision);
    }

    public void handle(TurnDecision turnDecision) {
        log(turnDecision);
    }

}
