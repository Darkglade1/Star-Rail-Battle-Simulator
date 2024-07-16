package relicSetBonus;

import characters.AbstractCharacter;

public abstract class AbstractRelicSetBonus {
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

    public void onCombatStart() {

    }

}
