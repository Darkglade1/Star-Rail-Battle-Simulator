package characters.goal.shared;

import characters.AbstractCharacter;
import characters.goal.UltGoal;

public class AlwaysUltGoal<C extends AbstractCharacter<C>> extends UltGoal<C> {
    public AlwaysUltGoal(C character) {
        super(character);
    }

    @Override
    public UltGoalResult determineAction() {
        return UltGoalResult.DO;
    }
}
