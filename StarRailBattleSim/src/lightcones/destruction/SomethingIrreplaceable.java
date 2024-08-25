package lightcones.destruction;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

public class SomethingIrreplaceable extends AbstractLightcone {

    public SomethingIrreplaceable(AbstractCharacter<?> owner) {
        super(1164, 582, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 24, "Something Irreplaceable ATK Boost"));
    }

    @Override
    public void onAttacked(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyFromAttacked) {
        // TODO: Restore HP
        TempPower power = TempPower.create(PowerStat.DAMAGE_BONUS, 24, 1, "Something Irreplaceable Damage Bonus");
        this.owner.addPower(power);
    }
}
