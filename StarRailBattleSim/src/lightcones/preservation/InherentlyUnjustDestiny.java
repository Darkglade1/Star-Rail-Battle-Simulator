package lightcones.preservation;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

/**
 * The battle sim does not have a concept of shields, we assume 100% update.
 */
public class InherentlyUnjustDestiny extends AbstractLightcone {

    public InherentlyUnjustDestiny(AbstractCharacter owner) {
        super(1058, 423, 662, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.DEF_PERCENT, 40));
        this.owner.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 40));
    }

    @Override
    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (!types.contains(AbstractCharacter.DamageType.FOLLOW_UP)) return;

        // TODO: Take EHR into account
        for (AbstractEnemy enemy : enemiesHit) {
            enemy.addPower(new FollowDmgBonus());
        }
    }

    public static class FollowDmgBonus extends AbstractPower {
        public FollowDmgBonus() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 2;
            this.type = PowerType.DEBUFF;
            this.bonusDamageTaken = 10;
        }
    }

}
