package lightcones.destruction;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

public class SomethingIrreplaceable extends AbstractLightcone {

    public SomethingIrreplaceable(AbstractCharacter owner) {
        super(1164, 582, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 24, "Something Irreplaceable ATK Boost"));
    }

    @Override
    public void onAttacked(AbstractEnemy enemy) {
        // TODO: Restore HP
        TempPower power = TempPower.create(PowerStat.DAMAGE_BONUS, 24, 1, "Something Irreplaceable Damage Bonus");
        power.justApplied = false; // Removes on next turn start
        this.owner.addPower(power);
    }
}
