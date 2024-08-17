package relics.relics;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

/**
 * The 4PC 100% boost after inflicting a debug is not working currently
 */
public class PioneerDiverOfDeadWaters extends AbstractRelicSetBonus {
    public PioneerDiverOfDeadWaters(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public PioneerDiverOfDeadWaters(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(new PioneerDiverOfDeadWaters2PC());

        if (this.isFullSet) {
            this.owner.addPower(new PioneerDiverOfDeadWaters4PC());
        }
    }

    public static class PioneerDiverOfDeadWaters2PC extends PermPower {
        public PioneerDiverOfDeadWaters2PC() {
            this.name = this.getClass().getSimpleName();
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (enemy.powerList.stream().anyMatch(p -> p.type == PowerType.DEBUFF)) {
                return 12;
            }

            return 0;
        }
    }

    public static class PioneerDiverOfDeadWaters4PC extends PermPower {

        private boolean boosted = false;

        public PioneerDiverOfDeadWaters4PC() {
            this.name = this.getClass().getSimpleName();
        }

        @Override
        public float getConditionalCritRate(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            return boosted ? 8 :4;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            int debuffs = Math.min(3, (int) enemy.powerList.stream().filter(p -> p.type == PowerType.DEBUFF).count());
            if (debuffs < 2) {
                return 0;
            }
            int mul = 4 * debuffs;
            return boosted ? 2 * mul : mul;
        }
    }

}
