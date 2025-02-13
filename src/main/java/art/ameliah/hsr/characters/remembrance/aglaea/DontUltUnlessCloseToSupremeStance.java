package art.ameliah.hsr.characters.remembrance.aglaea;

import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.goal.UltGoal;

public class DontUltUnlessCloseToSupremeStance<C extends AbstractCharacter<C>> extends UltGoal<Aglaea> {


    public DontUltUnlessCloseToSupremeStance(Aglaea character) {
        super(character);
    }

    @Override
    public UltGoalResult determineAction() {
        Float av = getBattle().getActionValueMap().get(this.character);
        Float stanceAV = getBattle().getActionValueMap().get(this.character.supremeStanceEntity);
        if (av == null || stanceAV == null) {
            return UltGoalResult.PASS;
        }

        if (av >= stanceAV) {
            return UltGoalResult.DO;
        }

        return UltGoalResult.DONT;
    }
}
