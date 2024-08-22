package battleLogic.log.lines.character;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;

public class CritHitResult implements Loggable {

    public final AbstractCharacter<?> source;
    public final AbstractEnemy target;
    public final double calculatedDamage;
    public final double baseDamage;
    public final double dmgMultiplier;
    public final double defMultiplier;
    public final double resMultiplier;
    public final double damageTakenMultiplier;
    public final double toughnessMultiplier;
    public final double critMultiplier;
    public final double expectedCritMultiplier;

    public CritHitResult(AbstractCharacter<?> source, AbstractEnemy target, double calculatedDamage, double baseDamage,
                         double dmgMultiplier, double defMultiplier, double resMultiplier,
                         double damageTakenMultiplier, double toughnessMultiplier,
                         double critMultiplier, double expectedCritMultiplier) {
        this.source = source;
        this.target = target;
        this.calculatedDamage = calculatedDamage;
        this.baseDamage = baseDamage;
        this.dmgMultiplier = dmgMultiplier;
        this.defMultiplier = defMultiplier;
        this.resMultiplier = resMultiplier;
        this.damageTakenMultiplier = damageTakenMultiplier;
        this.toughnessMultiplier = toughnessMultiplier;
        this.critMultiplier = critMultiplier;
        this.expectedCritMultiplier = expectedCritMultiplier;
    }

    @Override
    public String asString() {
        return String.format("%s critically hit %s for %.3f damage - Base Damage: %.3f, Damage Multiplier: %.3f, Defense Multiplier: %.3f, Res Multiplier: %.3f, Damage Vuln Multiplier: %.3f, Toughness Multiplier: %.3f, Crit Damage Multiplier: %.3f Expected Crit Damage Multiplier: %.3f",
                this.source.name, this.target.name, calculatedDamage, baseDamage, dmgMultiplier, defMultiplier, resMultiplier,
                damageTakenMultiplier, toughnessMultiplier, critMultiplier, expectedCritMultiplier);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
