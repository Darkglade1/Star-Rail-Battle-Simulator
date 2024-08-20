package lightcones;

import battleLogic.BattleEvents;
import battleLogic.BattleParticipant;
import battleLogic.IBattle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;

import java.util.ArrayList;

public abstract class AbstractLightcone implements BattleEvents,BattleParticipant {

    public int baseHP;
    public int baseAtk;
    public int baseDef;
    public AbstractCharacter owner;

    public AbstractLightcone(int baseHP, int baseAtk, int baseDef, AbstractCharacter owner) {
        this.baseHP = baseHP;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;
        this.owner = owner;
    }

    @Override
    public IBattle getBattle() {
        return this.owner.getBattle();
    }

    public void onEquip() {

    }

    public void onSpecificTrigger(AbstractCharacter character, AbstractEnemy enemy) {

    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

}
