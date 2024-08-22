package characters.march;

import characters.goal.TurnGoal;

public class SwordMarchTurnGoal extends TurnGoal<SwordMarch> {
    public SwordMarchTurnGoal(SwordMarch character) {
        super(character);
    }

    @Override
    public TurnGoalResult determineAction() {
        if (getBattle().getSkillPoints() > 0 && this.character.firstMove) {
            this.character.firstMove = false;
            return TurnGoalResult.SKILL;
        } else {
            return TurnGoalResult.BASIC;
        }
    }
}
