package lightcones;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.TempPower;

import java.util.ArrayList;

public class Resolution extends AbstractLightcone {

    public Resolution(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
        for (AbstractEnemy enemy : enemiesHit) {
            TempPower exposed = new TempPower();
            exposed.turnDuration = 1;
            exposed.defenseReduction = 16;
            exposed.name = "Resolution Def Reduction";
            exposed.type = AbstractPower.PowerType.DEBUFF;
            enemy.addPower(exposed);
        }
    }

    public String toString() {
        return "Resolution Shines as Pearls of Sweat";
    }
}
