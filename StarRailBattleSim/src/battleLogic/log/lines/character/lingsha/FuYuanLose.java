package battleLogic.log.lines.character.lingsha;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;

public class FuYuanLose implements Loggable {

    public final int amount;
    public final int initalStack;
    public final int fuYuanCurrentHitCount;

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
