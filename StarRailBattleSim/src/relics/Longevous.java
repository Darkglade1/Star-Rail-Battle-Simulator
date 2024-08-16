package relics;

import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;

public class Longevous extends AbstractRelicSetBonus {
    public Longevous(AbstractCharacter owner) {
        super(owner);
    }
    public Longevous(AbstractCharacter owner, boolean isFullSet) {
        super(owner, isFullSet);
    }

    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.HP_PERCENT, 12, "Longevous Stat Bonus"));
    }

    public String toString() {
        if (isFullSet) {
            return "4 PC Longevous";
        } else {
            return "2 PC Longevous";
        }
    }

}
