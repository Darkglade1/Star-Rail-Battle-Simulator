package characters.lingsha;

import characters.goal.TurnGoal;

public class LingshaTurnGoal extends TurnGoal<Lingsha> {
    public LingshaTurnGoal(Lingsha character) {
        super(character);
    }

    @Override
    public TurnGoalResult determineAction() {
        if (getBattle().getSkillPoints() >= 4) {
            return TurnGoalResult.SKILL;
        }

        boolean fuYuanGood = this.character.fuYuanCurrentHitCount <= Lingsha.fuYuanMaxHitCount - Lingsha.skillHitCountGain;
        if (getBattle().getSkillPoints() > 0 && fuYuanGood) {
            return TurnGoalResult.SKILL;
        }

        return TurnGoalResult.BASIC;
    }
}
