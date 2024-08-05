package lightcones;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;

import java.util.ArrayList;

public class Multiplication extends AbstractLightcone {

    public Multiplication(AbstractCharacter owner) {
        super(953, 318, 198, owner);
    }

    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        if (types.contains(AbstractCharacter.DamageType.BASIC)) {
            Battle.battle.AdvanceEntity(character, 20);
        }
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
