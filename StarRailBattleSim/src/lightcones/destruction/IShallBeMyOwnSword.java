package lightcones.destruction;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;


public class IShallBeMyOwnSword extends AbstractLightcone {

    private int eclipse = 0;

    public IShallBeMyOwnSword(AbstractCharacter<?> owner) {
        super(1164, 582, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 20, "I Shall Be My Own Sword CD Boost"));
    }

    @Override
    public void onCombatStart() {
        getBattle().getPlayers().forEach(c -> c.addPower(new IShallBeMyOwnSwordEffect(this)));
    }

    @Override
    public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        eclipse = 0;
    }

    public class IShallBeMyOwnSwordEffect extends PermPower {

        private IShallBeMyOwnSword lightcone;

        public IShallBeMyOwnSwordEffect(IShallBeMyOwnSword lightcone) {
            this.name = this.getClass().getSimpleName();
            this.lightcone = lightcone;
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (character != lightcone.owner) return 0;

            return eclipse * 14;
        }

        @Override
        public float getConditionDefenseIgnore(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (character != lightcone.owner) return 0;

            return eclipse == 3 ? 12 : 0;
        }

        @Override
        public void onAttacked(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyFromAttacked) {
            eclipse = Math.min(3, eclipse + 1);
        }
    }
}
