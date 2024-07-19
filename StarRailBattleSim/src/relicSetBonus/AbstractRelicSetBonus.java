package relicSetBonus;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;

import java.util.ArrayList;

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

    public void onBeforeUseAttack(ArrayList<AbstractCharacter.DamageType> damageTypes) {

    }

    public void onBeforeHitEnemy(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {

    }

}
