package enemies;

import characters.AbstractCharacter;

public class FireWindImgWeakEnemy extends AbstractEnemy {

    public FireWindImgWeakEnemy(int index, int doubleActionCooldown) {
        super("FireWindImgWeakEnemy" + index, 301193, 718, 1150, 150, 95, 100, doubleActionCooldown);
        setRes(AbstractCharacter.ElementType.FIRE, 0);
        setRes(AbstractCharacter.ElementType.WIND, 0);
        setRes(AbstractCharacter.ElementType.IMAGINARY, 0);
        weaknessMap.add(AbstractCharacter.ElementType.FIRE);
        weaknessMap.add(AbstractCharacter.ElementType.WIND);
        weaknessMap.add(AbstractCharacter.ElementType.IMAGINARY);
        this.speedPriority = index;
    }
}
