package lightcones.preservation;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

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
        this.owner.addPower(PermPower.create(PowerStat.DEF_PERCENT, 40, "Inherently Unjust Destiny Defense Boost"));
        this.owner.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 40, "Inherently Unjust Destiny Crit Damage Boost"));
    }

    @Override
    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (!types.contains(AbstractCharacter.DamageType.FOLLOW_UP)) return;

        // TODO: Take EHR into account
        for (AbstractEnemy enemy : enemiesHit) {
            enemy.addPower(new FollowDmgBonus());
        }
    }

    public static class FollowDmgBonus extends TempPower {
        public FollowDmgBonus() {
            super(2);
            this.name = this.getClass().getSimpleName();
            this.type = PowerType.DEBUFF;
            this.setStat(PowerStat.DAMAGE_TAKEN, 10);
        }
    }

}
