package battleLogic.log.lines.character.lingsha;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

public class HitSinceLastHeal implements Loggable {

    private final AbstractCharacter character;
    private final int timesHit;

    public HitSinceLastHeal(AbstractCharacter character, int timesHit) {
        this.character = character;
        this.timesHit = timesHit;
    }

    @Override
    public String asString() {
        return String.format("%s has been hit %d times since last heal", character.name, timesHit);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
