package art.ameliah.hsr.battleLogic.log.lines.character;

import art.ameliah.hsr.battleLogic.log.Loggable;
import art.ameliah.hsr.battleLogic.log.Logger;

public record HitInfo(float baseDmg, float critMultiplier, float dmgBoostMultiplier, float defMultiplier,
                      float resMultiplier, float vulnerabilityMultiplier, float brokenMultiplier, float result)
        implements Loggable {

    @Override
    public String asString() {
        return String.format(
                "Damage Calculation: baseDamage (%.2f) * critMultiplier (%.2f) * dmgBoostMultiplier (%.2f) * defMultiplier (%.2f) * resMultiplier (%.2f) * vulnerabilityMultiplier (%.2f) * brokenMultiplier (%.2f) = (%.2f)",
                baseDmg, critMultiplier, dmgBoostMultiplier, defMultiplier, resMultiplier, vulnerabilityMultiplier, brokenMultiplier, result
        );
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
