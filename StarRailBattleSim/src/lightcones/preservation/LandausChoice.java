package lightcones.preservation;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

public class LandausChoice extends AbstractLightcone {

    public LandausChoice(AbstractCharacter<?> owner) {
        super(953, 423, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.TAUNT_VALUE, 200, "Landau's Choice Taunt Value Boost"));
        // Dunno if this work
        this.owner.addPower(PermPower.create(PowerStat.DAMAGE_TAKEN, -24, "Landau's Choice Damage Taken Reduction"));
    }
}
