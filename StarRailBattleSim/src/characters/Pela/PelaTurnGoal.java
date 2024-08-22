package characters.Pela;

import characters.goal.TurnGoal;

public class PelaTurnGoal extends TurnGoal<Pela> {
    public PelaTurnGoal(Pela character) {
        super(character);
    }

    @Override
    public TurnGoalResult determineAction() {
        if (getBattle().getSkillPoints() > 0 && this.character.firstMove) {
            this.character.firstMove = false;
            return TurnGoalResult.SKILL;
        } else if (getBattle().getSkillPoints() >= 4) {
            return TurnGoalResult.SKILL;
        }

        return TurnGoalResult.BASIC;
    }
}
