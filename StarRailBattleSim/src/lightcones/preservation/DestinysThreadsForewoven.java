package lightcones.preservation;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class DestinysThreadsForewoven extends AbstractLightcone {

    public DestinysThreadsForewoven(AbstractCharacter<?> owner) {
        super(953, 370, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.EFFECT_RES, 20, "Destiny's Threads Forewoven Effect Resistance Boost"));
        this.owner.addPower(new DestinysThreadsForewovenPower());
    }

    public static class DestinysThreadsForewovenPower extends AbstractPower {
        public DestinysThreadsForewovenPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (character != owner) return 0;
            return Math.min((float) (((int) (character.getFinalDefense() / 100)) * 1.2), 48);
        }
    }
}
