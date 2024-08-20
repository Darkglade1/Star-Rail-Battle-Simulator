package battleLogic.log;

import battleLogic.IBattle;
import battleLogic.log.lines.metrics.BattleMetrics;
import battleLogic.log.lines.metrics.EnemyMetrics;
import battleLogic.log.lines.metrics.FinalDmgMetrics;
import battleLogic.log.lines.metrics.PlayerMetrics;

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
    public void handle(PlayerMetrics playerMetrics) {
        super.log(playerMetrics);
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
