package battleLogic.log.lines.character;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;
import characters.goal.TurnGoal;

public class TurnDecision implements Loggable {

    private final AbstractCharacter<?> character;
    private final Class<?> turnGoal;
    private final TurnGoal.TurnGoalResult result;

    public TurnDecision(AbstractCharacter<?> character, Class<?> turnGoal, TurnGoal.TurnGoalResult result) {
        this.character = character;
        this.turnGoal = turnGoal;
        this.result = result;
    }

    @Override
    public String asString() {
        return character.name + " decided to " + result + " because " + turnGoal.getSimpleName();
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
