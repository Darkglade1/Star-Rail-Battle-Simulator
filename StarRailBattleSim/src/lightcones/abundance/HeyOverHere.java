package lightcones.abundance;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

/**
 * Assuming 100% uptime
 */
public class HeyOverHere extends AbstractLightcone {

    public HeyOverHere(AbstractCharacter owner) {
        super(953, 423, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.HP_PERCENT, 12, "Hey Over Here HP Boost"));
        this.owner.addPower(PermPower.create(PowerStat.HEALING, 28, "Hey Over Here Healing Boost"));
    }
}
