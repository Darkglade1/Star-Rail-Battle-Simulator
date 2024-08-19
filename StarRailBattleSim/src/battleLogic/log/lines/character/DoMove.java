package battleLogic.log.lines.character;

import battleLogic.log.LogLine;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

public class DoMove extends LogLine {
    private final AbstractCharacter character;
    private final AbstractCharacter.MoveType moveType;
    private final float initialEnergy;
    private final float currentEnergy;

    public DoMove(AbstractCharacter character, AbstractCharacter.MoveType moveType) {
        this.character = character;
        this.moveType = moveType;
        this.initialEnergy = -1;
        this.currentEnergy = -1;
    }

    public DoMove(AbstractCharacter character, AbstractCharacter.MoveType moveType, float initialEnergy, float currentEnergy) {
        this.character = character;
        this.moveType = moveType;
        this.initialEnergy = initialEnergy;
        this.currentEnergy = currentEnergy;
    }

    @Override
    public String asString() {
        if (this.moveType.equals(AbstractCharacter.MoveType.ULTIMATE)) {
            return String.format("%s used Ultimate (%.3f -> %.3f)", this.character.name, this.initialEnergy, this.currentEnergy);
        }

        return this.character.name + " used " + this.moveType;
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
