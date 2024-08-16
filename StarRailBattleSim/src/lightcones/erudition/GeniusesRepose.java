package lightcones.erudition;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

/**
 * Uses 100% uptime, as the current battle sim, doesn't actually kill anyone
 */
public class GeniusesRepose extends AbstractLightcone {

    public GeniusesRepose(AbstractCharacter owner) {
        super(847, 476, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 32, "Geniuses Repose Attack Boost"));
    }

    @Override
    public void onCombatStart() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 48, "Geniuses Repose Crit Damage Boost"));
    }
}
