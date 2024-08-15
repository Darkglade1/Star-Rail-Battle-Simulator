package lightcones.destruction;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

public class BrighterThanTheSun extends AbstractLightcone {

    public BrighterThanTheSun(AbstractCharacter owner) {
        super(1058, 635, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_CHANCE, 18, "Brighter Than The Sun CR Boost"));
    }

    @Override
    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (types.contains(AbstractCharacter.DamageType.BASIC)) {
            this.owner.addPower(new DragonsCall());
        }
    }

    public static class DragonsCall extends TempPower {
        public DragonsCall() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 2;
            this.maxStacks = 2;
            this.setStat(PowerStat.ATK_PERCENT, 18);
            this.setStat(PowerStat.ENERGY_REGEN, 6);
        }
    }

}
