package relicSetBonus;

import characters.AbstractCharacter;

public abstract class AbstractRelicSetBonus {
    public AbstractCharacter owner;

    public AbstractRelicSetBonus(AbstractCharacter owner) {
        this.owner = owner;
    }

    public void onEquip() {

    }

    public void onCombatStart() {

    }

}
