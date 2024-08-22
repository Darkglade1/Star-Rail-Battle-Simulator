package relics.relics;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

public class GeniusOfBrilliantStars extends AbstractRelicSetBonus {
    public GeniusOfBrilliantStars(AbstractCharacter<?> owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public GeniusOfBrilliantStars(AbstractCharacter<?> owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        if (this.owner.elementType == AbstractCharacter.ElementType.QUANTUM) {
            this.owner.addPower(PermPower.create(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 10, "Genius of Brilliant Stars Quantum bonus"));
        }

        if (this.isFullSet) {
            this.owner.addPower(new GeniusOfBrilliantStars4PC());
        }
    }

    public static class GeniusOfBrilliantStars4PC extends PermPower {
        public GeniusOfBrilliantStars4PC() {
            super("Genius of Brilliant Stars 4PC power");
        }

        @Override
        public float getConditionDefenseIgnore(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (enemy.weaknessMap.contains(AbstractCharacter.ElementType.QUANTUM)) {
                return 10 * 2;
            }

            return 10;
        }
    }
}
