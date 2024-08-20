package relics.ornament;

import battleLogic.Battle;
import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

public class SpringhtlyVonwacq extends AbstractRelicSetBonus {
    public SpringhtlyVonwacq(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public SpringhtlyVonwacq(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ENERGY_REGEN, 5, "Springhtly Vonwacq energy regen"));
    }

    @Override
    public void onCombatStart() {
        if (this.owner.getFinalSpeed() >= 120) {
            getBattle().AdvanceEntity(this.owner, 40);
        }
    }
}
