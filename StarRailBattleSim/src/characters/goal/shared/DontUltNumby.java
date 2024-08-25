package characters.goal.shared;

import battleLogic.AbstractEntity;
import battleLogic.Numby;
import characters.AbstractCharacter;
import characters.topaz.Topaz;
import characters.goal.UltGoal;

import java.util.Map;

public class DontUltNumby<C extends AbstractCharacter<C>> extends UltGoal<C> {

    public DontUltNumby(C character) {
        super(character);
    }

    @Override
    public UltGoalResult determineAction() {
        if (!getBattle().hasCharacter(Topaz.NAME)) {
            return UltGoalResult.PASS;
        }


        for (Map.Entry<AbstractEntity,Float> entry : getBattle().getActionValueMap().entrySet()) {
            if (entry.getKey().name.equals(Numby.NAME)) {
                if (entry.getValue() <= 0) {
                    return UltGoalResult.DONT;
                }
            }
        }

        return UltGoalResult.PASS;
    }
}
