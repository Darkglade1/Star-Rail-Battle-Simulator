package lightcones.erudition;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

public class TheDayTheCosmosFell extends AbstractLightcone {

    public TheDayTheCosmosFell(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 24));
    }

    @Override
    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (enemiesHit.stream()
                .filter(e -> e.weaknessMap.contains(this.owner.elementType))
                .count() < 2) return;

        this.owner.addPower(TempPower.create(PowerStat.CRIT_DAMAGE, 40, 2));
    }
}
