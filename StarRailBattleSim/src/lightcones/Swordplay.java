package lightcones;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;

import java.util.ArrayList;

public class Swordplay extends AbstractLightcone {

    private AbstractEnemy target;

    public Swordplay(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    public void onBeforeHitEnemy(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types) {
        AbstractPower swordPlayDamagePower = new SwordplayDamagePower();
        if (target != enemy) {
            owner.removePower(swordPlayDamagePower.name);
            target = enemy;
        }
        owner.addPower(swordPlayDamagePower);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    private static class SwordplayDamagePower extends AbstractPower {
        public SwordplayDamagePower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
            this.maxStacks = 5;
        }
        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            return 16 * stacks;
        }
    }
}
