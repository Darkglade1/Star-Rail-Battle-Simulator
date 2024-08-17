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
        this.owner.addPower(PermPower.create(PowerStat.EFFECT_RES, 5, "Lushaka The Sunken Seas Effect Resistance Bonus"));
    }

    @Override
    public void onCombatStart() {
        if (Battle.battle.playerTeam.isEmpty()) return;

        AbstractCharacter firstAlly = Battle.battle.playerTeam.get(0);
        if (firstAlly != this.owner) {
            firstAlly.addPower(PermPower.create(PowerStat.ATK_PERCENT, 15, "Lushaka The Sunken Seas ATK Bonus"));
        }
    }
}
