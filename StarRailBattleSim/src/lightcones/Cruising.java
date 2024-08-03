package lightcones;

import characters.AbstractCharacter;
import powers.PermPower;

public class Cruising extends AbstractLightcone {

    public Cruising(AbstractCharacter owner) {
        super(953, 529, 463, owner);
    }

    @Override
    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Cruising Stat Bonus";
        statBonus.bonusCritChance = 16;
        owner.addPower(statBonus);
    }

    @Override
    public void onCombatStart() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Cruising Attack Bonus";
        statBonus.bonusAtkPercent = 20;
        owner.addPower(statBonus);
    }

    public String toString() {
        return "Cruising (50% Uptime)";
    }
}
