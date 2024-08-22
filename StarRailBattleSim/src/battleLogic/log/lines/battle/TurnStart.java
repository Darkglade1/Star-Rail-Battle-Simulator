package battleLogic.log.lines.battle;

import battleLogic.AbstractEntity;
import battleLogic.log.Loggable;
import battleLogic.log.Logger;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TurnStart implements Loggable {

    public final AbstractEntity next;
    public final float atAV;
    public final HashMap<AbstractEntity, Float> actionValueMap;

    public TurnStart(AbstractEntity next, float atAV, HashMap<AbstractEntity, Float> actionValueMap) {
        this.next = next;
        this.atAV = atAV;
        this.actionValueMap = new HashMap<>(actionValueMap); // Copy the map to prevent modification
    }

    @Override
    public String asString() {
        return "Next is " + this.next.name + " at " + this.atAV + " action value " +
                actionValueMap.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .map(e -> e.getKey().name + "=" + e.getValue())
                        .collect(Collectors.joining(", ", "{", "}"));
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
