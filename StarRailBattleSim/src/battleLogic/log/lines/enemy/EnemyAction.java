package battleLogic.log.lines.enemy;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;

public class EnemyAction implements Loggable {

    public final AbstractEnemy enemy;
    public final AbstractCharacter hit;
    public final AbstractEnemy.EnemyAttackType attackType;

    public EnemyAction(AbstractEnemy enemy, AbstractCharacter hit, AbstractEnemy.EnemyAttackType attackType) {
        this.enemy = enemy;
        this.hit = hit;
        this.attackType = attackType;
    }

    @Override
    public String asString() {
        if (this.attackType.equals(AbstractEnemy.EnemyAttackType.AOE)) {
            return this.enemy.name + " used AOE attack";
        }

        return this.enemy.name + " uses " + this.attackType + " attack against " + this.hit.name;
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
