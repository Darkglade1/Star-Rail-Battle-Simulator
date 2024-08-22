package lightcones.erudition;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;

import java.util.ArrayList;


public class NightOnTheMilkyWay extends AbstractLightcone {

    private final int weaknessBoostUptime;

    /**
     * Constructor for NightOnTheMilkyWay, with a default weakness boost uptime of 50%
     */
    public NightOnTheMilkyWay(AbstractCharacter<?> owner) {
        this(owner, 50);
    }

    public NightOnTheMilkyWay(AbstractCharacter<?> owner, int weaknessBoostUptime) {
        super(1164, 582, 397, owner);
        this.weaknessBoostUptime = weaknessBoostUptime;
    }

    @Override
    public void onEquip() {
        this.owner.addPower(new NightOnTheMilkyWayPower());
    }

    public class NightOnTheMilkyWayPower extends AbstractPower {
        public NightOnTheMilkyWayPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        @Override
        public float getConditionalAtkBonus(AbstractCharacter<?> character) {
            return 15 * getBattle().getEnemies().size();
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (getBattle().getMilkyWayRng().nextInt(100) < weaknessBoostUptime) {
                return 50;
            }
            return 0;
        }
    }


}
