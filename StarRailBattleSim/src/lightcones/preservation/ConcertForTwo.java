package lightcones.preservation;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

public class ConcertForTwo extends AbstractLightcone {

    public ConcertForTwo(AbstractCharacter<?> owner) {
        super(953, 370, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.DEF_PERCENT, 32, "Concert For Two Defense Boost"));
    }

    @Override
    public void onCombatStart() {
        // just assume full uptime for now
        this.owner.addPower(PermPower.create(PowerStat.DAMAGE_BONUS, 32, "Concert For Two Damage Boost"));
    }
}
