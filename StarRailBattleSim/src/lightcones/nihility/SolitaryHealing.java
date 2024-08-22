package lightcones.nihility;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

/**
 * Energy regen not implemented, no hooks
 */
public class SolitaryHealing extends AbstractLightcone {

    public SolitaryHealing(AbstractCharacter<?> owner) {
        super(1058, 529, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.BREAK_EFFECT, 40, "Solitary Healing Break Boost"));
    }

    @Override
    public void onUseUltimate() {
        this.owner.addPower(new SolitaryHealingBoost());
    }

    public static class SolitaryHealingBoost extends TempPower {
        public SolitaryHealingBoost() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 2;
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (!damageTypes.contains(AbstractCharacter.DamageType.DOT)) return 0;

            return 48;
        }
    }


}
