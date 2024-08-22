package lightcones.erudition;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class BeforeDawn extends AbstractLightcone {

    public BeforeDawn(AbstractCharacter<?> owner) {
        super(1058, 582, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 36, "Before Dawn Crit Damage Boost"));
        this.owner.addPower(new BeforeDawnPower());
    }

    public static class BeforeDawnPower extends AbstractPower {
        private boolean hasSomnusCorpus = false;

        public BeforeDawnPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (damageTypes.contains(AbstractCharacter.DamageType.FOLLOW_UP) && hasSomnusCorpus) {
                this.hasSomnusCorpus = false;
                return 48;
            }

            if (damageTypes.contains(AbstractCharacter.DamageType.SKILL) || damageTypes.contains(AbstractCharacter.DamageType.ULTIMATE)) {
                this.hasSomnusCorpus = true;
                return 18;
            }

            return 0;
        }
    }
}
