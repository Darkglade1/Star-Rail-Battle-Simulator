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
public class WatchMakerMasterOfDreamMachinations extends AbstractRelicSetBonus {

    public WatchMakerMasterOfDreamMachinations(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public WatchMakerMasterOfDreamMachinations(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.BREAK_EFFECT, 16, "Watch Maker's Master of Dream Machinations 2PC"));
    }

    @Override
    public void onUseUltimate() {
        if (!this.isFullSet) return;

        getBattle().getPlayers().forEach(c -> {
            TempPower ultPower = TempPower.create(PowerStat.BREAK_EFFECT, 30, 2, "Watch Maker's Master of Dream Machinations 4PC Ult");
            c.addPower(ultPower);
        });
    }
}
