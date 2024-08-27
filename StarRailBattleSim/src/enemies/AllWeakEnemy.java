package enemies;

import characters.AbstractCharacter;

public class AllWeakEnemy extends AbstractEnemy {

    public AllWeakEnemy(int index, int doubleActionCooldown) {
        super("AllWeakEnemy" + index, 301193, 718, 1150, 150, 95, 100, doubleActionCooldown);
        setRes(AbstractCharacter.ElementType.FIRE, 0);
        setRes(AbstractCharacter.ElementType.WIND, 0);
        setRes(AbstractCharacter.ElementType.IMAGINARY, 0);
        setRes(AbstractCharacter.ElementType.LIGHTNING, 0);
        setRes(AbstractCharacter.ElementType.PHYSICAL, 0);
        setRes(AbstractCharacter.ElementType.QUANTUM, 0);
        setRes(AbstractCharacter.ElementType.ICE, 0);
        weaknessMap.add(AbstractCharacter.ElementType.FIRE);
        weaknessMap.add(AbstractCharacter.ElementType.WIND);
        weaknessMap.add(AbstractCharacter.ElementType.IMAGINARY);
        weaknessMap.add(AbstractCharacter.ElementType.LIGHTNING);
        weaknessMap.add(AbstractCharacter.ElementType.PHYSICAL);
        weaknessMap.add(AbstractCharacter.ElementType.QUANTUM);
        weaknessMap.add(AbstractCharacter.ElementType.ICE);
        this.speedPriority = index;
    }
}
