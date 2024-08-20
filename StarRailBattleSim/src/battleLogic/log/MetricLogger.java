package battleLogic.log;

import battleLogic.IBattle;
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
}
