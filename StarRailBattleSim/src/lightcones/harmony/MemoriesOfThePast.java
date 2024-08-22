package lightcones.harmony;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class MemoriesOfThePast extends AbstractLightcone {

    private boolean canRegen = true;

    public MemoriesOfThePast(AbstractCharacter<?> owner) {
        super(953, 423, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.BREAK_EFFECT, 56, "Memories of the Past Break Effect Boost"));
    }

    @Override
    public void onTurnStart() {
        this.canRegen = true;
    }

    @Override
    public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (!this.canRegen) return;

        this.owner.increaseEnergy(8);
        this.canRegen = false;
    }
}
