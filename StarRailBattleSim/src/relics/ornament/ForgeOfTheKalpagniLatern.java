package relics.ornament;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

public class ForgeOfTheKalpagniLatern extends AbstractRelicSetBonus {
    public ForgeOfTheKalpagniLatern(AbstractCharacter<?> owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public ForgeOfTheKalpagniLatern(AbstractCharacter<?> owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.SPEED_PERCENT, 6, "Forge Of The Kalpagni Latern SPD Boost"));
    }


    // TODO: Rework onBeforeUseAttack to include enemies
    @Override
    public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (enemiesHit.stream().anyMatch(e -> e.weaknessMap.contains(AbstractCharacter.ElementType.FIRE))) {
            this.owner.addPower(TempPower.create(PowerStat.BREAK_EFFECT, 40, 1, "Forge Of The Kalpagni Latern Break Bonus"));
        }
    }
}
