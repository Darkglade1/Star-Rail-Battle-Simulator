package characters.yunli;

import battleLogic.AbstractEntity;
import characters.goal.UltGoal;

public class YunliEmergancyUlt extends UltGoal<Yunli> {
    public YunliEmergancyUlt(Yunli character) {
        super(character);
    }

    @Override
    public UltGoalResult determineAction() {
        AbstractEntity first = getBattle().getNextUnit(0);
        float firstAV = getBattle().getActionValueMap().get(first);

        AbstractEntity second = getBattle().getNextUnit(1);
        float secondAV = getBattle().getActionValueMap().get(second);

        if (secondAV > getBattle().battleLength() && firstAV < getBattle().battleLength()) {
            return UltGoalResult.DO;
        }

        return UltGoalResult.PASS;
    }
}
