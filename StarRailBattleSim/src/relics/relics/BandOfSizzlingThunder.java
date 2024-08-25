package relics.relics;

import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;
import relics.AbstractRelicSetBonus;

public class BandOfSizzlingThunder extends AbstractRelicSetBonus {
    public BandOfSizzlingThunder(AbstractCharacter<?> owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public BandOfSizzlingThunder(AbstractCharacter<?> owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        if (this.owner.elementType == AbstractCharacter.ElementType.LIGHTNING) {
            this.owner.addPower(PermPower.create(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 10, "Band Of Sizzling Thunder Lightning Bonus"));
        }
    }

    @Override
    public void onUseSkill() {
        if (!this.isFullSet) return;

        this.owner.addPower(TempPower.create(PowerStat.ATK_PERCENT, 20, 1, "Band of Sizzling Thunder 4PC"));
    }
}
