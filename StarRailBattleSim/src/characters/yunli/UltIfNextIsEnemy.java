package characters.yunli;

import battleLogic.AbstractEntity;
import characters.goal.UltGoal;
import enemies.AbstractEnemy;

public class UltIfNextIsEnemy extends UltGoal<Yunli> {
    public UltIfNextIsEnemy(Yunli character) {
        super(character);
    }

    @Override
    public UltGoalResult determineAction() {
        AbstractEntity next = getBattle().getNextUnit();
        if (next instanceof AbstractEnemy) {
            return UltGoalResult.DO;
        }

        return UltGoalResult.DONT;
    }
}
