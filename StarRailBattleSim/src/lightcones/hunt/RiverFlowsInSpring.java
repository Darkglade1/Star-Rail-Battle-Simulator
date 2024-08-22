package lightcones.hunt;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

/**
 * Assumes 100% uptime
 */
public class RiverFlowsInSpring extends AbstractLightcone {

    public RiverFlowsInSpring(AbstractCharacter<?> owner) {
        super(847, 476, 397, owner);
    }

    @Override
    public void onCombatStart() {
        this.owner.addPower(PermPower.create(PowerStat.SPEED_PERCENT, 12, "River Flows In Spring Speed Boost"));
        this.owner.addPower(PermPower.create(PowerStat.DAMAGE_BONUS, 24, "River Flows In Spring Damage Boost"));
    }
}
