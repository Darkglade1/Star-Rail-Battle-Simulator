package lightcones.destruction;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

public class NowhereToRun extends AbstractLightcone {
    public NowhereToRun(AbstractCharacter owner) {
        super(953, 529, 265, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 48, "Nowhere to Run ATK Boost"));
    }
}
