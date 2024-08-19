package battleLogic.log.lines.character.lingsha;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;

public class FuYuanLose implements Loggable {

    private final int amount;
    private final int initalStack;
    private final int fuYuanCurrentHitCount;

    public FuYuanLose(int amount, int initalStack, int fuYuanCurrentHitCount) {
        this.amount = amount;
        this.initalStack = initalStack;
        this.fuYuanCurrentHitCount = fuYuanCurrentHitCount;
    }


    @Override
    public String asString() {
        return String.format("Fu Yuan hits left decreased by %d (%d -> %d)", amount, initalStack, fuYuanCurrentHitCount);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
