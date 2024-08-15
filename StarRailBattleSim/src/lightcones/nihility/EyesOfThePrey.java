package lightcones.nihility;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class EyesOfThePrey extends AbstractLightcone {

    public EyesOfThePrey(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(new EyesOfThePreyPower());
    }

    public static class EyesOfThePreyPower extends PermPower {
        public EyesOfThePreyPower() {
            this.name = this.getClass().getSimpleName();
            this.setStat(PowerStat.EFFECT_HIT, 40);
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (!damageTypes.contains(AbstractCharacter.DamageType.DOT)) return 0;

            return 48;
        }
    }
}
