package enemies;

import characters.AbstractCharacter;

public class PhysWeakEnemy extends AbstractEnemy {

    public PhysWeakEnemy(int index, int doubleActionCooldown) {
        super("PhysWeakEnemy" + index, 301193, 718, 1150, 150, 95, 300, doubleActionCooldown);
        setRes(AbstractCharacter.ElementType.PHYSICAL, 0);
        weaknessMap.add(AbstractCharacter.ElementType.PHYSICAL);
        this.speedPriority = index;
    }
}
