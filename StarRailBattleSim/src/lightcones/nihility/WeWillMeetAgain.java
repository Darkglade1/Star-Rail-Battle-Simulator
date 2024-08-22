package lightcones.nihility;

import battleLogic.BattleHelpers;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;

import java.util.ArrayList;

public class WeWillMeetAgain extends AbstractLightcone {

    public WeWillMeetAgain(AbstractCharacter<?> owner) {
        super(847, 529, 331, owner);
    }

    @Override
    public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        for (AbstractEnemy enemy : enemiesHit) {
            getBattle().getHelper().additionalDamageHitEnemy(this.owner, enemy, 96, BattleHelpers.MultiplierStat.ATK);
        }
    }
}
