package battleLogic.log.lines.metrics;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import enemies.AbstractEnemy;

import java.util.HashMap;
import java.util.Map;

public class EnemyMetrics implements Loggable {

    private final AbstractEnemy enemy;
    private final Map<String, Object> metrics = new HashMap<>();

    public EnemyMetrics(AbstractEnemy enemy) {
        this.enemy = enemy;

        this.metrics.put("speed", enemy.baseSpeed);
        this.metrics.put("turnsTaken", enemy.numTurnsMetric);
        this.metrics.put("totalAttacks", enemy.numAttacksMetric);
        this.metrics.put("singleTargetAttacks", enemy.numSingleTargetMetric);
        this.metrics.put("blastAttacks", enemy.numBlastMetric);
        this.metrics.put("AoEAttacks", enemy.numAoEMetric);
        this.metrics.put("weaknessBroken", enemy.timesBrokenMetric);
    }

    @Override
    public String asString() {
        return String.format("Metrics for %s with %d speed \nTurns taken: %d \nTotal attacks: %d \nSingle-target attacks: %d \nBlast attacks: %d \nAoE attacks: %d \nWeakness Broken: %d", enemy.name, enemy.baseSpeed, enemy.numTurnsMetric, enemy.numAttacksMetric, enemy.numSingleTargetMetric, enemy.numBlastMetric, enemy.numAoEMetric, enemy.timesBrokenMetric);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
