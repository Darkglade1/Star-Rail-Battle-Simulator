package lightcones.erudition;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

/**
 * Assumes no ramp up
 */
public class TheSeriousnessOfBreakfast extends AbstractLightcone {

    public TheSeriousnessOfBreakfast(AbstractCharacter owner) {
        super(847, 476, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.DAMAGE_BONUS, 24));
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 8*3));
    }
}