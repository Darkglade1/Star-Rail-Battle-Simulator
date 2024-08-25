package battleLogic.log.lines.character;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;


import java.util.ArrayList;
import java.util.List;

public class TotalDamage implements Loggable {

    public final AbstractCharacter<?> character;
    public final List<AbstractCharacter.DamageType> types;
    public final int totalDamage;

    public TotalDamage(AbstractCharacter<?> character, List<AbstractCharacter.DamageType> types, int totalDamage) {
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
