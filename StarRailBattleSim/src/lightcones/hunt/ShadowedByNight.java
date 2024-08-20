package lightcones.hunt;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

public class ShadowedByNight extends AbstractLightcone {

    public ShadowedByNight(AbstractCharacter owner) {
        super(847, 476, 397, owner);
    }


    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.BREAK_EFFECT, 56, "Shadowed By Night Break Effect Boost"));
    }

    @Override
    public void onCombatStart() {
        getBattle().IncreaseSpeed(this.owner, new ShadowedByNightPower());
    }

    @Override
    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (!types.contains(AbstractCharacter.DamageType.BREAK)) return;

        getBattle().IncreaseSpeed(this.owner, new ShadowedByNightPower());
    }

    public static class ShadowedByNightPower extends TempPower {
        public ShadowedByNightPower() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 2;
            this.setStat(PowerStat.SPEED_PERCENT, 12);
        }
    }
}
