package battleLogic.log;

import battleLogic.IBattle;
import battleLogic.log.lines.battle.StringLine;

import java.io.PrintStream;

public class VoidLogger extends DefaultLogger {
    public VoidLogger(IBattle battle, PrintStream out) {
        super(battle, out);
    }

    public VoidLogger(IBattle battle) {
        super(battle);
    }

    @Override
    protected void log(Loggable loggable) {

    }

    @Override
    public void handle(StringLine line) {
        super.handle(line);
    }
}
