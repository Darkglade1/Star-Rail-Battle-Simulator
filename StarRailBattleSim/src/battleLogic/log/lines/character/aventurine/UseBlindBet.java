package battleLogic.log.lines.character.aventurine;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;
import characters.Adventurine.Aventurine;

public class UseBlindBet implements Loggable {

    public final AbstractCharacter<?> character;
    public final int initialBlindBet;
    public final int blindBetCounter;

    public UseBlindBet(Aventurine aventurine, int initialBlindBet, int blindBetCounter) {
        this.character = aventurine;
        this.initialBlindBet = initialBlindBet;
        this.blindBetCounter = blindBetCounter;
    }



    @Override
    public String asString() {
        return String.format("%s used Follow Up (%d -> %d)", character.name, initialBlindBet, this.blindBetCounter);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
