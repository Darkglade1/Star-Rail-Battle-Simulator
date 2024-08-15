package lightcones.hunt;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class SailingTowardsASecondLife extends AbstractLightcone {

    public SailingTowardsASecondLife(AbstractCharacter owner) {
        super(1058, 582, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.BREAK_EFFECT, 60));
        this.owner.addPower(new SailingTowardsASecondLifePower());
    }

    @Override
    public void onCombatStart() {
        if (this.owner.getTotalBreakEffect() > 150) {
            this.owner.addPower(PermPower.create(PowerStat.SPEED_PERCENT, 12));
        }
    }

    public static class SailingTowardsASecondLifePower extends PermPower {
        public SailingTowardsASecondLifePower() {
            this.name = this.getClass().getSimpleName();
        }

        @Override
        public float getConditionDefenseIgnore(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (!damageTypes.contains(AbstractCharacter.DamageType.BREAK)) return 0;

            return 20;
        }
    }
}
