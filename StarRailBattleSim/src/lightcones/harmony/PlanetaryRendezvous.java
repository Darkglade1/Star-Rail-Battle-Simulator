package lightcones.harmony;

import battleLogic.Battle;
import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

public class PlanetaryRendezvous extends AbstractLightcone {

    public PlanetaryRendezvous(AbstractCharacter<?> owner) {
        super(1058, 423, 331, owner);
    }

    @Override
    public void onCombatStart() {
        getBattle().getPlayers().stream()
                .filter(c -> c.elementType.equals(this.owner.elementType))
                .forEach(c -> c.addPower(PermPower.create(PowerStat.DAMAGE_BONUS, 24, "Planetary Rendezvous DMG Boost")));
    }
}
