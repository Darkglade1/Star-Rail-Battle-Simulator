package battleLogic.log.lines.character.yunli;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.Yunli;

public class UseCull implements Loggable {

    private final Yunli yunli;

    public UseCull(Yunli yunli) {
        this.yunli = yunli;
    }

    @Override
    public String asString() {
        return this.yunli.name + " used Cull";
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
