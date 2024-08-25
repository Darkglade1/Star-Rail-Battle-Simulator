package relics.relics;

import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

public class KnightOfPurityPalace extends AbstractRelicSetBonus {
    public KnightOfPurityPalace(AbstractCharacter<?> owner) {
        super(owner);
    }
    public KnightOfPurityPalace(AbstractCharacter<?> owner, boolean isFullSet) {
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
