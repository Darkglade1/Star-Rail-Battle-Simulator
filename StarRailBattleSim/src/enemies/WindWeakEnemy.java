package enemies;

import characters.AbstractCharacter;

public class WindWeakEnemy extends AbstractEnemy {

    public WindWeakEnemy(int index, int doubleActionCooldown) {
        super("WindWeakEnemy" + index, 301193, 718, 1150, 150, 95, 100, doubleActionCooldown);
        setRes(AbstractCharacter.ElementType.WIND, 0);
        weaknessMap.add(AbstractCharacter.ElementType.WIND);
        this.speedPriority = index;
    }
}
