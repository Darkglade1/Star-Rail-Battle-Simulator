package lightcones.nihility;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class AlongThePassingShore extends AbstractLightcone {

    public AlongThePassingShore(AbstractCharacter owner) {
        super(1058, 635, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 36, "Along The Passing Shore Crit Damage Boost"));
    }

    @Override
    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        for (AbstractEnemy enemy : enemiesHit) {
            enemy.addPower(new MirageFizzle(this));
        }
    }

    public static class MirageFizzle extends PermPower {

        private final AlongThePassingShore lightcone;

        public MirageFizzle(AlongThePassingShore lightcone) {
            this.name = this.getClass().getSimpleName();
            this.lightcone = lightcone;
            this.type = PowerType.DEBUFF;
        }

        @Override
        public float getConditionalDamageTaken(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (character != this.lightcone.owner) return 0;

            if (damageTypes.contains(AbstractCharacter.DamageType.ULTIMATE)) {
                return 0.24f * 2;
            }

            return 0.24f;
        }
    }
}
