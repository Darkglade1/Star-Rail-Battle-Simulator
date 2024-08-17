package relics.ornament;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

public class Duran extends AbstractRelicSetBonus {
    public Duran(AbstractCharacter owner) {
        super(owner);
    }
    public Duran(AbstractCharacter owner, boolean isFullSet) {
        super(owner, isFullSet);
    }

    @Override
    public void onBeforeUseAttack(ArrayList<AbstractCharacter.DamageType> damageTypes) {
        if (damageTypes.contains(AbstractCharacter.DamageType.FOLLOW_UP)) {
            for (AbstractCharacter character : Battle.battle.playerTeam) {
                for (AbstractRelicSetBonus relicSetBonus : character.relicSetBonus) {
                    if (relicSetBonus instanceof Duran) {
                        character.addPower(new DuranStackPower());
                    }
                }
            }
        }
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    private static class DuranStackPower extends AbstractPower {
        public DuranStackPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
            this.maxStacks = 5;
        }
        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    return 5 * stacks;
                }
            }
            return 0;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP && stacks == maxStacks) {
                    return 25;
                }
            }
            return 0;
        }
    }

}