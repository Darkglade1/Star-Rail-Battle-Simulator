package characters.yunli;

import characters.goal.TurnGoal;

public class YunliTurnGoal extends TurnGoal<Yunli> {
    public YunliTurnGoal(Yunli character) {
        super(character);
    }

    @Override
    public TurnGoalResult determineAction() {
        if (getBattle().getSkillPoints() == 0) {
            return TurnGoalResult.BASIC;
        }

        if (character.firstMove) {
            character.firstMove = false;
            return TurnGoalResult.SKILL;
        }

        if (getBattle().getSkillPoints() > 1) {
            return TurnGoalResult.SKILL;
        }

        return TurnGoalResult.BASIC;
    }
}
