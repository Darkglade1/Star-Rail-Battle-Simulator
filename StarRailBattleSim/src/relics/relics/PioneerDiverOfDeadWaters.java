package relics.relics;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

/**
 * Assumes the character always inflicts a debuff every turn.
 */
public class PioneerDiverOfDeadWaters extends AbstractRelicSetBonus {
    public PioneerDiverOfDeadWaters(AbstractCharacter<?> owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public PioneerDiverOfDeadWaters(AbstractCharacter<?> owner) {
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
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (enemy.powerList.stream().anyMatch(p -> p.type == PowerType.DEBUFF)) {
                return 12;
            }
            return 0;
        }
    }

    public static class PioneerDiverOfDeadWaters4PC extends PermPower {

        public PioneerDiverOfDeadWaters4PC() {
            this.name = this.getClass().getSimpleName();
            this.setStat(PowerStat.CRIT_CHANCE, 4);
        }

        @Override
        public float getConditionalCritRate(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            return 4;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            int debuffs = Math.min(3, (int) enemy.powerList.stream().filter(p -> p.type == PowerType.DEBUFF).count());
            if (debuffs < 2) {
                return 0;
            }
            return 8 * debuffs;
        }
    }

}
