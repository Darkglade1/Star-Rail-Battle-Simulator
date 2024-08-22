package battleLogic.log.lines.enemy;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import enemies.AbstractEnemy;

public class SecondAction implements Loggable {

    public final AbstractEnemy enemy;

    public SecondAction(AbstractEnemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public String asString() {
        return this.enemy.name + " takes a second action";
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
