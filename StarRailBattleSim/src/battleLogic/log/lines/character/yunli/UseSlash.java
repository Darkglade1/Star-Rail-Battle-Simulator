package battleLogic.log.lines.character.yunli;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;
import characters.Yunli;

public class UseSlash implements Loggable {

    private final AbstractCharacter yunli;

    public UseSlash(Yunli yunli) {
        this.yunli = yunli;
    }

    @Override
    public String asString() {
        return this.yunli.name + " used Slash";
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
