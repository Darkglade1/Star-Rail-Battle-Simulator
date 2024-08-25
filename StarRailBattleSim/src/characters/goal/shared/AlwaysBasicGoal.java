package characters.goal.shared;

import characters.AbstractCharacter;
import characters.goal.TurnGoal;

public class AlwaysBasicGoal<C extends AbstractCharacter<C>> extends TurnGoal<C> {

    public AlwaysBasicGoal(C character) {
        super(character);
    }

    @Override
    public TurnGoalResult determineAction() {
        return TurnGoalResult.BASIC;
    }
}
