package lightcones.hunt;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FinalVictor extends AbstractLightcone {

    public FinalVictor(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 20));

        // I'm really not sure how to do this one
        throw new NotImplementedException();
    }
}
