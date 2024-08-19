package battleLogic.log.lines.character.lingsha;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;

public class ResetTracker implements Loggable {
    @Override
    public String asString() {
        return "Resetting Lingsha damage tracker due to healing";
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
