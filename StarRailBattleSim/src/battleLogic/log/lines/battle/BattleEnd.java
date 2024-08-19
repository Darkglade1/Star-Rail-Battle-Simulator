package battleLogic.log.lines.battle;

import battleLogic.log.LogLine;
import battleLogic.log.Logger;

public class BattleEnd extends LogLine {
    @Override
    public String asString() {
        return "Battle Ended";
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
