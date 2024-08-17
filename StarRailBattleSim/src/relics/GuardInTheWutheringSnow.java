package relics;

import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;

public class GuardInTheWutheringSnow extends AbstractRelicSetBonus {
    public GuardInTheWutheringSnow(AbstractCharacter owner) {
        super(owner);
    }

    public GuardInTheWutheringSnow(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.DEFENSE_REDUCTION, 8, "Guard in the Wuthering Snow DMG reduction"));
    }

    @Override
    public void onTurnStart() {
        // TODO: Regen HP

        //if (this.owner.getCurrentHP() < this.owner.getFinalHP() /2 ) {
        //    this.owner.increaseEnergy(5);
        //}
    }
}
