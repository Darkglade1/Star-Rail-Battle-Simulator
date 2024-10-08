package lightcones.abundance;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

public class EchoesOftheCoffin extends AbstractLightcone {

    public EchoesOftheCoffin(AbstractCharacter<?> owner) {
        super(1164, 582, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 24, "Echoes of the Coffin ATK Boost"));
    }

    @Override
    public void onUseUltimate() {
        getBattle().getPlayers().forEach(c -> {
            getBattle().IncreaseSpeed(c, TempPower.create(PowerStat.FLAT_SPEED, 12, 1, "Echoes of the Coffin Speed Boost"));
        });
    }

    @Override
    public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        int stacks = Math.min(3, enemiesHit.size());
        this.owner.increaseEnergy(3 * stacks, AbstractCharacter.LIGHTCONE_ENERGY_GAIN);
    }
}
