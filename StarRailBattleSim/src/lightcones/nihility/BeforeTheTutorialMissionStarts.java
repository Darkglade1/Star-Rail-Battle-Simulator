package lightcones.nihility;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class BeforeTheTutorialMissionStarts extends AbstractLightcone {

    public BeforeTheTutorialMissionStarts(AbstractCharacter<?> owner) {
        super(953, 476, 331, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.EFFECT_HIT, 40, "Before The Tutorial Mission Starts Boost"));
    }

    @Override
    public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (enemiesHit.stream()
                .anyMatch(e -> e.powerList
                        .stream()
                        .anyMatch(p -> p.getStat(PowerStat.DEFENSE_REDUCTION) != 0))) {
            this.owner.increaseEnergy(8);
        }
    }

}
