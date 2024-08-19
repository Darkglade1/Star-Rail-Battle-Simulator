package battleLogic.log.lines.battle;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;

public class StringLine implements Loggable {

    private final String content;

    public StringLine(String content) {
        this.content = content;
    }

    @Override
    public String asString() {
        return this.content;
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
