package lightcones;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;

import java.util.ArrayList;

public abstract class AbstractLightcone {

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

    public void onCombatStart() {

    }

    public void onUseUltimate() {

    }

    public void onTurnStart() {

    }

    public void onBeforeUseAttack(ArrayList<AbstractCharacter.DamageType> types) {

    }

    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {

    }

    public void onAttacked(AbstractEnemy enemy) {

    }

    public void onBeforeHitEnemy(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types) {

    }

    public void onSpecificTrigger(AbstractCharacter character, AbstractEnemy enemy) {

    }

    public void onEndTurn() {

    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

}
