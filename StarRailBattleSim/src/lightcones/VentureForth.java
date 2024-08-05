package lightcones;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;

import java.util.ArrayList;

public class VentureForth extends AbstractLightcone {

    public VentureForth(AbstractCharacter owner) {
        super(953, 635, 463, owner);
    }

    @Override
    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Venture Forth Stat Bonus";
        statBonus.bonusCritChance = 15;
        owner.addPower(statBonus);
    }

    public void onUseUltimate() {
        owner.addPower(new VentureForthPower());
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
        public float getConditionDefenseIgnore(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (damageTypes.contains(AbstractCharacter.DamageType.ULTIMATE)) {
                return 54;
            }
            return 0;
        }
    }
}
