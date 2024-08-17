package relics.relics;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

/**
 * 4PC CD bonus is currently not working, need to implement a more complete weakness break for it
 */
public class WastelandOfBanditryDesert extends AbstractRelicSetBonus {
    public WastelandOfBanditryDesert(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public WastelandOfBanditryDesert(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        if (this.owner.elementType == AbstractCharacter.ElementType.IMAGINARY) {
            this.owner.addPower(PermPower.create(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 10, "Genius of Brilliant Stars Quantum bonus"));
        }
    }

    public static class WastelandOfBanditryDesert4PC extends PermPower {
        public WastelandOfBanditryDesert4PC(AbstractCharacter owner) {
            super("Wasteland of Banditry Desert 4PC bonus");
        }

        @Override
        public float getConditionalCritRate(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (enemy.powerList.stream().noneMatch(p -> p.type == PowerType.DEBUFF)) {
                return 0;
            }

            return 10;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            //if (enemy.powerList.stream().noneMatch(p -> p.name.equals(WeaknessBreak.IMPRISONED))) {
            //    return 0;
            //}
            // return 20;

            return 0;
        }
    }
}
