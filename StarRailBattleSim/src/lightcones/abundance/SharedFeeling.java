package lightcones.abundance;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

/**
 * Extra energy is ignored as there is no skill hook
 */
public class SharedFeeling extends AbstractLightcone {

    public SharedFeeling( AbstractCharacter owner) {
        super(953, 423, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.HEALING, 20, "Shared Feeling Healing Boost"));
    }
}
