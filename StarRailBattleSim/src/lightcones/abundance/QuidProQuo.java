package lightcones.abundance;

import battleLogic.Battle;
import characters.AbstractCharacter;
import lightcones.AbstractLightcone;

public class QuidProQuo extends AbstractLightcone {

    public QuidProQuo(AbstractCharacter owner) {
        super(953, 423, 397, owner);
    }

    @Override
    public void onTurnStart() {
        Battle.battle.playerTeam
                .stream()
                .filter(c -> c.usesEnergy)
                .filter(c -> c.currentEnergy < c.maxEnergy / 2)
                .findAny() // Not sure if this is random enough
                .ifPresent(c -> c.increaseEnergy(16));
    }
}
