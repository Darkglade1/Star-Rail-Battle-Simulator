package battleLogic.log.lines.entity;

import battleLogic.AbstractEntity;
import battleLogic.log.Loggable;
import battleLogic.log.Logger;

public class GainCharge implements Loggable {

    public final AbstractEntity character;
    public final int amount;
    public final int initialCharge;
    public final int chargeCount;
    public final String charge;

    public GainCharge(AbstractEntity character, int amount, int initialCharge, int chargeCount) {
        this(character, amount, initialCharge, chargeCount, "Charge");
    }

    public GainCharge(AbstractEntity character, int amount, int initialCharge, int chargeCount, String charge) {
        this.character = character;
        this.amount = amount;
        this.initialCharge = initialCharge;
        this.chargeCount = chargeCount;
        this.charge = charge;
    }

    @Override
    public String asString() {
        return String.format("%s gained %d %s (%d -> %d)", character.name, amount, charge, initialCharge, chargeCount);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
