package lightcones.hunt;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class InTheNight extends AbstractLightcone {

    public InTheNight(AbstractCharacter owner) {
        super(1058, 582, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_CHANCE, 18));
        this.owner.addPower(new InTheNightPower());
    }

    public static class InTheNightPower extends PermPower {
        public InTheNightPower() {
            this.name = this.getClass().getSimpleName();
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (!damageTypes.contains(AbstractCharacter.DamageType.SKILL) && !damageTypes.contains(AbstractCharacter.DamageType.BASIC)) return 0;
            if (owner != character) return 0;
            if (character.getFinalSpeed() < 100) return 0;
            int stacks = Math.min(6, (int) ((character.getFinalSpeed() - 100) / 10));
            return stacks * 6;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (!damageTypes.contains(AbstractCharacter.DamageType.ULTIMATE)) return 0;
            if (owner != character) return 0;
            if (character.getFinalSpeed() < 100) return 0;
            int stacks = Math.min(6, (int) ((character.getFinalSpeed() - 100) / 10));
            return stacks * 12;
        }
    }
}
