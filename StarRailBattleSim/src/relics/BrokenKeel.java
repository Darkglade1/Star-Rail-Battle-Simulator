package relics;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class BrokenKeel extends AbstractRelicSetBonus {
    public BrokenKeel(AbstractCharacter owner) {
        super(owner);
    }

    public void onEquip() {
        owner.addPower(PermPower.create(PowerStat.EFFECT_RES, 10, "Broken Keel Effect Resistance Bonus"));
    }

    public void onCombatStart() {
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            AbstractPower power = new BrokenKeelStackPower();
            character.addPower(power);
        }
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    private static class BrokenKeelStackPower extends AbstractPower {
        public BrokenKeelStackPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
            this.maxStacks = 99;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            return 10 * stacks;
        }
    }

}
