package battleLogic.log;

import battleLogic.IBattle;
import battleLogic.log.lines.metrics.BattleMetrics;
import battleLogic.log.lines.metrics.EnemyMetrics;
import battleLogic.log.lines.metrics.FinalDmgMetrics;
import battleLogic.log.lines.metrics.PostCombatPlayerMetrics;
import battleLogic.log.lines.metrics.PreCombatPlayerMetrics;

import java.io.PrintStream;

public class MetricLogger extends JsonLogger {

    public MetricLogger(IBattle battle, PrintStream out) {
        super(battle, out);
    }

    public MetricLogger(IBattle battle) {
        super(battle);
    }

    @Override
    protected void log(Loggable loggable) {
    }

    @Override
    public void handle(PreCombatPlayerMetrics preCombatPlayerMetrics) {
        super.log(preCombatPlayerMetrics);
    }

    @Override
    public void handle(PostCombatPlayerMetrics postCombatPlayerMetrics) {
        super.log(postCombatPlayerMetrics);
    }

    @Override
    public void handle(BattleMetrics battleMetrics) {
        super.log(battleMetrics);
    }

    @Override
    public void handle(EnemyMetrics enemyMetrics) {
        super.log(enemyMetrics);
    }

    @Override
    public void handle(FinalDmgMetrics finalDmgMetrics) {
        super.log(finalDmgMetrics);
    }
}
