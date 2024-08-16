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
        this.owner.addPower(PermPower.create(PowerStat.TAUNT_VALUE, 200, "Moment Of Victory Taunt Value Boost"));
    }

    @Override
    public void onAttacked(AbstractEnemy enemy) {
        this.owner.addPower(TempPower.create(PowerStat.DEF_PERCENT, 24, 1, "Moment Of Victory Defense Boost"));
    }
}
