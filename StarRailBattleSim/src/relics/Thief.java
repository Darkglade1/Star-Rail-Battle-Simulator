package relics;

import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;

public class Thief extends AbstractRelicSetBonus {
    public Thief(AbstractCharacter owner) {
        super(owner);
    }
    public Thief(AbstractCharacter owner, boolean isFullSet) {
        super(owner, isFullSet);
    }

    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Thief Stat Bonus";
        statBonus.setStat(PowerStat.BREAK_EFFECT, 16);
        if (isFullSet) {
            statBonus.increaseStat(PowerStat.BREAK_EFFECT, 16);
        }
        owner.addPower(statBonus);
    }

    public String toString() {
        if (isFullSet) {
            return "4 PC Thief";
        } else {
            return "2 PC Thief";
        }
    }

}
