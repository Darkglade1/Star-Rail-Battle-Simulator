package lightcones.preservation;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

public class ThisIsMe extends AbstractLightcone {

    public ThisIsMe(AbstractCharacter<?> owner) {
        super(847, 370, 529, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.DEF_PERCENT, 32, "This Is Me Defense Boost"));
    }

    @Override
    public void onUseUltimate() {
        // I'm not 100% sure this is correct.
        // This effect only applies 1 time per enemy target during each use of the wearer's Ultimate.
        TempPower dmgBoost = TempPower.create(PowerStat.DAMAGE_BONUS, (float) (this.owner.getFinalDefense() * 1.2), 1, "This Is Me Damage Boost");
        dmgBoost.justApplied = false;
        this.owner.addPower(dmgBoost);
    }
}
