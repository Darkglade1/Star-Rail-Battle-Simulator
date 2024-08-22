package battleLogic.log;

import battleLogic.IBattle;

import java.io.PrintStream;

public class VoidLogger extends Logger {
    public VoidLogger(IBattle battle, PrintStream out) {
        super(battle, out);
    }

    public VoidLogger(IBattle battle) {
        super(battle);
    }

    @Override
    protected void log(Loggable loggable) {

    }
}
