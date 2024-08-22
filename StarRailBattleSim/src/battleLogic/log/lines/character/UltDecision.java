package battleLogic.log.lines.character;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;
import characters.goal.UltGoal;

public class UltDecision implements Loggable {

    private final AbstractCharacter<?> character;
    private final Class<?> ultGoal;
    private final UltGoal.UltGoalResult result;

    public UltDecision(AbstractCharacter<?> character, Class<?> ultGoal, UltGoal.UltGoalResult result) {
        this.character = character;
        this.ultGoal = ultGoal;
        this.result = result;
    }

    @Override
    public String asString() {
        return character.name + " decided to " + result + " because " + ultGoal.getSimpleName();
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
