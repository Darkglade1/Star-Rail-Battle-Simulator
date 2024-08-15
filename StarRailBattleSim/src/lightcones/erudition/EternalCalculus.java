package lightcones.erudition;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

public class EternalCalculus extends AbstractLightcone {

    public EternalCalculus(AbstractCharacter owner) {
        super(1058, 529, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 12));
        this.owner.addPower(new EternalCalculusPower());
    }

    @Override
    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        super.onAttack(character, enemiesHit, types);
    }

    public static class EternalCalculusPower extends AbstractPower {

        public EternalCalculusPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        @Override
        public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            this.stacks = Math.min(5, enemiesHit.size());
            if (this.stacks > 3) {
                this.owner.addPower(TempPower.create(PowerStat.SPEED_PERCENT, 16, 1));
            }
        }

        @Override
        public float getConditionalAtkBonus(AbstractCharacter character) {
            return 8 * this.stacks;
        }
    }
}