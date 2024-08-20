package relics.ornament;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

public class BrokenKeel extends AbstractRelicSetBonus {
    public BrokenKeel(AbstractCharacter owner) {
        super(owner);
    }

    public void onEquip() {
        owner.addPower(PermPower.create(PowerStat.EFFECT_RES, 10, "Broken Keel Effect Resistance Bonus"));
    }

    public void onCombatStart() {
        getBattle().getPlayers().forEach(character -> character.addPower(new BrokenKeelStackPower()));
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    private class BrokenKeelStackPower extends PermPower {
        public BrokenKeelStackPower() {
            // If more than one character has this relic, the relic should not stack. As the CRIT DMG is conditional per wearer
            this.name = this.getClass().getSimpleName() + " - " + BrokenKeel.this.owner.name;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (BrokenKeel.this.owner.getTotalEffectRes() < 30) {
                return 0;
            }

            return 10;
        }
    }

}
