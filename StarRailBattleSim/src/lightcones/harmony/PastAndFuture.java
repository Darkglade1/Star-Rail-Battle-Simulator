package lightcones.harmony;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PowerStat;
import powers.TempPower;

/**
 * Assumes onSpecificTrigger is called on skill, with the character as next to act

 */
public class PastAndFuture extends AbstractLightcone {

    public PastAndFuture(AbstractCharacter owner) {
        super(953, 423, 397, owner);
    }

    public void onSpecificTrigger(AbstractCharacter character, AbstractEnemy enemy) {
        if (character != null) {
            character.addPower(TempPower.create(PowerStat.DAMAGE_BONUS, 32, 1, "Past and Future Damage Boost"));
        }
    }

    public String toString() {
        return "Past and Future";
    }
}
