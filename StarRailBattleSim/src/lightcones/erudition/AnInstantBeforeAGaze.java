package lightcones.erudition;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class AnInstantBeforeAGaze extends AbstractLightcone {

    public AnInstantBeforeAGaze(AbstractCharacter owner) {
        super(1058, 582, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 36));
        this.owner.addPower(new AnInstantBeforeAGazePower());
    }

    public static class AnInstantBeforeAGazePower extends AbstractPower {
        public AnInstantBeforeAGazePower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (!damageTypes.contains(AbstractCharacter.DamageType.ULTIMATE)) return 0;
            if (character != owner) return 0;

            return (float) (Math.min(character.maxEnergy, 180) * 0.36);

        }
    }
}
