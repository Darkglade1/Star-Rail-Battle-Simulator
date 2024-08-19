package battleLogic.log.lines.battle;

import battleLogic.log.LogLine;
import battleLogic.log.Logger;
import characters.AbstractCharacter;

public class UseSkillPoint extends LogLine {

    private final AbstractCharacter character;
    private final int amount;
    private final int from;
    private final int to;

    public UseSkillPoint(AbstractCharacter character, int amount, int from, int to) {
        this.character = character;
        this.amount = amount;
        this.from = from;
        this.to = to;
    }


    @Override
    public String asString() {
        return String.format("%s used %d Skill Point(s) (%d -> %d)", this.character.name, this.amount, this.from, this.to);
    }

    @Override
    public void handle(Logger logger) {
        logger.handle(this);
    }
}
