package battleLogic.log.lines.character;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final HashMap<String, Float> damageBonusMultiConstituents;
    private final HashMap<String, Float> defenseMultiConstituents;
    private final HashMap<String, Float> resMultiConstituents;
    private final HashMap<String, Float> damageVulnMultiConstituents;
    private final HashMap<String, Float> critDmgMultiConstituents;

    public CritHitResult(AbstractCharacter<?> source, AbstractEnemy target, double calculatedDamage, double baseDamage,
                         double dmgMultiplier, double defMultiplier, double resMultiplier,
                         double damageTakenMultiplier, double toughnessMultiplier,
                         double critMultiplier, double expectedCritMultiplier,
                         HashMap<String, Float> damageBonusMultiConstituents,
                         HashMap<String, Float> defenseMultiConstituents,
                         HashMap<String, Float> resMultiConstituents,
                         HashMap<String, Float> damageVulnMultiConstituents,
                         HashMap<String, Float> critDmgMultiConstituents) {
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
        this.damageBonusMultiConstituents = new HashMap<>(damageBonusMultiConstituents);
        this.defenseMultiConstituents = new HashMap<>(defenseMultiConstituents);
        this.resMultiConstituents = new HashMap<>(resMultiConstituents);
        this.damageVulnMultiConstituents = new HashMap<>(damageVulnMultiConstituents);
        this.critDmgMultiConstituents = new HashMap<>(critDmgMultiConstituents);
    }

    @Override
    public String asString() {
        return String.format("%s hit %s for expected crit result of %.3f damage - Base Damage: %.3f, Damage Bonus Multiplier: %s%.3f, Defense Multiplier: %s%.3f, Res Multiplier: %s%.3f, Damage Vuln Multiplier: %s%.3f, Toughness Multiplier: %.3f, Crit Damage Multiplier: %s%.3f Expected Crit Damage Multiplier: %.3f",
                this.source.name, this.target.name, calculatedDamage, baseDamage, parseConstituents(damageBonusMultiConstituents), dmgMultiplier, parseConstituents(defenseMultiConstituents), defMultiplier, parseConstituents(resMultiConstituents), resMultiplier,
                parseConstituents(damageVulnMultiConstituents), damageTakenMultiplier, toughnessMultiplier, parseConstituents(critDmgMultiConstituents), critMultiplier, expectedCritMultiplier);
    }

    private String parseConstituents(HashMap<String, Float> multConstituents) {
        String result = multConstituents.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> {
                    return String.format("[%s, %.1f]", entry.getKey(), entry.getValue());
                })
                .collect(Collectors.joining("+"));
        return String.format("(%s)=", result);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
