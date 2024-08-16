package lightcones.abundance;

import battleLogic.Battle;
import characters.AbstractCharacter;
import lightcones.AbstractLightcone;

import java.util.List;
import java.util.stream.Collectors;

public class QuidProQuo extends AbstractLightcone {

    public QuidProQuo(AbstractCharacter owner) {
        super(953, 423, 397, owner);
    }

    @Override
    public void onTurnStart() {
        List<AbstractCharacter> characters = Battle.battle.playerTeam
                .stream()
                .filter(c -> c.currentEnergy < c.maxEnergy / 2)
                .collect(Collectors.toList());

        if (characters.isEmpty()) return;

        AbstractCharacter target = characters.get(Battle.battle.qpqRng.nextInt(characters.size()));
        target.increaseEnergy(16);
    }
}
