package relics;

import battleLogic.BattleEvents;
import battleLogic.BattleParticipant;
import battleLogic.IBattle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;

import java.util.ArrayList;

public abstract class AbstractRelicSetBonus implements BattleEvents,BattleParticipant {
    public AbstractCharacter owner;
    protected boolean isFullSet;

    public AbstractRelicSetBonus(AbstractCharacter owner, boolean fullSet) {
        this.owner = owner;
        this.isFullSet = fullSet;
    }

    @Override
    public IBattle getBattle() {
        return this.owner.getBattle();
    }

    public AbstractRelicSetBonus(AbstractCharacter owner) {
        this(owner, true);
    }

    public void onEquip() {

    }

    public boolean isFullSet() {
        return isFullSet;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

}
