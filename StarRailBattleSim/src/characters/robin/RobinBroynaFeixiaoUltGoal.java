package characters.robin;

import characters.AbstractCharacter;
import characters.Feixiao.Feixiao;
import characters.bronya.Bronya;
import characters.goal.UltGoal;


public class RobinBroynaFeixiaoUltGoal extends UltGoal<Robin> {
    public RobinBroynaFeixiaoUltGoal(Robin character) {
        super(character);
    }

    @Override
    public UltGoalResult determineAction() {

        if (!getBattle().hasCharacter(Feixiao.NAME)) {
            return UltGoalResult.PASS;
        }

        AbstractCharacter<?> bronya = getBattle().getCharacter(Bronya.NAME);
        if (bronya != null) {
            if (getBattle().getActionValueMap().get(bronya) < bronya.getBaseAV() * 0.7) {
                return UltGoalResult.DONT;
            }

            return UltGoalResult.PASS;
        }

        AbstractCharacter<?> feixiao = getBattle().getCharacter(Feixiao.NAME);
        if (feixiao != null) {
            if (getBattle().getActionValueMap().get(feixiao) < feixiao.getBaseAV() * 0.7) {
                return UltGoalResult.DONT;
            }

            return UltGoalResult.PASS;
        }

        return UltGoalResult.PASS;
    }
}
