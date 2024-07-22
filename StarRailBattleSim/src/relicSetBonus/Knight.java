package relicSetBonus;

import characters.AbstractCharacter;
import powers.PermPower;

public class Knight extends AbstractRelicSetBonus {
    public Knight(AbstractCharacter owner) {
        super(owner);
    }
    public Knight(AbstractCharacter owner, boolean isFullSet) {
        super(owner, isFullSet);
    }

    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Knight Stat Bonus";
        statBonus.bonusDefPercent = 15;
        owner.addPower(statBonus);
    }

    public String toString() {
        if (isFullSet) {
            return "4 PC Knight of Purity";
        } else {
            return "2 PC Knight of Purity";
        }
    }

}
