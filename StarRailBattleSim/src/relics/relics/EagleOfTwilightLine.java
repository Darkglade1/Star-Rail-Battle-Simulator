package relics.relics;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

public class EagleOfTwilightLine extends AbstractRelicSetBonus {
    public EagleOfTwilightLine(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public EagleOfTwilightLine(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        if (this.owner.elementType == AbstractCharacter.ElementType.WIND) {
            this.owner.addPower(PermPower.create(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 10, "Eagle Of Twilight Line Wind bonus"));
        }
    }

    @Override
    public void onUseUltimate() {
        if (!this.isFullSet) return;

        Battle.battle.AdvanceEntity(this.owner, 25);
    }
}
