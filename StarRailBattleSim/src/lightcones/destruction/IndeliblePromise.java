package lightcones.destruction;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

public class IndeliblePromise extends AbstractLightcone {
    public IndeliblePromise(AbstractCharacter<?> owner) {
        super(953, 476, 331, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.BREAK_EFFECT, 56, "Indelible Promise Break Boost"));
    }

    @Override
    public void onUseUltimate() {
        this.owner.addPower(TempPower.create(PowerStat.CRIT_CHANCE, 30, 2, "Indelible Promise Crit Boost"));
    }
}
