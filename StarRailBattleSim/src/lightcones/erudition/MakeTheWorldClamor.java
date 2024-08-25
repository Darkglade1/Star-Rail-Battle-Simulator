package lightcones.erudition;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;

import java.util.ArrayList;

public class MakeTheWorldClamor extends AbstractLightcone {

    public MakeTheWorldClamor(AbstractCharacter<?> owner) {
        super(847, 476, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(new MakeTheWorldClamorPower());
    }

    @Override
    public void onCombatStart() {
        if (this.owner.usesEnergy)
            this.owner.increaseEnergy(32);
    }

    public static class MakeTheWorldClamorPower extends PermPower {
        public MakeTheWorldClamorPower() {
            this.name = this.getClass().getSimpleName();
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (!damageTypes.contains(AbstractCharacter.DamageType.ULTIMATE)) return 0;

            return 64;
        }
    }
}
