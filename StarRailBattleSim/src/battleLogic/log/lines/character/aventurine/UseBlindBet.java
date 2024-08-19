package battleLogic.log.lines.character.aventurine;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.Aventurine;

public class UseBlindBet implements Loggable {

    private final Aventurine character;
    private final int initialBlindBet;
    private final int blindBetCounter;

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
