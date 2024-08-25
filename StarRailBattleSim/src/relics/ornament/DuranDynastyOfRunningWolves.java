package relics.ornament;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

public class DuranDynastyOfRunningWolves extends AbstractRelicSetBonus {
    public DuranDynastyOfRunningWolves(AbstractCharacter<?> owner) {
        super(owner);
    }
    public DuranDynastyOfRunningWolves(AbstractCharacter<?> owner, boolean isFullSet) {
        super(owner, isFullSet);
    }

    @Override
    public void onCombatStart() {
        getBattle().getPlayers().forEach(c -> c.addPower(new DuranTrackerPower()));
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    private static class DuranTrackerPower extends PermPower {
        public DuranTrackerPower() {
            this.name = this.getClass().getSimpleName();
        }
        @Override
        public void onBeforeUseAttack(ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (damageTypes.contains(AbstractCharacter.DamageType.FOLLOW_UP)) {
                for (AbstractCharacter<?> character : getBattle().getPlayers()) {
                    for (AbstractRelicSetBonus relicSetBonus : character.relicSetBonus) {
                        if (relicSetBonus instanceof DuranDynastyOfRunningWolves) {
                            character.addPower(new DuranStackPower());
                        }
                    }
                }
            }
        }
    }

    private static class DuranStackPower extends PermPower {
        public DuranStackPower() {
            this.name = this.getClass().getSimpleName();
            this.maxStacks = 5;
        }
        @Override
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    return 5 * stacks;
                }
            }
            return 0;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP && stacks == maxStacks) {
                    return 25;
                }
            }
            return 0;
        }
    }

}