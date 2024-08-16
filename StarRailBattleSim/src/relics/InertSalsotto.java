package relics;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class InertSalsotto extends AbstractRelicSetBonus {
    public InertSalsotto(AbstractCharacter owner) {
        super(owner);
    }
    public void onEquip() {
        owner.addPower(PermPower.create(PowerStat.CRIT_CHANCE, 8, "Inert Salsotto Crit Chance Bonus"));
    }

    public void onCombatStart() {
        owner.addPower(new InertSalsottoDamagePower());
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    private static class InertSalsottoDamagePower extends AbstractPower {
        public InertSalsottoDamagePower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }
        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP || type == AbstractCharacter.DamageType.ULTIMATE) {
                    return 15;
                }
            }
            return 0;
        }
    }

}
