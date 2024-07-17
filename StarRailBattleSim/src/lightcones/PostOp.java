package lightcones;

import characters.AbstractCharacter;
import powers.PermPower;

public class PostOp extends AbstractLightcone {

    public PostOp(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    @Override
    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Post Op Stat Bonus";
        statBonus.bonusEnergyRegen = 16;
        owner.addPower(statBonus);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
