package lightcones.destruction;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class TheUnreachableSide extends AbstractLightcone {
    public TheUnreachableSide(AbstractCharacter owner) {
        super(1270, 582, 331, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_CHANCE, 18, "The Unreachable Side CR Boost"));
        this.owner.addPower(PermPower.create(PowerStat.HP_PERCENT, 18, "The Unreachable Side HP Boost"));
    }

    // TODO: On consuming health
    public static class TheUnreachableSideDMGBoost extends AbstractPower {

        private boolean active = true;

        public TheUnreachableSideDMGBoost() {
            this.name = this.getClass().getSimpleName();
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (this.active) {
                this.active = false;
                return 24;
            }

            return 0;
        }

        @Override
        public void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyFromAttacked) {
            this.active = true;
        }
    }
}
