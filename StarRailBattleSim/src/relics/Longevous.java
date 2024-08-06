package relics;

import characters.AbstractCharacter;
import powers.PermPower;

public class Longevous extends AbstractRelicSetBonus {
    public Longevous(AbstractCharacter owner) {
        super(owner);
    }
    public Longevous(AbstractCharacter owner, boolean isFullSet) {
        super(owner, isFullSet);
    }

    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Longevous Stat Bonus";
        statBonus.bonusHPPercent = 12;
        owner.addPower(statBonus);
    }

    public String toString() {
        if (isFullSet) {
            return "4 PC Longevous";
        } else {
            return "2 PC Longevous";
        }
    }

}
