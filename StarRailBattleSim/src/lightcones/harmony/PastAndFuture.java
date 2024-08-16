package lightcones.harmony;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;

/**
 * Assumes onSpecificTrigger is called on skill, with the character as next to act

 */
public class PastAndFuture extends AbstractLightcone {

    public PastAndFuture(AbstractCharacter owner) {
        super(953, 423, 397, owner);
    }

    public void onSpecificTrigger(AbstractCharacter character, AbstractEnemy enemy) {
        if (character != null) {
            character.addPower(new PastAndFutureDamagePower());
        }
    }

    public String toString() {
        return "Past and Future";
    }

    private static class PastAndFutureDamagePower extends AbstractPower {
        public PastAndFutureDamagePower() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 1;
            this.bonusDamageBonus = 32;
        }
    }
}
