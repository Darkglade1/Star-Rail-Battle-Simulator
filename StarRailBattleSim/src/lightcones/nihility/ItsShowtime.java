package lightcones.nihility;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

/**
 * Don't think there are hooks for inflicting debugs yet
 */
public class ItsShowtime extends AbstractLightcone {

    public ItsShowtime(AbstractCharacter owner) {
        super(1058, 476, 265, owner);

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onCombatStart() {
        if (this.owner.getTotalEHR() > 80) {
            this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 36, "It's Showtime ATK Boost"));
        }
    }
}
