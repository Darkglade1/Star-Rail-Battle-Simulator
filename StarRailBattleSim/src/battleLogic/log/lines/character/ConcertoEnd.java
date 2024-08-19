package battleLogic.log.lines.character;

import battleLogic.log.LogLine;
import battleLogic.log.Logger;

public class ConcertoEnd extends LogLine {
    @Override
    public String asString() {
        return "Concerto ends, it's now Robin's turn";
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
