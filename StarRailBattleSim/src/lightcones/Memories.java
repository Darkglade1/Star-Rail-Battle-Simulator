package lightcones;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;

import java.util.ArrayList;

public class Memories extends AbstractLightcone {

    public Memories(AbstractCharacter owner) {
        super(953, 423, 397, owner);
    }

    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        character.increaseEnergy(8);
    }

    public String toString() {
        return "Memories of the Past";
    }
}
