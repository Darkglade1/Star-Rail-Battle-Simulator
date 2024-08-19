package battleLogic.log.lines.battle;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;

public class LeftOverAV implements Loggable {

    private final float AV;

    public LeftOverAV(float AV) {
        this.AV = AV;
    }

    @Override
    public String asString() {
        return "AV until battle ends: " + this.AV;
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
