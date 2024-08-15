package lightcones.abundance;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

/**
 * Healing on basic, or skill is ignored as there is no hook
 */
public class WarmthShortensColdNights extends AbstractLightcone {

    public WarmthShortensColdNights(AbstractCharacter owner) {
        super(1058, 370, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.HP_PERCENT, 32));
    }
}
