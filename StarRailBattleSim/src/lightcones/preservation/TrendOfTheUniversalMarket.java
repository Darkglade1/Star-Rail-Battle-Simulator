package lightcones.preservation;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class TrendOfTheUniversalMarket extends AbstractLightcone {

    public TrendOfTheUniversalMarket(AbstractCharacter owner) {
        super(1058, 370, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.DEF_PERCENT, 32, "Trend Of The Universal Market Defense Boost"));
    }

    @Override
    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        double dmg = character.getFinalDefense() * 0.8;
        // TODO: Add dot to enemies
        // for (AbstractEnemy enemy : enemiesHit) {
        //
        // }
    }
}
