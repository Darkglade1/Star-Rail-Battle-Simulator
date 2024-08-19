package battleLogic.log;

import battleLogic.log.lines.battle.AdvanceEntity;
import battleLogic.log.lines.battle.BattleEnd;
import battleLogic.log.lines.battle.DelayEntity;
import battleLogic.log.lines.battle.LeftOverAV;
import battleLogic.log.lines.battle.SpeedAdvanceEntity;
import battleLogic.log.lines.battle.SpeedDelayEntity;
import battleLogic.log.lines.battle.StringLine;
import battleLogic.log.lines.battle.TriggerTechnique;
import battleLogic.log.lines.battle.TurnStart;
import battleLogic.log.lines.battle.UseSkillPoint;
import battleLogic.log.lines.character.Attacked;
import battleLogic.log.lines.character.BreakDamageHitResult;
import battleLogic.log.lines.character.ConcertoEnd;
import battleLogic.log.lines.character.CritHitResult;
import battleLogic.log.lines.character.EmergencyHeal;
import battleLogic.log.lines.character.ExtraHits;
import battleLogic.log.lines.character.aventurine.UseBlindBet;
import battleLogic.log.lines.character.hanya.BurdenLog;
import battleLogic.log.lines.character.lingsha.FuYuanGain;
import battleLogic.log.lines.character.lingsha.FuYuanLose;
import battleLogic.log.lines.character.lingsha.HitSinceLastHeal;
import battleLogic.log.lines.character.lingsha.ResetTracker;
import battleLogic.log.lines.entity.GainCharge;
import battleLogic.log.lines.character.GainEnergy;
import battleLogic.log.lines.character.HitResult;
import battleLogic.log.lines.character.TotalDamage;
import battleLogic.log.lines.character.DoMove;
import battleLogic.log.lines.character.UseCounter;
import battleLogic.log.lines.character.yunli.UseCull;
import battleLogic.log.lines.character.yunli.UseSlash;
import battleLogic.log.lines.enemy.EnemyAction;
import battleLogic.log.lines.enemy.ForcedAttack;
import battleLogic.log.lines.enemy.ReduceToughness;
import battleLogic.log.lines.enemy.RuanMeiDelay;
import battleLogic.log.lines.enemy.SecondAction;
import battleLogic.log.lines.enemy.WeaknessBreakRecover;
import battleLogic.log.lines.entity.GainPower;
import battleLogic.log.lines.entity.LoseCharge;
import battleLogic.log.lines.entity.LosePower;
import battleLogic.log.lines.entity.RefreshPower;
import battleLogic.log.lines.entity.StackPower;

/**
 * Abstract class for logging battle events.
 * Overriding the log method will by default do the same thing for every LogLine.
 * You may override a specific handle method to handle a specific LogLine differently.
 */
public abstract class Logger {

    public final void handle(LogLine logLine) {
        logLine.handle(this);
    }

    abstract void log(LogLine logLine);
    
    public void handle(StringLine line) {
        log(line);
    }

    public void handle(UseSkillPoint useSkillPoint) {
        log(useSkillPoint);
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

    public void handle(HitResult hit) {
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

}
