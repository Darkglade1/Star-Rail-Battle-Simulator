package battleLogic.log.lines.character.hanya;

import battleLogic.log.LogLine;
import battleLogic.log.Logger;

public class BurdenLog extends LogLine {

    private final int hitCount;
    private final int hitsToTrigger;

    public BurdenLog(int hitCount, int hitsToTrigger) {
        this.hitCount = hitCount;
        this.hitsToTrigger = hitsToTrigger;
    }

    @Override
    public String asString() {
        return String.format("Burden is at %d/%d hits", hitCount, hitsToTrigger);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
