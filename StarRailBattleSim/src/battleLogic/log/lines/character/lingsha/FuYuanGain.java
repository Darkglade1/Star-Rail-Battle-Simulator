package battleLogic.log.lines.character.lingsha;

import battleLogic.log.LogLine;
import battleLogic.log.Logger;

public class FuYuanGain extends LogLine {

    private final int amount;
    private final int initalStack;
    private final int fuYuanCurrentHitCount;

    public FuYuanGain(int amount, int initalStack, int fuYuanCurrentHitCount) {
        this.amount = amount;
        this.initalStack = initalStack;
        this.fuYuanCurrentHitCount = fuYuanCurrentHitCount;
    }

    @Override
    public String asString() {
        return String.format("Fu Yuan gained %d hits (%d -> %d)", amount, initalStack, fuYuanCurrentHitCount);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
