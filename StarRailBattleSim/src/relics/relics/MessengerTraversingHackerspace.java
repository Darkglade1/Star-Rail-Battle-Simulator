package relics.relics;

import battleLogic.Battle;
import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;
import relics.AbstractRelicSetBonus;

/**
 * Currently no check if ultimate is used on ally, don't add the 4PC if the character can't do this
 */
public class MessengerTraversingHackerspace extends AbstractRelicSetBonus {

    private TempPower ultimate4PC = TempPower.create(PowerStat.SPEED_PERCENT, 12, 1, "Messenger Traversing Hackerspace 4PC ");

    public MessengerTraversingHackerspace(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public MessengerTraversingHackerspace(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.SPEED_PERCENT, 6, "Messenger Traversing Hackerspace 2PC"));
    }

    @Override
    public void onUseUltimate() {
        if (!this.isFullSet) return;

        Battle.battle.playerTeam.forEach(c -> c.addPower(ultimate4PC));
    }
}
