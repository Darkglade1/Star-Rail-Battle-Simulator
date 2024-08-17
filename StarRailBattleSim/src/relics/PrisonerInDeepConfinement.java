package relics;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class PrisonerInDeepConfinement extends AbstractRelicSetBonus{
    public PrisonerInDeepConfinement(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public PrisonerInDeepConfinement(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 12, "Prisoner In Deep Confinement 2PC"));

        if (this.isFullSet) {
            this.owner.addPower(new PrisonerInDeepConfinement4PC());
        }
    }

    public static class PrisonerInDeepConfinement4PC extends PermPower {
        public PrisonerInDeepConfinement4PC() {
            super("Prisoner In Deep Confinement 4PC");
        }

        @Override
        public float getConditionDefenseIgnore(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            int mul = (int) Math.min(3, enemy.powerList
                    .stream()
                    .filter(p -> p.type == PowerType.DOT)
                    .count());
            return mul * 6;
        }
    }
}
