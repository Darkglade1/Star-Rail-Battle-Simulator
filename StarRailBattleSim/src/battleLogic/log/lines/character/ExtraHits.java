package battleLogic.log.lines.character;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

public class ExtraHits implements Loggable {

    public final AbstractCharacter<?> character;
    public final int numExtraHits;

    public ExtraHits(AbstractCharacter<?> character, int numExtraHits) {
        this.character = character;
        this.numExtraHits = numExtraHits;
    }

    @Override
    public String asString() {
        return String.format("%s rolled %d extra hits", character.name, numExtraHits);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
