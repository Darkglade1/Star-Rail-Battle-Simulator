package battleLogic.log.lines.character;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

import java.util.ArrayList;

public class TotalDamage implements Loggable {

    private final AbstractCharacter character;
    private final ArrayList<AbstractCharacter.DamageType> types;
    private final int totalDamage;

    public TotalDamage(AbstractCharacter character, ArrayList<AbstractCharacter.DamageType> types, int totalDamage) {
        this.character = character;
        this.types = new ArrayList<>(types);
        this.totalDamage = totalDamage;
    }

    @Override
    public String asString() {
        return String.format("Total Damage: %d", this.totalDamage);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
