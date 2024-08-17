package relics;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class EagleOfTwilightLine extends AbstractRelicSetBonus{
    public EagleOfTwilightLine(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public EagleOfTwilightLine(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        if (this.owner.elementType == AbstractCharacter.ElementType.WIND) {
            this.owner.addPower(PermPower.create(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 10, "Genius of Brilliant Stars Quantum bonus"));
        }
    }

    @Override
    public void afterAttackFinish(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (!this.isFullSet) return;
        if (!types.contains(AbstractCharacter.DamageType.ULTIMATE)) return;

        Battle.battle.AdvanceEntity(this.owner, 25);
    }
}
