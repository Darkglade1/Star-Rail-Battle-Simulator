package characters.yunli;

import battleLogic.AbstractEntity;
import characters.goal.UltGoal;

public class YunliEmergancyUlt extends UltGoal<Yunli> {
    public YunliEmergancyUlt(Yunli character) {
        super(character);
    }

    @Override
    public UltGoalResult determineAction() {
        if (!getBattle().isAboutToEnd()) {
            return UltGoalResult.PASS;
        }

        AbstractEntity second = getBattle().getUnit(1);
        if (getBattle().getActionValueMap().get(second) > getBattle().battleLength()) {
            return UltGoalResult.DO;
        }

        return UltGoalResult.PASS;
    }
}
