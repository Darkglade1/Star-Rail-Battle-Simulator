package lightcones.abundance;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

public class ScentAloneStaysTrue extends AbstractLightcone {

    public ScentAloneStaysTrue(AbstractCharacter owner) {
        super(1058, 529, 529, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.BREAK_EFFECT, 60));
    }

    @Override
    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (!types.contains(AbstractCharacter.DamageType.ULTIMATE)) return;

        enemiesHit.forEach(e -> {
            float dmg = this.owner.getTotalBreakEffect() >= 150 ? 18 : 10;
            e.addPower(TempPower.create(PowerStat.DAMAGE_TAKEN, dmg, 2));
        });
    }
}
