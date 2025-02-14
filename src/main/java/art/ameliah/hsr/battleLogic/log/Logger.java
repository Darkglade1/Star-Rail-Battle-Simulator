package art.ameliah.hsr.battleLogic.log;

import art.ameliah.hsr.battleLogic.BattleParticipant;
import art.ameliah.hsr.battleLogic.IBattle;
import art.ameliah.hsr.battleLogic.log.lines.StringLine;
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
import art.ameliah.hsr.battleLogic.log.lines.battle.WaveEnd;
import art.ameliah.hsr.battleLogic.log.lines.battle.WaveStart;
import art.ameliah.hsr.battleLogic.log.lines.battle.pf.GainGridPoints;
import art.ameliah.hsr.battleLogic.log.lines.battle.pf.SurgingGritState;
import art.ameliah.hsr.battleLogic.log.lines.character.AttackEnd;
import art.ameliah.hsr.battleLogic.log.lines.character.AttackStart;
import art.ameliah.hsr.battleLogic.log.lines.character.Attacked;
import art.ameliah.hsr.battleLogic.log.lines.character.BreakDamageHitResult;
import art.ameliah.hsr.battleLogic.log.lines.character.CharacterDeath;
import art.ameliah.hsr.battleLogic.log.lines.character.ConcertoEnd;
import art.ameliah.hsr.battleLogic.log.lines.character.DoMove;
import art.ameliah.hsr.battleLogic.log.lines.character.EmergencyHeal;
import art.ameliah.hsr.battleLogic.log.lines.character.ExtraHits;
import art.ameliah.hsr.battleLogic.log.lines.character.FailedHit;
import art.ameliah.hsr.battleLogic.log.lines.character.GainEnergy;
import art.ameliah.hsr.battleLogic.log.lines.character.HealthChange;
import art.ameliah.hsr.battleLogic.log.lines.character.HitInfo;
import art.ameliah.hsr.battleLogic.log.lines.character.HitResultLine;
import art.ameliah.hsr.battleLogic.log.lines.character.LoseEnergy;
import art.ameliah.hsr.battleLogic.log.lines.character.TotalDamage;
import art.ameliah.hsr.battleLogic.log.lines.character.TurnDecision;
import art.ameliah.hsr.battleLogic.log.lines.character.UltDecision;
import art.ameliah.hsr.battleLogic.log.lines.character.UseCounter;
import art.ameliah.hsr.battleLogic.log.lines.character.aventurine.UseBlindBet;
import art.ameliah.hsr.battleLogic.log.lines.character.hanya.BurdenLog;
import art.ameliah.hsr.battleLogic.log.lines.character.lingsha.FuYuanGain;
import art.ameliah.hsr.battleLogic.log.lines.character.lingsha.FuYuanLose;
import art.ameliah.hsr.battleLogic.log.lines.character.lingsha.HitSinceLastHeal;
import art.ameliah.hsr.battleLogic.log.lines.character.lingsha.ResetTracker;
import art.ameliah.hsr.battleLogic.log.lines.character.yunli.UseCull;
import art.ameliah.hsr.battleLogic.log.lines.character.yunli.UseSlash;
import art.ameliah.hsr.battleLogic.log.lines.enemy.EnemyAction;
import art.ameliah.hsr.battleLogic.log.lines.enemy.EnemyDied;
import art.ameliah.hsr.battleLogic.log.lines.enemy.ForcedAttack;
import art.ameliah.hsr.battleLogic.log.lines.enemy.GainedWeakness;
import art.ameliah.hsr.battleLogic.log.lines.enemy.ReduceToughness;
import art.ameliah.hsr.battleLogic.log.lines.enemy.RuanMeiDelay;
import art.ameliah.hsr.battleLogic.log.lines.enemy.SecondAction;
import art.ameliah.hsr.battleLogic.log.lines.enemy.WeaknessBreakRecover;
import art.ameliah.hsr.battleLogic.log.lines.entity.GainCharge;
import art.ameliah.hsr.battleLogic.log.lines.entity.GainPower;
import art.ameliah.hsr.battleLogic.log.lines.entity.LoseCharge;
import art.ameliah.hsr.battleLogic.log.lines.entity.LosePower;
import art.ameliah.hsr.battleLogic.log.lines.entity.RefreshPower;
import art.ameliah.hsr.battleLogic.log.lines.entity.StackPower;
import art.ameliah.hsr.battleLogic.log.lines.metrics.BattleMetrics;
import art.ameliah.hsr.battleLogic.log.lines.metrics.EnemyMetrics;
import art.ameliah.hsr.battleLogic.log.lines.metrics.PostCombatPlayerMetrics;
import art.ameliah.hsr.battleLogic.log.lines.metrics.PreCombatPlayerMetrics;
import lombok.Getter;

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
    @Getter
    protected final List<Loggable> events = new ArrayList<>();
    protected PrintStream out;

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

    public void handle(LoseEnergy loseEnergy) {
        log(loseEnergy);
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

    public void handle(HitResultLine hit) {
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

    public void handle(UltDecision ultDecision) {
        log(ultDecision);
    }

    public void handle(TurnDecision turnDecision) {
        log(turnDecision);
    }

    public void handle(EnemyDied enemyDied) {
        log(enemyDied);
    }

    public void handle(WaveStart waveStart) {
        log(waveStart);
    }

    public void handle(WaveEnd waveEnd) {
        log(waveEnd);
    }

    public void handle(AttackStart attackStart) {
        log(attackStart);
    }

    public void handle(AttackEnd attackEnd) {
        log(attackEnd);
    }

    public void handle(SurgingGritState surgingGritState) {
        log(surgingGritState);
    }

    public void handle(EntityJoinedBattle entityJoinedBattle) {
        log(entityJoinedBattle);
    }

    public void handle(GainGridPoints gainGridPoints) {
        log(gainGridPoints);
    }

    public void handle(TurnEnd turnEnd) {
        log(turnEnd);
    }

    public void handle(GainedWeakness gainedWeakness) {
        log(gainedWeakness);
    }

    public void handle(FailedHit failedHit) {
        log(failedHit);
    }

    public void handle(StringLine line) {
        log(line);
    }

    public void handle(HealthChange line) {
        log(line);
    }

    public void handle(CharacterDeath characterDeath) {
        log(characterDeath);
    }

    public void handle(HitInfo hitInfo) {
        log(hitInfo);
    }

}
