package battleLogic.log.lines.character;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

public class GainEnergy implements Loggable {
    public final AbstractCharacter character;
    public final float initialEnergy;
    public final float currentEnergy;
    public final float energyGained;

    public GainEnergy(AbstractCharacter character, float initialEnergy, float currentEnergy, float energyGained) {
        this.character = character;
        this.initialEnergy = initialEnergy;
        this.currentEnergy = currentEnergy;
        this.energyGained = energyGained;
    }


    @Override
    public String asString() {
        return String.format("%s gained %.3f Energy (%.3f -> %.3f)", this.character.name, energyGained, initialEnergy, currentEnergy);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
