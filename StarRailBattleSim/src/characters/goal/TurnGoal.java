package characters.goal;

import battleLogic.BattleParticipant;
import battleLogic.IBattle;
import characters.AbstractCharacter;

public abstract class TurnGoal<C extends AbstractCharacter<C>> implements BattleParticipant {

    protected final C character;

    public TurnGoal(C character) {
        this.character = character;
    }

    public abstract TurnGoalResult determineAction();

    @Override
    public IBattle getBattle() {
        return this.character.getBattle();
    }

    public enum TurnGoalResult {
        BASIC,
        SKILL,
        PASS
    }
}
