package lightcones.abundance;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

/**
 * The extra damage from healing allies is ignored
 */
public class TimeWaitsForNoOne extends AbstractLightcone {

    public TimeWaitsForNoOne(AbstractCharacter owner) {
        super(1270, 476, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.HP_PERCENT, 18));
        this.owner.addPower(PermPower.create(PowerStat.HEALING, 12));
    }
}
