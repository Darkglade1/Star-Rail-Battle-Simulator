package lightcones.preservation;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;

public class MomentOfVictory extends AbstractLightcone {

    public MomentOfVictory(int baseHP, int baseAtk, int baseDef, AbstractCharacter owner) {
        super(baseHP, baseAtk, baseDef, owner);
    }

    @Override
    public void onEquip() {
        super.onEquip();
    }
}
