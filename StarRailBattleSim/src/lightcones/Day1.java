package lightcones;

import characters.AbstractCharacter;
import powers.PermPower;

public class Day1 extends AbstractLightcone {

    public Day1(AbstractCharacter owner) {
        super(953, 370, 463, owner);
    }

    @Override
    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Day 1 Stat Bonus";
        statBonus.bonusDefPercent = 24;
        owner.addPower(statBonus);
    }

    public String toString() {
        return "Day 1 of My New Life";
    }
}
