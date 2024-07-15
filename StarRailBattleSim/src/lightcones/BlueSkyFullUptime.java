package lightcones;

import characters.AbstractCharacter;
import powers.PermPower;

public class BlueSkyFullUptime extends AbstractLightcone {

    public BlueSkyFullUptime(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    @Override
    public void onEquip() {
        PermPower atkBonus = new PermPower();
        atkBonus.name = "Blue Sky Atk Bonus";
        atkBonus.bonusAtkPercent = 32;
        owner.addPower(atkBonus);
    }

    @Override
    public void onCombatStart() {
        PermPower critBonus = new PermPower();
        critBonus.name = "Blue Sky Crit Bonus";
        critBonus.bonusCritChance = 24;
        owner.addPower(critBonus);
    }
}
