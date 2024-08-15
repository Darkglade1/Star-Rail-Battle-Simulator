package lightcones.harmony;

import battleLogic.Battle;
import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

/**
 * Assumes 100% uptime, would need to implement events for stuff like this
 */
public class FlowingNightglow extends AbstractLightcone {

    public FlowingNightglow(AbstractCharacter owner) {
        super(953, 635, 463, owner);
    }

    @Override
    public void onCombatStart() {
        this.owner.addPower(PermPower.create(PowerStat.ENERGY_REGEN, 15, "Flowing Nightglow Energy Regen Boost"));
    }

    @Override
    public void onUseUltimate() {
        this.owner.addPower(TempPower.create(PowerStat.ATK_PERCENT, 48, 1, "Flowing Nightglow ATK Boost"));
        Battle.battle.playerTeam.forEach(c -> c.addPower(TempPower.create(PowerStat.DAMAGE_BONUS, 24, 1, "Flowing Nightglow DMG Boost")));
    }
}
