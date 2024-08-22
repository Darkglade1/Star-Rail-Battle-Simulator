package battleLogic.log.lines.battle;

import battleLogic.AbstractEntity;
import battleLogic.log.Loggable;
import battleLogic.log.Logger;

public class AdvanceEntity implements Loggable {

    public final AbstractEntity entity;
    public final float avDelta;
    public final float from;
    public final float to;

    public AdvanceEntity(AbstractEntity entity, float avDelta, float from, float to) {
        this.entity = entity;
        this.avDelta = avDelta;
        this.from = from;
        this.to = to;
    }

    @Override
    public String asString() {
        return String.format("%s advanced by %.1f%% (%.3f -> %.3f)", this.entity.name, this.avDelta, this.from, this.to);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
