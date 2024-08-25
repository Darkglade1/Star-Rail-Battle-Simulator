package relics.relics;

import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GuardInTheWutheringSnow extends AbstractRelicSetBonus {
    public GuardInTheWutheringSnow(AbstractCharacter<?> owner) {
        super(owner);

        throw new NotImplementedException();
    }

    public GuardInTheWutheringSnow(AbstractCharacter<?> owner, boolean fullSet) {
        super(owner, fullSet);

        throw new NotImplementedException();
    }

    @Override
    public void onTurnStart() {
        // TODO: Regen HP

        //if (this.owner.getCurrentHP() < this.owner.getFinalHP() /2 ) {
        //    this.owner.increaseEnergy(5);
        //}
    }
}
