package relicSetBonus;

import battleLogic.Battle;
import characters.AbstractCharacter;
import powers.PermPower;

public class BrokenKeel extends AbstractRelicSetBonus {
    public BrokenKeel(AbstractCharacter owner) {
        super(owner);
    }

    public void onCombatStart() {
        PermPower power = new PermPower();
        power.bonusCritDamage = 10;
        power.name = "Broken Keel Crit Dmg Bonus";
        power.maxStacks = 999;
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            character.addPower(power);
        }
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

}
