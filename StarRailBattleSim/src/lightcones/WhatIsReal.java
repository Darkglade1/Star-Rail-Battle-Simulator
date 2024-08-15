package lightcones;

import characters.AbstractCharacter;
import powers.PermPower;

public class WhatIsReal extends AbstractLightcone {

    public WhatIsReal(AbstractCharacter owner) {
        super(1058, 423, 331, owner);
    }

    @Override
    public void onEquip() {
        PermPower statBonus = new PermPower();
        statBonus.name = "What is Real Stat Bonus";
        statBonus.bonusBreakEffect = 48;
        owner.addPower(statBonus);
    }

    public String toString() {
        return "What is Real";
    }
}
