package lightcones.nihility;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class Fermata extends AbstractLightcone {

    public Fermata(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(new FermataBoost());
    }

    public static class FermataBoost extends PermPower {
        public FermataBoost() {
            this.name = this.getClass().getSimpleName();
            this.setStat(PowerStat.BREAK_EFFECT, 32);
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            // TODO: Check for Wind & Shock

            return 32;
        }
    }
}
