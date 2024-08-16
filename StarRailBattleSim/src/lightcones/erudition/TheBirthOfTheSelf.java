package lightcones.erudition;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;

import java.util.ArrayList;

public class TheBirthOfTheSelf extends AbstractLightcone {

    public TheBirthOfTheSelf(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    @Override
    public void onEquip() {
        super.onEquip();
    }

    public static class TheBirthOfTheSelfPower extends PermPower {
        public TheBirthOfTheSelfPower() {
            this.name = this.getClass().getSimpleName();
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (!damageTypes.contains(AbstractCharacter.DamageType.FOLLOW_UP)) return 0;
            // if (enemy.getCurrentHp() < enemy.baseHP / 2) {
            //    return 96;
            // }

            return 48;
        }
    }
}
