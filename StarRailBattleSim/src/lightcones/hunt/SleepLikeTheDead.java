package lightcones.hunt;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

/**
 * No info on crit after hit, so always missing crit after cooldown is over
 */
public class SleepLikeTheDead extends AbstractLightcone {

    private int cooldown = 0;

    public SleepLikeTheDead(AbstractCharacter<?> owner) {
        super(1058, 582, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 36, "Sleep Like The Dead Crit Damage Boost"));
    }

    @Override
    public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (this.cooldown <= 0 && (types.contains(AbstractCharacter.DamageType.SKILL) || types.contains(AbstractCharacter.DamageType.BASIC))) {
            AbstractPower critPower = TempPower.create(PowerStat.CRIT_CHANCE, 36, 1, "Sleep Like The Dead Crit Chance Boost");
            critPower.justApplied = true;
            this.owner.addPower(critPower);
            cooldown = 3;
        }
    }

    @Override
    public void onTurnStart() {
        if (cooldown > 0) {
            cooldown--;
        }
    }
}
