package characters.goal.shared;

import characters.AbstractCharacter;
import characters.pela.Pela;
import characters.goal.UltGoal;
import enemies.AbstractEnemy;

public class DontUltMissingDebuffGoal<C extends AbstractCharacter<C>> extends UltGoal<C> {

    private final String other;
    private final String[] debuffNames;

    public DontUltMissingDebuffGoal(C character, String other, String ...debuffName) {
        super(character);
        this.other = other;
        this.debuffNames = debuffName;
    }

    public static <C extends AbstractCharacter<C>> DontUltMissingDebuffGoal<C> pela(C character) {
        return new DontUltMissingDebuffGoal<>(character, Pela.NAME, Pela.ULT_DEBUFF_NAME);
    }

    @Override
    public UltGoalResult determineAction() {
        if (!getBattle().hasCharacter(other)) {
            return UltGoalResult.PASS;
        }

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        for (String debuffName : debuffNames) {
            if (!enemy.hasPower(debuffName)) {
                return UltGoalResult.DONT;
            }
        }

        return UltGoalResult.PASS;
    }
}
