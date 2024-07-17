package lightcones;

import characters.AbstractCharacter;
import powers.AbstractPower;
import powers.PermPower;

public class TomorrowJourney extends AbstractLightcone {

    public TomorrowJourney(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    @Override
    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Tomorrow Journey Stat Bonus";
        statBonus.bonusAtkPercent = 32;
        owner.addPower(statBonus);
    }

    public void onUseUltimate() {
        owner.addPower(new TomorrowJourneyDamagePower());
    }

    public String toString() {
        return "For Tomorrow's Journey";
    }

    private static class TomorrowJourneyDamagePower extends AbstractPower {
        public TomorrowJourneyDamagePower() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 1;
            this.bonusDamageBonus = 30;
        }
    }
}
