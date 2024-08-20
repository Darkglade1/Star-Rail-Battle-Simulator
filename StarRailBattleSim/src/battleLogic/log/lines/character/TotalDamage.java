package battleLogic.log.lines.character;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;


import java.util.ArrayList;
import java.util.List;

public class TotalDamage implements Loggable {

    private final AbstractCharacter character;
    private final List<AbstractCharacter.DamageType> types;
    private final int totalDamage;

    public TotalDamage(AbstractCharacter character, List<AbstractCharacter.DamageType> types, int totalDamage) {
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
