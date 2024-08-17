package relics.ornament;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

public class DuranDynastyOfRunningWolves extends AbstractRelicSetBonus {
    public DuranDynastyOfRunningWolves(AbstractCharacter owner) {
        super(owner);
    }
    public DuranDynastyOfRunningWolves(AbstractCharacter owner, boolean isFullSet) {
        super(owner, isFullSet);
    }

    @Override
    public void onCombatStart() {
        Battle.battle.playerTeam.forEach(c -> c.addPower(new DuranStackPower()));
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    private class DuranStackPower extends PermPower {
        public DuranStackPower() {
            this.name = this.getClass().getSimpleName();
            this.maxStacks = 5;
        }

        @Override
        public void onBeforeUseAttack(ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (damageTypes.contains(AbstractCharacter.DamageType.FOLLOW_UP)) {
                this.stacks = Math.min(this.stacks + 1, this.maxStacks);
            }
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (character != DuranDynastyOfRunningWolves.this.owner) return 0;

            if (damageTypes.contains(AbstractCharacter.DamageType.FOLLOW_UP)) {
                return 5 * stacks;
            }
            return 0;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (character != DuranDynastyOfRunningWolves.this.owner) return 0;

            if (damageTypes.contains(AbstractCharacter.DamageType.FOLLOW_UP)  && stacks == maxStacks) {
                return 25;
            }
            return 0;
        }
    }

}
