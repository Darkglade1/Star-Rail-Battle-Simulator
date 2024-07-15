package relicSetBonus;

import characters.AbstractCharacter;
import powers.PermPower;

public class FullMusketeer extends AbstractRelicSetBonus {
    public FullMusketeer(AbstractCharacter owner) {
        super(owner);
    }

    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "Musketeer Stat Bonus";
        statBonus.bonusAtkPercent = 12;
        statBonus.bonusSpeedPercent = 6;
        owner.addPower(statBonus);
    }

    public void onCombatStart() {

    }

}
