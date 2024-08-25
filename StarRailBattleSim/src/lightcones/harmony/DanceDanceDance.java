package lightcones.harmony;

import battleLogic.Battle;
import characters.AbstractCharacter;
import lightcones.AbstractLightcone;

public class DanceDanceDance extends AbstractLightcone {

    public DanceDanceDance(AbstractCharacter<?> owner) {
        super(953, 423, 397, owner);
    }

    @Override
    public void onUseUltimate() {
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            getBattle().AdvanceEntity(character, 24);
        }
    }
}
