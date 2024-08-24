package characters.lingsha;

import characters.goal.TurnGoal;

public class LingshaTurnGoal extends TurnGoal<Lingsha> {
    public LingshaTurnGoal(Lingsha character) {
        super(character);
    }

    @Override
    public TurnGoalResult determineAction() {
        boolean fuYuanGood = this.character.fuYuanCurrentHitCount <= Lingsha.fuYuanMaxHitCount - Lingsha.skillHitCountGain;
        if (getBattle().getSkillPoints() > 0 && fuYuanGood) {
            return TurnGoalResult.SKILL;
        }

        return TurnGoalResult.BASIC;
    }
}
