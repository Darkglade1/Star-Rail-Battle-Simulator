package enemies;

import characters.AbstractCharacter;

public class PhysFireWeakEnemy extends AbstractEnemy {

    public PhysFireWeakEnemy(int index, int doubleActionCooldown) {
        super("PhysFireWeakEnemy" + index, 301193, 718, 1150, 150, 95, 300, doubleActionCooldown);
        setRes(AbstractCharacter.ElementType.PHYSICAL, 0);
        setRes(AbstractCharacter.ElementType.FIRE, 0);
        weaknessMap.add(AbstractCharacter.ElementType.PHYSICAL);
        weaknessMap.add(AbstractCharacter.ElementType.FIRE);
        this.speedPriority = index;
    }
}
