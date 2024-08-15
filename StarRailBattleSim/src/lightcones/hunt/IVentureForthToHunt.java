package lightcones.hunt;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class IVentureForthToHunt extends AbstractLightcone {

    public IVentureForthToHunt(AbstractCharacter owner) {
        super(953, 635, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_CHANCE, 15, "I Venture Forth to Hunt Crit Chance Boost"));
        this.owner.addPower(new VentureForthPower());
    }

    public String toString() {
        return "I Venture Forth to Hunt";
    }

    public static class VentureForthPower extends AbstractPower {
        public VentureForthPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        @Override
        public void onEndTurn() {
            this.stacks = Math.max(0, this.stacks - 1);
        }

        @Override
        public float getConditionDefenseIgnore(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (damageTypes.contains(AbstractCharacter.DamageType.ULTIMATE) && this.stacks > 0) {
                return 27 * this.stacks;
            }
            if (damageTypes.contains(AbstractCharacter.DamageType.FOLLOW_UP)) {
                this.stacks = Math.min(2, this.stacks + 1);
            }
            return 0;
        }
    }
}
