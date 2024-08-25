package characters.goal.shared;

import characters.AbstractCharacter;
import characters.robin.Robin;
import characters.goal.UltGoal;

public class UltAtEndOfBattle<C extends AbstractCharacter<C>> extends UltGoal<C> {
    public UltAtEndOfBattle(C character) {
        super(character);
    }

    @Override
    public UltGoalResult determineAction() {
        if (!getBattle().isAboutToEnd()) {
            return UltGoalResult.PASS;
        }
        AbstractCharacter<?> robin = getBattle().getCharacter(Robin.NAME);
        if (robin == null) {
            return UltGoalResult.DO;
        }

        if (robin.currentEnergy >= robin.maxEnergy && !this.character.name.equals(Robin.NAME)) {
            return UltGoalResult.DONT;
        }

        return UltGoalResult.DO;
    }
}
