package battleLogic.log;

import battleLogic.BattleParticipant;
import battleLogic.IBattle;

import java.io.PrintStream;

/**
 * Default implementation of the Logger interface.
 * This logger will print everything to the passed PrintStream
 */
public class DefaultLogger extends Logger {

    public DefaultLogger(IBattle battle, PrintStream out) {
        super(battle, out);
    }

    public DefaultLogger(IBattle battle) {
        super(battle);
    }

    private String prefix() {
        if (this.inBattle()) {
            return String.format("(%.2f AV) - ", getBattle().initialLength() - getBattle().battleLength());
        }
        return "";
    }

    @Override
    protected void log(Loggable loggable) {
        this.out.println(prefix() + loggable.asString());
    }
}
