package characters.goal.shared;

import characters.AbstractCharacter;
import characters.goal.TurnGoal;

public class AlwaysSkillGoal<C extends AbstractCharacter<C>> extends TurnGoal<C> {

    private final int threshold;

    public AlwaysSkillGoal(C character) {
        this(character, 0);
    }

    public AlwaysSkillGoal(C character, int threshold) {
        super(character);
        this.threshold = threshold;
    }

    @Override
    public TurnGoalResult determineAction() {
        if (getBattle().getSkillPoints() > this.threshold) {
            return TurnGoalResult.SKILL;
        }

        return TurnGoalResult.BASIC;
    }
}
