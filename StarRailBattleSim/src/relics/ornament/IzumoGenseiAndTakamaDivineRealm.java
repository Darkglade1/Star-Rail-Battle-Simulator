package relics.ornament;

import battleLogic.Battle;
import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

public class IzumoGenseiAndTakamaDivineRealm extends AbstractRelicSetBonus {
    public IzumoGenseiAndTakamaDivineRealm(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public IzumoGenseiAndTakamaDivineRealm(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 12, "Izumo Gensei And Takama Divine Realm ATK boost"));
    }

    @Override
    public void onCombatStart() {
        if (Battle.battle.playerTeam.stream().anyMatch(c -> c.getPath() == this.owner.getPath())) {
            this.owner.addPower(PermPower.create(PowerStat.CRIT_CHANCE, 12, "Izumo Gensei And Takama Divine Realm CRIT boost"));
        }
    }
}
