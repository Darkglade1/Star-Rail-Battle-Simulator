package lightcones.preservation;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

public class DayOneOfMyNewLife extends AbstractLightcone {

    public DayOneOfMyNewLife(AbstractCharacter<?> owner) {
        super(953, 370, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.DEF_PERCENT, 24, "Day One Of My New Life Defense Boost"));
    }

    @Override
    public void onCombatStart() {
        // TODO: Implement character RES, there is no dmg so doesn't do anything atm
        // for (AbstractCharacter character : Battle.battle.playerTeam) {

        // }
    }
}
