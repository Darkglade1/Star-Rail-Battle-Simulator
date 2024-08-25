package lightcones.nihility;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

/**
 * Missing CR boost
 */
public class IncessantRain extends AbstractLightcone {

    public IncessantRain(AbstractCharacter<?> owner) {
        super(1058, 582, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.EFFECT_HIT, 24, "Incessant Rain Effect Hit Boost"));
    }

    @Override
    public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (!types.contains(AbstractCharacter.DamageType.BASIC)
                && !types.contains(AbstractCharacter.DamageType.SKILL)
                && !types.contains(AbstractCharacter.DamageType.ULTIMATE)) {
            return;
        }

        if (enemiesHit.isEmpty()) {
            return;
        }

        AbstractEnemy target = enemiesHit.get(getBattle().getAetherRng().nextInt(enemiesHit.size()));
        target.addPower(new AetherCode());
    }

    public static class AetherCode extends TempPower {
        public AetherCode() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 1;
            this.setStat(PowerStat.DAMAGE_TAKEN, 12);
        }
    }
}
