package battleLogic.log.lines.battle;

import battleLogic.AbstractEntity;
import battleLogic.log.Loggable;
import battleLogic.log.Logger;

public class SpeedAdvanceEntity implements Loggable {

    private final AbstractEntity entity;
    private final float from;
    private final float to;

    public SpeedAdvanceEntity(AbstractEntity entity, float from, float to) {
        this.entity = entity;
        this.from = from;
        this.to = to;
    }

    @Override
    public String asString() {
        return String.format("%s advanced by speed increase (%.3f -> %.3f)", this.entity.name, this.from, this.to);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
