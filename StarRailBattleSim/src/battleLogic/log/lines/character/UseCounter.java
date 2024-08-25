package battleLogic.log.lines.character;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

public class UseCounter implements Loggable {

    public final AbstractCharacter<?> character;

    public UseCounter(AbstractCharacter<?> character) {
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
