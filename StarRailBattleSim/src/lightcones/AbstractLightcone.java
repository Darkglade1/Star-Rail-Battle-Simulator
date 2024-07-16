package lightcones;

import characters.AbstractCharacter;

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

}
