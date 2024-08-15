package lightcones.hunt;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class WorrisomeBlissful extends AbstractLightcone {

    public WorrisomeBlissful(AbstractCharacter owner) {
        super(1058, 582, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(new WorrisomeBlissfulPower());
    }



    @Override
    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (character != owner) return;
        if (enemiesHit.isEmpty()) return;

        for (AbstractEnemy enemy : enemiesHit) {
            enemy.addPower(new TameState());
        }
    }

    public static class WorrisomeBlissfulPower extends PermPower {
        public WorrisomeBlissfulPower() {
            this.name = this.getClass().getSimpleName();
            this.setStat(PowerStat.CRIT_CHANCE, 18);
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (!damageTypes.contains(AbstractCharacter.DamageType.FOLLOW_UP)) return 0;

            return 30;
        }
    }

    public static class TameState extends PermPower {
        public TameState() {
            this.name = this.getClass().getSimpleName();
            this.maxStacks = 2;
        }

        @Override
        public float receiveConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            return 12 * this.stacks;
        }
    }
}
