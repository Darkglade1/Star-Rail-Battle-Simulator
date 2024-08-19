package battleLogic.log.lines.battle;

import battleLogic.AbstractEntity;
import battleLogic.log.Loggable;
import battleLogic.log.Logger;

import java.util.HashMap;

public class TurnStart implements Loggable {

    private final AbstractEntity next;
    private final float atAV;
    private final HashMap<AbstractEntity, Float> actionValueMap;

    public TurnStart(AbstractEntity next, float atAV, HashMap<AbstractEntity, Float> actionValueMap) {
        this.next = next;
        this.atAV = atAV;
        this.actionValueMap = new HashMap<>(actionValueMap); // Copy the map to prevent modification
    }

    @Override
    public String asString() {
        return "Next is " + this.next.name + " at " + this.atAV + " action value " + actionValueMap;
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
