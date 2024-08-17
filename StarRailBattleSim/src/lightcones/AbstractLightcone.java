package lightcones;

import battleLogic.BattleEvents;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;

import java.util.ArrayList;

public abstract class AbstractLightcone implements BattleEvents {

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

    public void onEquip() {

    }

    public void onSpecificTrigger(AbstractCharacter character, AbstractEnemy enemy) {

    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

}
