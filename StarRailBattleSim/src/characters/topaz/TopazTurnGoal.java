package characters.topaz;

import characters.goal.TurnGoal;

public class TopazTurnGoal extends TurnGoal<Topaz> {
    public TopazTurnGoal(Topaz character) {
        super(character);
    }

    @Override
    public TurnGoalResult determineAction() {
        if (getBattle().getSkillPoints() <= 3 || character.ultCounter > 0) {
            return TurnGoalResult.BASIC;
        }

        return TurnGoalResult.PASS;
    }
}
