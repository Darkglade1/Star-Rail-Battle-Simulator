package battleLogic.log.lines.battle;

import battleLogic.AbstractEntity;
import battleLogic.log.LogLine;
import battleLogic.log.Logger;

public class DelayEntity extends LogLine {

    private final AbstractEntity entity;
    private final float avDelta;
    private final float from;
    private final float to;

    public DelayEntity(AbstractEntity entity, float avDelta, float from, float to) {
        this.entity = entity;
        this.avDelta = avDelta;
        this.from = from;
        this.to = to;
    }

    @Override
    public String asString() {
        return String.format("%s delayed by %.1f%% (%.3f -> %.3f)", this.entity.name, this.avDelta, this.from, this.to);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }

}
