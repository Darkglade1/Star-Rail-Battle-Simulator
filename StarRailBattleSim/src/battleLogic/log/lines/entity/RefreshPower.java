package battleLogic.log.lines.entity;

import battleLogic.AbstractEntity;
import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import powers.AbstractPower;

public class RefreshPower implements Loggable {

    private final AbstractEntity entity;
    private final AbstractPower power;
    private final int turns;

    public RefreshPower(AbstractEntity entity, AbstractPower power, int turns) {
        this.entity = entity;
        this.power = power;
        this.turns = turns;
    }

    @Override
    public String asString() {
        return this.entity + " refreshed " + this.power.name + " (" + this.turns + " turn(s))";
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
