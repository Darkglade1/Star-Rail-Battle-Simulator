package battleLogic.log.lines.character;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;

public class BreakDamageHitResult implements Loggable {

    public final AbstractCharacter source;
    public final AbstractEnemy target;
    public final double calculatedDamage;
    public final double baseDamage;
    public final double breakEffectMultiplier;
    public final double defMultiplier;
    public final double resMultiplier;
    public final double damageTakenMultiplier;
    public final double toughnessMultiplier;

    public BreakDamageHitResult(AbstractCharacter source, AbstractEnemy target, double calculatedDamage, double baseDamage,
                                double breakEffectMultiplier, double defMultiplier, double resMultiplier,
                                double damageTakenMultiplier, double toughnessMultiplier) {
        this.source = source;
        this.target = target;
        this.calculatedDamage = calculatedDamage;
        this.baseDamage = baseDamage;
        this.breakEffectMultiplier = breakEffectMultiplier;
        this.defMultiplier = defMultiplier;
        this.resMultiplier = resMultiplier;
        this.damageTakenMultiplier = damageTakenMultiplier;
        this.toughnessMultiplier = toughnessMultiplier;
    }

    @Override
    public String asString() {
        return String.format("%s hit %s for %.3f Break damage - Base Damage: %.3f, Break Effect Multiplier: %.3f, Defense Multiplier: %.3f, Res Multiplier: %.3f, Damage Vuln Multiplier: %.3f, Toughness Multiplier: %.3f",
                source.name, target.name, calculatedDamage, baseDamage, breakEffectMultiplier, defMultiplier, resMultiplier,
                damageTakenMultiplier, toughnessMultiplier);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }

}
