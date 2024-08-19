package battleLogic.log.lines.character;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

public class EmergencyHeal implements Loggable {

    private final AbstractCharacter character;

    public EmergencyHeal(AbstractCharacter character) {
        this.character = character;
    }

    @Override
    public String asString() {
        return "Triggering " + this.character.name + " Emergency Heal";
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
