package relics;

import battleLogic.Battle;
import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;

public class Passerby extends AbstractRelicSetBonus {
    public Passerby(AbstractCharacter owner) {
        super(owner);
    }
    public Passerby(AbstractCharacter owner, boolean isFullSet) {
        super(owner, isFullSet);
    }


    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.HEALING, 10, "Passerby of the Wandering Cloud Healing Boost"));
    }

    public void onCombatStart() {
        if (this.isFullSet) {
            Battle.battle.generateSkillPoint(owner, 1);
        }
    }

    public String toString() {
        if (isFullSet) {
            return "4 PC Passerby";
        } else {
            return "2 PC Passerby";
        }
    }

}
