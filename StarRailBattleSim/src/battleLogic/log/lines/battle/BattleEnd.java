package battleLogic.log.lines.battle;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;

public class BattleEnd implements Loggable {
    @Override
    public String asString() {
        return "Battle Ended";
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
