package battleLogic.log.lines.battle;

import battleLogic.log.Loggable;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

public class GenerateSkillPoint implements Loggable {

    public final AbstractCharacter<?> character;
    public final int amount;
    public final int from;
    public final int to;

    public GenerateSkillPoint(AbstractCharacter<?> character, int amount, int from, int to) {
        this.character = character;
        this.amount = amount;
        this.from = from;
        this.to = to;
    }


    @Override
    public String asString() {
        return String.format("%s generated %d Skill Point(s) (%d -> %d)", this.character.name, this.amount, this.from, this.to);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
