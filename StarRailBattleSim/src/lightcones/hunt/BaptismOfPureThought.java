package lightcones.hunt;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

/**
 * enemyDebugs are not tracked, so it a constant. It is capped at 3. Passing none assumes 3
 */
public class BaptismOfPureThought extends AbstractLightcone {

    private final int enemyDebugs;

    public BaptismOfPureThought(AbstractCharacter owner) {
        this(owner, 3);
    }

    public BaptismOfPureThought(AbstractCharacter owner, int enemyDebugs) {
        super(953, 582, 529, owner);
        this.enemyDebugs = Math.min(3, enemyDebugs);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 20 + this.enemyDebugs * 8));
    }

    @Override
    public void onUseUltimate() {
        this.owner.addPower(new DisputationEffect());
    }

    public static class DisputationEffect extends TempPower {
        public DisputationEffect() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 2;
            this.setStat(PowerStat.DAMAGE_BONUS, 36);
        }

        @Override
        public float getConditionDefenseIgnore(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (!damageTypes.contains(AbstractCharacter.DamageType.FOLLOW_UP)) return 0;

            return 24;
        }
    }
}
