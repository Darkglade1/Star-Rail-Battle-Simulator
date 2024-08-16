package lightcones.destruction;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class DanceAtSunset extends AbstractLightcone {

    public DanceAtSunset(AbstractCharacter owner) {
        super(1058, 582, 463, owner);
    }

    @Override
    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Dance At Sunset Stat Bonus";
        statBonus.setStat(PowerStat.CRIT_DAMAGE, 36);
        statBonus.setStat(PowerStat.TAUNT_VALUE, 500);
        owner.addPower(statBonus);
    }

    public void onUseUltimate() {
        owner.addPower(new DanceAtSunsetDamagePower());
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    public static class DanceAtSunsetDamagePower extends AbstractPower {
        public DanceAtSunsetDamagePower() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 2;
            this.maxStacks = 2;
        }
        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    return 36 * stacks;
                }
            }
            return 0;
        }
    }
}