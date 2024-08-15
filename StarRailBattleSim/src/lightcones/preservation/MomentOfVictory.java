package lightcones.preservation;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

public class MomentOfVictory extends AbstractLightcone {

    public MomentOfVictory(AbstractCharacter owner) {
        super(1058, 476, 595, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.DEF_PERCENT, 24, "Moment Of Victory Defense Boost"));
        this.owner.addPower(PermPower.create(PowerStat.EFFECT_HIT, 24, "Moment Of Victory Effect Hit Boost"));
    }

    @Override
    public void onAttacked(AbstractEnemy enemy) {
        TempPower defBoost = new TempPower();
        defBoost.name = "MomentOfVictory#onAttacked";
        defBoost.bonusDefPercent = 24;
        // Power should be removed at the end of the turn
        // TODO: Check if this is called before dmg it taken, is dmg even taken in the sim?
        defBoost.justApplied = false;
        this.owner.addPower(defBoost);
    }
}
