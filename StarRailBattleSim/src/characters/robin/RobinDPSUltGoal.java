package characters.robin;

import characters.AbstractCharacter;
import characters.goal.UltGoal;


public class RobinDPSUltGoal extends UltGoal<Robin> {
    public RobinDPSUltGoal(Robin character) {
        super(character);
    }

    @Override
    public UltGoalResult determineAction() {
        AbstractCharacter<?> dpsChar = null;
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            if (character.isDPS) {
                dpsChar = character;
                break;
            }
        }
        if (dpsChar == null) {
            return UltGoalResult.PASS;
        }

        if (getBattle().getActionValueMap().get(dpsChar) < dpsChar.getBaseAV() * 0.7) {
            return UltGoalResult.DONT;
        } else {
            return UltGoalResult.DO;
        }
    }
}
