package lightcones.erudition;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

public class AfterTheCharmonyFall extends AbstractLightcone {

    public AfterTheCharmonyFall(AbstractCharacter owner) {
        super(847, 476, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.BREAK_EFFECT, 56, "After the Charmony Fall Break Effect Boost"));
    }

    @Override
    public void onUseUltimate() {
        this.owner.addPower(TempPower.create(PowerStat.SPEED_PERCENT, 16, 2, "After the Charmony Fall Speed Boost"));
    }
}
