package battleLogic.log.lines.enemy;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;

public class RuanMeiDelay implements Loggable {
    @Override
    public String asString() {
        return "Ruan Mei Ult Delay Triggered";
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
