package relics;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class ChampionOfStreetwiseBoxing extends AbstractRelicSetBonus{
    public ChampionOfStreetwiseBoxing(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public ChampionOfStreetwiseBoxing(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        if (this.owner.elementType == AbstractCharacter.ElementType.PHYSICAL) {
            this.owner.addPower(PermPower.create(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 10, "Champion of Streetwise Boxing Physical Boost"));
        }
    }

    @Override
    public void onCombatStart() {
        if (!this.isFullSet) return;

        this.owner.addPower(new ChampionOfStreetwiseBoxing4PCPower());
    }

    public static class ChampionOfStreetwiseBoxing4PCPower extends PermPower {
        public ChampionOfStreetwiseBoxing4PCPower() {
            super("Champion of Streetwise Boxing 4PC Power");
        }

        @Override
        public float getConditionalAtkBonus(AbstractCharacter character) {
            return 5 * this.stacks;
        }

        @Override
        public void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyFromAttacked) {
            this.stacks = Math.min(this.stacks + 1, 5);
        }

        @Override
        public void afterAttackFinish(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            this.stacks = Math.min(this.stacks + 1, 5);
        }
    }

}
