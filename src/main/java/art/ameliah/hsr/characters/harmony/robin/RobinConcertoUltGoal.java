package art.ameliah.hsr.characters.harmony.robin;

import art.ameliah.hsr.characters.goal.UltGoal;

public class RobinConcertoUltGoal extends UltGoal<Robin> {
    public RobinConcertoUltGoal(Robin character) {
        super(character);
    }

    @Override
    public UltGoalResult determineAction() {
        if (getBattle().getActionValueMap().containsKey(character.concerto)) {
            return UltGoalResult.DONT;
        }

        return UltGoalResult.PASS;
    }
}
