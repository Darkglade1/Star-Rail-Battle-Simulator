package characters.goal.shared;

import characters.AbstractCharacter;
import characters.goal.TurnGoal;

/**
 * A turn goal that will use a skill if the skill counter is <b>below or equal to</b> a certain threshold.
 */
public class SkillCounterTurnGoal<C extends AbstractCharacter<C> & SkillCounterTurnGoal.SkillCounterCharacter> extends TurnGoal<C> {

    private final int threshold;

    /**
     * Creates a new skill counter turn goal. The threshold is set to 0.
     * @param character the character to create the goal for
     */
    public SkillCounterTurnGoal(C character) {
        this(character, 0);
    }

    /**
     * Creates a new skill counter turn goal.
     * @param character the character to create the goal for
     * @param threshold the threshold for the skill counter
     */
    public SkillCounterTurnGoal(C character, int threshold) {
        super(character);
        this.threshold = threshold;
    }

    @Override
    public TurnGoalResult determineAction() {

        if (getBattle().getSkillPoints() > 0 && this.character.getSkillCounter() <= this.threshold) {
            return TurnGoalResult.SKILL;
        }

        return TurnGoalResult.BASIC;
    }

    public interface SkillCounterCharacter {
        int getSkillCounter();
    }
}
