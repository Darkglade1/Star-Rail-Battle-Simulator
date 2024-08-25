package relics.ornament;

import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

public class PanCosmicCommercialEnterprise extends AbstractRelicSetBonus {
    public PanCosmicCommercialEnterprise(AbstractCharacter<?> owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public PanCosmicCommercialEnterprise(AbstractCharacter<?> owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.EFFECT_HIT, 10, "Pan Cosmic Commercial Enterprise effect hit bonus"));
    }

    @Override
    public void onCombatStart() {
        float bonus = Math.min(25, this.owner.getTotalEHR() / 25);
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, bonus, "Pan Cosmic Commercial Enterprise ATK bonus"));
    }
}
