package lightcones.erudition;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

public class TodayIsAnotherPeacefulDay extends AbstractLightcone {

    public TodayIsAnotherPeacefulDay(AbstractCharacter owner) {
        super(847, 529, 331, owner);
    }

    @Override
    public void onCombatStart() {
        float boost = (float) 0.4 * Math.min(160, this.owner.maxEnergy);
        this.owner.addPower(PermPower.create(PowerStat.DAMAGE_BONUS, boost));
    }
}
