package battleLogic.log.lines.character;

import battleLogic.log.LogLine;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

public class EmergencyHeal extends LogLine {

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
