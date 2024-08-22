package lightcones.destruction;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

/**
 * Ignored DMG boost from break
 */
public class OnTheFallOfAnAeon extends AbstractLightcone {

    public OnTheFallOfAnAeon(AbstractCharacter<?> owner) {
        super(1058, 529, 397, owner);
    }

    @Override
    public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        this.owner.addPower(new OnTheFallOfAnAeonATKBoost());
    }

    public static class OnTheFallOfAnAeonATKBoost extends PermPower {
        public OnTheFallOfAnAeonATKBoost() {
            this.name = this.getClass().getSimpleName();
            this.maxStacks = 4;
            this.setStat(PowerStat.ATK_PERCENT, 16);
        }


    }
}
