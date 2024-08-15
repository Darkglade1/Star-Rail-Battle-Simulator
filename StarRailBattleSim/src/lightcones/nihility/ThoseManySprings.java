package lightcones.nihility;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

public class ThoseManySprings extends AbstractLightcone {

    public ThoseManySprings(AbstractCharacter owner) {
        super(953, 582, 529, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.EFFECT_HIT, 60, "Those Many Springs Effect Hit Boost"));
    }

    @Override
    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (!types.contains(AbstractCharacter.DamageType.BASIC)
                && !types.contains(AbstractCharacter.DamageType.SKILL)
                && !types.contains(AbstractCharacter.DamageType.ULTIMATE)) {
            return;
        }

        for (AbstractEnemy enemy : enemiesHit) {
            if (enemy.hasPower(Unarmored.NAME)) {
                enemy.removePower(Unarmored.NAME);
                enemy.addPower(new Cornered());
            } else if (!enemy.hasPower(Cornered.NAME)) {
                enemy.addPower(new Unarmored());
            }
        }
    }

    public static class Unarmored extends TempPower {
        public static final String NAME = "Unarmored";

        public Unarmored() {
            this.name = NAME;
            this.turnDuration = 2;
            this.bonusDamageBonus = 10;
        }
    }

    public static class Cornered extends TempPower {
        public static final String NAME = "Cornered";

        public Cornered() {
            this.name = NAME;
            this.turnDuration = 2;
            this.bonusDamageBonus = 24;
        }
    }
}
