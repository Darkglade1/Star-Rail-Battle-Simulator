package lightcones.harmony;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

public class ForTomorrowsJourney extends AbstractLightcone {

    public ForTomorrowsJourney(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 32, "For Tomorrow's Journey ATK Boost"));
    }

    @Override
    public void onUseUltimate() {
        this.owner.addPower(TempPower.create(PowerStat.DAMAGE_BONUS, 30, 1, "For Tomorrow's Journey Damage Boost"));
    }
}
