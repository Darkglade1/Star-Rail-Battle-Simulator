package lightcones.nihility;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;

import java.util.ArrayList;

public class GoodNightAndSleepWell extends AbstractLightcone {

    public GoodNightAndSleepWell(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    public static class GoodNightAndSleepWellPower extends PermPower {
        public GoodNightAndSleepWellPower() {
            this.name = this.getClass().getSimpleName();
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            int mul = (int) enemy.powerList.stream().filter(p -> p.type.equals(PowerType.DEBUFF)).count();
            return 24 * Math.min(3, mul);
        }
    }


}
