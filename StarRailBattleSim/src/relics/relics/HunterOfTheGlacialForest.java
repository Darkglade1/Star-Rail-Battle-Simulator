package relics.relics;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

public class HunterOfTheGlacialForest extends AbstractRelicSetBonus {
    public HunterOfTheGlacialForest(AbstractCharacter owner) {
        super(owner);
    }

    public HunterOfTheGlacialForest(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    @Override
    public void onEquip() {
        if (this.owner.elementType == AbstractCharacter.ElementType.ICE) {
            this.owner.addPower(PermPower.create(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 10, "Hunter of the Glacial Forest Ice Boost"));
        }
    }

    @Override
    public void afterAttackFinish(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (!types.contains(AbstractCharacter.DamageType.ULTIMATE)) return;

        this.owner.addPower(TempPower.create(PowerStat.CRIT_DAMAGE, 25, 2, "Hunter of the Glacial Forest Ultimate CD Boost"));
    }
}
