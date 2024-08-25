package relics.ornament;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

public class CelestialDifferentiator extends AbstractRelicSetBonus {
    public CelestialDifferentiator(AbstractCharacter<?> owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public CelestialDifferentiator(AbstractCharacter<?> owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 16, "Celestial Differentiator CD bonus"));
    }

    @Override
    public void onCombatStart() {
        this.owner.addPower(new CelestialDifferentiatorCRBonus());
    }

    public static class CelestialDifferentiatorCRBonus extends PermPower {
        public CelestialDifferentiatorCRBonus() {
            super("Celestial Differentiator CR bonus");
            this.setStat(PowerStat.CRIT_CHANCE, 60);
        }

        @Override
        public void afterAttackFinish(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            this.owner.removePower(this);
        }
    }
}
