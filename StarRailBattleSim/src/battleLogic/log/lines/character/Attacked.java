package battleLogic.log.lines.character;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;

public class Attacked implements Loggable {

    public final AbstractEnemy source;
    public final AbstractCharacter target;

    public Attacked(AbstractEnemy source, AbstractCharacter target) {
        this.source = source;
        this.target = target;
    }


    @Override
    public String asString() {
        return source.name + " attacked " + target.name;
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
