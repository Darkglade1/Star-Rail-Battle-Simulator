package lightcones.abundance;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

/**
 * The healing boost is ignored, as there is no hook for it
 */
public class PostOpConversation extends AbstractLightcone {

    public PostOpConversation(AbstractCharacter<?> owner) {
        super(1058, 423, 331, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ENERGY_REGEN, 16, "Post-Op Conversation Energy Regen Boost"));
    }


}
