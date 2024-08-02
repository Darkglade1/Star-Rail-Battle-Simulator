package lightcones;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.PermPower;

import java.util.ArrayList;

public class Memories extends AbstractLightcone {

    public Memories(AbstractCharacter owner) {
        super(953, 423, 397, owner);
    }

    @Override
    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Memories of the Past Stat Bonus";
        statBonus.bonusBreakEffect = 56;
        owner.addPower(statBonus);
    }

    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        character.increaseEnergy(8);
    }

    public String toString() {
        return "Memories of the Past";
    }
}
