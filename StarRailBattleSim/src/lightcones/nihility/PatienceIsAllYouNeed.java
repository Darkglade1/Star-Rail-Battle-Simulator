package lightcones.nihility;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class PatienceIsAllYouNeed extends AbstractLightcone {

    public PatienceIsAllYouNeed(AbstractCharacter<?> owner) {
        super(1058, 582, 463, owner);
        throw new UnsupportedOperationException("Not implemented, stacking speed buffs currently doesn't work.");
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.DAMAGE_BONUS, 24, "Patience Is All You Need DMG Boost"));
    }

    @Override
    public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (character == null) return;
        character.addPower(new PatienceIsAllYouNeedSpeedBoost());
    }

    public static class PatienceIsAllYouNeedSpeedBoost extends PermPower {
        public PatienceIsAllYouNeedSpeedBoost() {
            this.name = this.getClass().getSimpleName();
            this.maxStacks = 3;
            this.setStat(PowerStat.SPEED_PERCENT, 4.8f);
        }
    }
}
