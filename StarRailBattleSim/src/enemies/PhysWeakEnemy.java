package enemies;

import characters.AbstractCharacter;

public class PhysWeakEnemy extends AbstractEnemy {

    public PhysWeakEnemy() {
        super("PhysWeakEnemy", 301193, 718, 1150, 132, 95, 300);
        setRes(AbstractCharacter.ElementType.PHYSICAL, 0);
    }
}
