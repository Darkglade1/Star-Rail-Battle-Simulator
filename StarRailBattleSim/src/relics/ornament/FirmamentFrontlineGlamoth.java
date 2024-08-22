package relics.ornament;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

import java.util.ArrayList;

public class FirmamentFrontlineGlamoth extends AbstractRelicSetBonus {
    public FirmamentFrontlineGlamoth(AbstractCharacter<?> owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public FirmamentFrontlineGlamoth(AbstractCharacter<?> owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 12, "Firmament Frontline Glamoth ATK bonus"));
    }

    @Override
    public void onCombatStart() {
        this.owner.addPower(new FirmamentFrontlineGlamothDMGBonus());
    }

    public class FirmamentFrontlineGlamothDMGBonus extends PermPower {
        public FirmamentFrontlineGlamothDMGBonus() {
            super("Firmament Frontline Glamoth DMG bonus");
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (FirmamentFrontlineGlamoth.this.owner.getFinalSpeed() > 160) {
                return 18;
            }
            if (FirmamentFrontlineGlamoth.this.owner.getFinalSpeed() > 135) {
                return 12;
            }
            return 0;
        }
    }
}
