package battleLogic.log;

import battleLogic.BattleParticipant;
import battleLogic.IBattle;
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
import battleLogic.log.lines.entity.RefreshPower;
import battleLogic.log.lines.entity.StackPower;

import java.io.PrintStream;

/**
 * Default implementation of the Logger interface.
 * This logger will print everything to the passed PrintStream
 */
public class DefaultLogger extends Logger implements BattleParticipant {

    private final IBattle battle;
    private final PrintStream out;

    /**
     * Create a new DefaultLogger
     * @param battle The battle this logger is for
     * @param out The PrintStream to print to
     */
    public DefaultLogger(IBattle battle, PrintStream out) {
        this.battle = battle;
        this.out = out;
    }

    /**
     * Create a new DefaultLogger that will print to System.out
     * @param battle The battle this logger is for
     */
    public DefaultLogger(IBattle battle) {
        this(battle, System.out);
    }

    private String prefix() {
        if (this.inBattle()) {
            return String.format("(%.2f AV) - ", getBattle().initialLength() - getBattle().battleLength());
        }
        return "";
    }

    @Override
    public IBattle getBattle() {
        return this.battle;
    }

    @Override
    void log(LogLine logLine) {
        this.out.println(prefix() + logLine.asString());
    }

    @Override
    public void handle(StringLine line) {
        this.out.println(line.asString());
    }
}
