package characters.huohuo;

import characters.goal.TurnGoal;

public class HuoHuoTurnGoal extends TurnGoal<Huohuo> {
    public HuoHuoTurnGoal(Huohuo character) {
        super(character);
    }

    @Override
    public TurnGoalResult determineAction() {
        if (getBattle().getSkillPoints() > 0 && this.character.talentCounter <= 0) {
            return TurnGoalResult.SKILL;
        }

        return TurnGoalResult.BASIC;
    }
}
