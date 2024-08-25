package characters.goal.shared;

import characters.AbstractCharacter;
import characters.bronya.Bronya;
import characters.robin.Robin;
import characters.goal.UltGoal;

import static characters.goal.UltGoal.UltGoalResult.DO;
import static characters.goal.UltGoal.UltGoalResult.PASS;

public class BroynaRobinUltGoal<C extends AbstractCharacter<C>> extends UltGoal<C> {
    public BroynaRobinUltGoal(C character) {
        super(character);
    }

    @Override
    public UltGoalResult determineAction() {
        if (getBattle().hasCharacter(Bronya.NAME) && getBattle().hasCharacter(Robin.NAME)) {
            if (this.character.hasPower(Bronya.SKILL_POWER_NAME) && this.character.hasPower(Bronya.ULT_POWER_NAME)) {
                return DO;
            }
        }

        return PASS;
    }
}
