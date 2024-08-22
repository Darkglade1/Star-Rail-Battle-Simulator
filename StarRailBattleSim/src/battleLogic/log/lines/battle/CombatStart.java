package battleLogic.log.lines.battle;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;

public class CombatStart implements Loggable {
    @Override
    public String asString() {
        return "Combat Start";
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
