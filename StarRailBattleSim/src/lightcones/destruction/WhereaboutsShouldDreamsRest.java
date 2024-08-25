package lightcones.destruction;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

public class WhereaboutsShouldDreamsRest extends AbstractLightcone {
    public WhereaboutsShouldDreamsRest(AbstractCharacter<?> owner) {
        super(1164, 476, 529, owner);
        throw new UnsupportedOperationException("Not implemented, speed debugs not working properly.");
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.BREAK_EFFECT, 60, "Whereabouts Should Dreams Rest Break Boost"));
    }

    @Override
    public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        for (AbstractEnemy enemy : enemiesHit) {
            enemy.addPower(new Routed(this));
        }
    }

    public static class Routed extends TempPower {

        private final WhereaboutsShouldDreamsRest lightcone;

        public Routed(WhereaboutsShouldDreamsRest lightcone) {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 2;
            this.type = PowerType.DEBUFF;
            this.lightcone = lightcone;
            this.setStat(PowerStat.SPEED_PERCENT, -20);
        }

        @Override
        public float getConditionalDamageTaken(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (!damageTypes.contains(AbstractCharacter.DamageType.BREAK)) return 0;
            if (character != this.lightcone.owner) return 0;
            return 24;
        }
    }

}
