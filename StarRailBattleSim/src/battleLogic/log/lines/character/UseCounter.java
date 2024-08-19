package battleLogic.log.lines.character;

import battleLogic.log.LogLine;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

public class UseCounter extends LogLine {

    private final AbstractCharacter character;

    public UseCounter(AbstractCharacter character) {
        this.character = character;
    }

    @Override
    public String asString() {
        return this.character.name + " used Counter";
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
