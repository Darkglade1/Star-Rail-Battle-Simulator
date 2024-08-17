package relics.relics;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

public class FiresmithOfLavaForging extends AbstractRelicSetBonus {
    public FiresmithOfLavaForging(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public FiresmithOfLavaForging(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        if (this.owner.elementType == AbstractCharacter.ElementType.FIRE) {
            this.owner.addPower(PermPower.create(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 10, "Firesmith of Lave Forging Fire Bonus"));
        }

        if (this.isFullSet) {
            this.owner.addPower(new FiresmithOfLavaForging4PC());
        }
    }

    public static class FiresmithOfLavaForging4PC extends PermPower {
        public FiresmithOfLavaForging4PC() {
            super("Firesmith of Lave Forging Fire Bonus 4PC");
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (damageTypes.contains(AbstractCharacter.DamageType.SKILL)) {
                return 12;
            }

            return 0;
        }

        @Override
        public void afterAttackFinish(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            if (!types.contains(AbstractCharacter.DamageType.ULTIMATE)) return;

            this.owner.addPower(TempPower.create(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 12, 1, "Firesmith of Lave Forging Fire Bonus 4PC Ultimate Bonus"));
        }
    }
}
