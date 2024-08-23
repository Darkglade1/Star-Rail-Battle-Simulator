package characters.adventurine;

import characters.AbstractCharacter;
import characters.goal.TurnGoal;

public class AventurineTurnGoal extends TurnGoal<Aventurine> {
    public AventurineTurnGoal(Aventurine character) {
        super(character);
    }

    @Override
    public TurnGoalResult determineAction() {
        if (!this.character.SPNeutral) {
            return TurnGoalResult.BASIC;
        }

        if (getBattle().getSkillPoints() == 0) {
            return TurnGoalResult.BASIC;
        }

        if (this.character.lastMove(AbstractCharacter.MoveType.BASIC) || this.character.firstMove) {
            this.character.firstMove = false;
            return TurnGoalResult.SKILL;
        }

        return TurnGoalResult.BASIC;
    }
}
