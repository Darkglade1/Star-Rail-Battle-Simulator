package relics.ornament;

import battleLogic.Battle;
import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

public class LushakaTheSunkenSeas extends AbstractRelicSetBonus {
    public LushakaTheSunkenSeas(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public LushakaTheSunkenSeas(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ENERGY_REGEN, 5, "Lushaka The Sunken Seas Effect Resistance Bonus"));
    }

    @Override
    public void onCombatStart() {
        if (getBattle().getPlayers().isEmpty()) return;

        AbstractCharacter firstAlly = getBattle().getPlayers().get(0);
        if (firstAlly != this.owner) {
            firstAlly.addPower(PermPower.create(PowerStat.ATK_PERCENT, 12, "Lushaka The Sunken Seas ATK Bonus"));
        }
    }
}
