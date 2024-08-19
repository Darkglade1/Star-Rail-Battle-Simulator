package battleLogic.log.lines.character;

import battleLogic.log.LogLine;
import battleLogic.log.Logger;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;

public class HitResult extends LogLine {

    private final AbstractCharacter source;
    private final AbstractEnemy target;
    private final double calculatedDamage;
    private final double baseDamage;
    private final double dmgMultiplier;
    private final double defMultiplier;
    private final double resMultiplier;
    private final double damageTakenMultiplier;
    private final double toughnessMultiplier;

    public HitResult(AbstractCharacter source, AbstractEnemy target, double calculatedDamage, double baseDamage,
                      double dmgMultiplier, double defMultiplier, double resMultiplier,
                      double damageTakenMultiplier, double toughnessMultiplier) {
        this.source = source;
        this.target = target;
        this.calculatedDamage = calculatedDamage;
        this.baseDamage = baseDamage;
        this.dmgMultiplier = dmgMultiplier;
        this.defMultiplier = defMultiplier;
        this.resMultiplier = resMultiplier;
        this.damageTakenMultiplier = damageTakenMultiplier;
        this.toughnessMultiplier = toughnessMultiplier;
    }

    @Override
    public String asString() {
        return String.format("%s hit %s for %.3f damage - Base Damage: %.3f, Damage Multiplier: %.3f, Defense Multiplier: %.3f, Res Multiplier: %.3f, Damage Vuln Multiplier: %.3f, Toughness Multiplier: %.3f",
                source.name, target.name, calculatedDamage, baseDamage, dmgMultiplier, defMultiplier, resMultiplier,
                damageTakenMultiplier, toughnessMultiplier);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }

}
