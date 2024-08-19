package battleLogic.log.lines.enemy;

import battleLogic.log.LogLine;
import battleLogic.log.Logger;

public class RuanMeiDelay extends LogLine {
    @Override
    public String asString() {
        return "Ruan Mei Ult Delay Triggered";
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
