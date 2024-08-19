package battleLogic.log;

import battleLogic.BattleParticipant;
import battleLogic.IBattle;
import battleLogic.log.lines.battle.StringLine;

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
    protected void log(Loggable loggable) {
        this.out.println(prefix() + loggable.asString());
    }

    @Override
    public void handle(StringLine line) {
        this.out.println(line.asString());
    }
}
