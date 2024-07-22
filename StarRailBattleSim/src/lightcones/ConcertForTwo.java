package lightcones;

import characters.AbstractCharacter;
import powers.PermPower;

public class ConcertForTwo extends AbstractLightcone {

    public ConcertForTwo(AbstractCharacter owner) {
        super(953, 370, 463, owner);
    }

    @Override
    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Concert For Two Stat Bonus";
        statBonus.bonusDefPercent = 32;
        statBonus.bonusDamageBonus = 32;
        owner.addPower(statBonus);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
