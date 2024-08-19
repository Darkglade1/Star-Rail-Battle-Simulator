package battleLogic.log.lines.character;

import battleLogic.log.LogLine;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

public class GainEnergy extends LogLine {
    private final AbstractCharacter character;
    private final float initialEnergy;
    private final float currentEnergy;
    private final float energyGained;

    public GainEnergy(AbstractCharacter character, float initialEnergy, float energyGained) {
        this.character = character;
        this.initialEnergy = initialEnergy;
        this.currentEnergy = this.character.currentEnergy;
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
