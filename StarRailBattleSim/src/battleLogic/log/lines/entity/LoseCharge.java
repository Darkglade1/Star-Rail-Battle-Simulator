package battleLogic.log.lines.entity;

import battleLogic.AbstractEntity;
import battleLogic.log.Loggable;
import battleLogic.log.Logger;

public class LoseCharge implements Loggable {

    public final AbstractEntity character;
    public final int amount;
    public final int initialCharge;
    public final int chargeCount;

    public LoseCharge(AbstractEntity character, int amount, int initialCharge, int chargeCount) {
        this.character = character;
        this.amount = amount;
        this.initialCharge = initialCharge;
        this.chargeCount = chargeCount;
    }

    @Override
    public String asString() {
        return String.format("%s loses %d Charge (%d -> %d)", character.name, amount, initialCharge, chargeCount);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
