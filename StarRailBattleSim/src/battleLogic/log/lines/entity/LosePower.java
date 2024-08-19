package battleLogic.log.lines.entity;

import battleLogic.AbstractEntity;
import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import powers.AbstractPower;

public class LosePower implements Loggable {

    private final AbstractEntity entity;
    private final AbstractPower power;

    public LosePower(AbstractEntity entity, AbstractPower power) {
        this.entity = entity;
        this.power = power;
    }

    @Override
    public String asString() {
        return this.entity.name + " lost " + this.power.name;
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }

}
