package characters.goal.shared;

import characters.AbstractCharacter;
import characters.goal.TurnGoal;

public class SkillCounterTurnGoal<C extends AbstractCharacter<C> & SkillCounterTurnGoal.SkillCounterCharacter> extends TurnGoal<C> {

    public SkillCounterTurnGoal(C character) {
        super(character);
    }

    @Override
    public TurnGoalResult determineAction() {

        if (getBattle().getSkillPoints() > 0 && this.character.getSkillCounter() <= 0) {
            return TurnGoalResult.SKILL;
        }

        return TurnGoalResult.BASIC;
    }

    public interface SkillCounterCharacter {
        int getSkillCounter();
    }
}
