package characters;

import battleLogic.AbstractSummon;
import battleLogic.IBattle;

import java.util.List;

public abstract class AbstractSummoner extends AbstractCharacter{
    public AbstractSummoner(String name, int baseHP, int baseAtk, int baseDef, int baseSpeed, int level, ElementType elementType, float maxEnergy, int tauntValue, Path path) {
        super(name, baseHP, baseAtk, baseDef, baseSpeed, level, elementType, maxEnergy, tauntValue, path);
    }

    public abstract List<AbstractSummon> getSummons();
}
