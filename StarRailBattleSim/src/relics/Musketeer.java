package relics;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;

import java.util.ArrayList;

public class Musketeer extends AbstractRelicSetBonus {
    public Musketeer(AbstractCharacter owner) {
        super(owner);
    }
    public Musketeer(AbstractCharacter owner, boolean isFullSet) {
        super(owner, isFullSet);
    }

    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Musketeer Stat Bonus";
        statBonus.bonusAtkPercent = 12;
        if (this.isFullSet) {
            statBonus.bonusSpeedPercent = 6;
        }
        owner.addPower(statBonus);
    }

    public void onCombatStart() {
        if (this.isFullSet) {
            owner.addPower(new MusketeerDamagePower());
        }
    }

    public String toString() {
        if (isFullSet) {
            return "4 PC Musketeer";
        } else {
            return "2 PC Musketeer";
        }
    }

    private static class MusketeerDamagePower extends AbstractPower {
        public MusketeerDamagePower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }
        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.BASIC) {
                    return 10;
                }
            }
            return 0;
        }
    }

}
