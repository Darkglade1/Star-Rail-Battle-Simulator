package lightcones.hunt;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class OnlySilenceRemains extends AbstractLightcone {

    public OnlySilenceRemains(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 32, "Only Silence Remains Attack Boost"));
        this.owner.addPower(new OnlySilenceRemainsPower());
    }

    public static class OnlySilenceRemainsPower extends PermPower {
        public OnlySilenceRemainsPower() {
            this.name = this.getClass().getSimpleName();
        }

        @Override
        public float getConditionalCritRate(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (getBattle().getEnemies().size() < 3) {
                return 24;
            }

            return 0;
        }
    }
}
