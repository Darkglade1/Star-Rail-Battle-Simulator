package relics;

import battleLogic.BattleEvents;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;

import java.util.ArrayList;

public abstract class AbstractRelicSetBonus implements BattleEvents {
    public AbstractCharacter owner;
    protected boolean isFullSet;

    public AbstractRelicSetBonus(AbstractCharacter owner, boolean fullSet) {
        this.owner = owner;
        this.isFullSet = fullSet;
    }

    public AbstractRelicSetBonus(AbstractCharacter owner) {
        this(owner, true);
    }

    public void onEquip() {

    }

}
