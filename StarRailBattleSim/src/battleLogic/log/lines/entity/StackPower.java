package battleLogic.log.lines.entity;

import battleLogic.AbstractEntity;
import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import powers.AbstractPower;

public class StackPower implements Loggable {

    public final AbstractEntity entity;
    public final AbstractPower power;
    public final int stack;

    public StackPower(AbstractEntity entity, AbstractPower power, int stack) {
        this.entity = entity;
        this.power = power;
        this.stack = stack;
    }

    @Override
    public String asString() {
        return this.entity.name + " stacked " + this.power.name + " to " + this.stack;
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
