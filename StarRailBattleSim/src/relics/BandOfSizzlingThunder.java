package relics;

import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

public class BandOfSizzlingThunder extends AbstractRelicSetBonus {
    public BandOfSizzlingThunder(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public BandOfSizzlingThunder(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        if (this.owner.elementType == AbstractCharacter.ElementType.LIGHTNING) {
            this.owner.addPower(PermPower.create(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 10, "Firesmith of Lave Forging Fire Bonus"));
        }
    }

    @Override
    public void onUseSkill() {
        if (!this.isFullSet) return;

        // When the wearer uses their Skill, increases the wearer's ATK by 20% for 1 turn(s).
        // Not sure if this means after skill, or add it before using skill. And that skill is also boosted(?)
        // Someone would have to check, I don't have this set lol
        this.owner.addPower(TempPower.create(PowerStat.ATK_PERCENT, 20, 1, "Band of Sizzling Thunder 4PC"));
    }
}
