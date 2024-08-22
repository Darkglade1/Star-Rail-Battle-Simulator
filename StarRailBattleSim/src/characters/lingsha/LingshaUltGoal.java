package characters.lingsha;

import characters.goal.UltGoal;

import static characters.goal.UltGoal.UltGoalResult.*;

public class LingshaUltGoal extends UltGoal<Lingsha> {
    public LingshaUltGoal(Lingsha character) {
        super(character);
    }

    @Override
    public UltGoalResult determineAction() {
        if (getBattle().getActionValueMap().get(this.character.fuYuan) >= this.character.fuYuan.getBaseAV() * 0.5) {
            return DO;
        }

        return DONT;
    }
}
