package relics.relics;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

public class TheAshblazingGrandDuke extends AbstractRelicSetBonus {
    public TheAshblazingGrandDuke(AbstractCharacter owner) {
        super(owner);
    }
    public TheAshblazingGrandDuke(AbstractCharacter owner, boolean isFullSet) {
        super(owner, isFullSet);
    }
    AbstractPower atkBonus;

    @Override
    public void onEquip() {
        owner.addPower(new DukeDamagePower());
    }
    @Override
    public void onBeforeUseAttack(ArrayList<AbstractCharacter.DamageType> damageTypes) {
        if (damageTypes.contains(AbstractCharacter.DamageType.FOLLOW_UP) && atkBonus != null && isFullSet) {
            owner.removePower(atkBonus.name);
        }
    }

    @Override
    public void onBeforeHitEnemy(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        atkBonus = new DukeAtkBonus();
        if (damageTypes.contains(AbstractCharacter.DamageType.FOLLOW_UP) && isFullSet) {
            owner.addPower(atkBonus);
        }
    }

    public String toString() {
        if (isFullSet) {
            return "4 PC Duke";
        } else {
            return "2 PC Duke";
        }
    }

    private static class DukeDamagePower extends AbstractPower {
        public DukeDamagePower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }
        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    return 20;
                }
            }
            return 0;
        }
    }

    private static class DukeAtkBonus extends AbstractPower {
        public DukeAtkBonus() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 3;
            this.maxStacks = 8;
        }
        @Override
        public float getConditionalAtkBonus(AbstractCharacter character) {
            return stacks * 6.0f;
        }
    }

}
