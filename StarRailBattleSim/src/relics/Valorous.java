package relics;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class Valorous extends AbstractRelicSetBonus {
    public Valorous(AbstractCharacter owner) {
        super(owner);
    }
    public Valorous(AbstractCharacter owner, boolean isFullSet) {
        super(owner, isFullSet);
    }

    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Valorous Stat Bonus";
        statBonus.setStat(PowerStat.ATK_PERCENT, 12);
        if (this.isFullSet) {
            statBonus.setStat(PowerStat.CRIT_CHANCE, 6);
        }
        owner.addPower(statBonus);
    }

    @Override
    public void onBeforeUseAttack(ArrayList<AbstractCharacter.DamageType> damageTypes) {
        if (damageTypes.contains(AbstractCharacter.DamageType.FOLLOW_UP) && isFullSet) {
            owner.addPower(new ValorousDamagePower());
        }
    }

    public String toString() {
        if (isFullSet) {
            return "4 PC Wind Soaring Valorous";
        } else {
            return "2 PC Wind Soaring Valorous";
        }
    }

    private static class ValorousDamagePower extends AbstractPower {
        public ValorousDamagePower() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 1;
        }
        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.ULTIMATE) {
                    return 36;
                }
            }
            return 0;
        }
    }

}
