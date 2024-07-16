package enemies;

import characters.AbstractCharacter;

public class PhysWeakEnemy extends AbstractEnemy {

    public PhysWeakEnemy(int index) {
        super("PhysWeakEnemy" + index, 301193, 718, 1150, 132, 95, 300);
        setRes(AbstractCharacter.ElementType.PHYSICAL, 0);
        weaknessMap.add(AbstractCharacter.ElementType.PHYSICAL);
    }

    public PhysWeakEnemy() {
        this(0);
    }
}
