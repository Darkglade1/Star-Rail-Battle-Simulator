package lightcones.nihility;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

public class ResolutionShinesAsPearlsOfSweat extends AbstractLightcone {

    public ResolutionShinesAsPearlsOfSweat(AbstractCharacter<?> owner) {
        super(953, 476, 331, owner);
    }

    @Override
    public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        for (AbstractEnemy enemy : enemiesHit) {
            if (!enemy.hasPower(Ensnared.NAME)) {
                enemy.addPower(new Ensnared());
            }
        }
    }

    public static class Ensnared extends TempPower {

        public static String NAME = "Ensnared";


        public Ensnared() {
            this.name = NAME;
            this.type = PowerType.DEBUFF;
            this.turnDuration = 1;
            this.setStat(PowerStat.DEFENSE_REDUCTION, 16);
        }
    }
}
