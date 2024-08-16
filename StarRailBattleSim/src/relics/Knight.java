package relics;

import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;

public class Knight extends AbstractRelicSetBonus {
    public Knight(AbstractCharacter owner) {
        super(owner);
    }
    public Knight(AbstractCharacter owner, boolean isFullSet) {
        super(owner, isFullSet);
    }

    public void onEquip() {
        owner.addPower(PermPower.create(PowerStat.DEF_PERCENT, 15, "Knight Defense Bonus"));
    }

    public String toString() {
        if (isFullSet) {
            return "4 PC Knight of Purity";
        } else {
            return "2 PC Knight of Purity";
        }
    }

}
