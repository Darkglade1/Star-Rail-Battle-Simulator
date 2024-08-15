package lightcones.hunt;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

public class ReturnToDarkness extends AbstractLightcone {

    public ReturnToDarkness(AbstractCharacter owner) {
        super(847, 529, 331, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_CHANCE, 24));
    }
}

