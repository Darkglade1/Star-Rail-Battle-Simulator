package characters.FuXuan;

import characters.goal.TurnGoal;

public class FuXuanTurnGoal extends TurnGoal<FuXuan> {
    public FuXuanTurnGoal(FuXuan character) {
        super(character);
    }

    @Override
    public TurnGoalResult determineAction() {
        if (getBattle().getSkillPoints() > 0 && this.character.skillCounter <= 1) {
            return TurnGoalResult.SKILL;
        }
        return TurnGoalResult.BASIC;
    }
}
