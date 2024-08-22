package characters.robin;

import battleLogic.AbstractEntity;
import characters.AbstractCharacter;
import characters.goal.UltGoal;

import java.util.Map;

public class Robin0AVUltGoal extends UltGoal<Robin> {
    public Robin0AVUltGoal(Robin character) {
        super(character);
    }

    @Override
    public UltGoalResult determineAction() {
        for (Map.Entry<AbstractEntity,Float> entry : getBattle().getActionValueMap().entrySet()) {
            if (entry.getKey() instanceof AbstractCharacter && entry.getValue() <= 0) {
                return UltGoalResult.DONT;
            }
        }

        return UltGoalResult.PASS;
    }
}
