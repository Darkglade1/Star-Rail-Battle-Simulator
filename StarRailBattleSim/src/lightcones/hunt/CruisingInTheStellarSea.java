package lightcones.hunt;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

/**
 * Default uptime is 50%
 */
public class CruisingInTheStellarSea extends AbstractLightcone {

    private final float upTime;

    public CruisingInTheStellarSea(AbstractCharacter owner) {
        this(owner, 0.5f);
    }

    public CruisingInTheStellarSea(AbstractCharacter owner, float upTime) {
        super(953, 529, 463, owner);
        this.upTime = Math.max(0, Math.min(1, upTime));
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_CHANCE, 16, "Cruising In The Stellar Sea Crit Chance Boost"));
    }

    @Override
    public void onCombatStart() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 40 * upTime, "Cruising In The Stellar Sea Attack Boost"));
    }
}
