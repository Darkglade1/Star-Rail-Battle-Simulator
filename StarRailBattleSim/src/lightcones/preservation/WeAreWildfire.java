package lightcones.preservation;

import characters.AbstractCharacter;
import lightcones.AbstractLightcone;

public class WeAreWildfire extends AbstractLightcone {

    public WeAreWildfire(AbstractCharacter<?> owner) {
        super(741, 476, 463, owner);
    }

    @Override
    public void onCombatStart() {
        // TODO: DMG reduction & HP regen
    }


}
