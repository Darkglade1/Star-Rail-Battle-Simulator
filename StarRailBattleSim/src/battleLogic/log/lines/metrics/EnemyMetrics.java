package battleLogic.log.lines.metrics;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import enemies.AbstractEnemy;

public class EnemyMetrics implements Loggable {

    public final AbstractEnemy enemy;
    public final int speed;
    public final int turnsTaken;
    public final int totalAttacks;
    public final int singleTargetAttacks;
    public final int blastAttacks;
    public final int AoEAttacks;
    public final int weaknessBroken;

    public EnemyMetrics(AbstractEnemy enemy) {
        this.enemy = enemy;

        this.speed = enemy.baseSpeed;
        this.turnsTaken = enemy.numTurnsMetric;
        this.totalAttacks = enemy.numAttacksMetric;
        this.singleTargetAttacks = enemy.numSingleTargetMetric;
        this.blastAttacks = enemy.numBlastMetric;
        this.AoEAttacks = enemy.numAoEMetric;
        this.weaknessBroken = enemy.timesBrokenMetric;
    }

    @Override
    public String asString() {
        return String.format("Metrics for %s with %d speed \nTurns taken: %d \nTotal attacks: %d \nSingle-target attacks: %d \nBlast attacks: %d \nAoE attacks: %d \nWeakness Broken: %d", enemy.name, speed, turnsTaken, totalAttacks, singleTargetAttacks, blastAttacks, AoEAttacks, weaknessBroken);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
