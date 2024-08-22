package lightcones.harmony;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import powers.PermPower;

import java.util.ArrayList;

public class DreamvilleAdventure extends AbstractLightcone {

    private AbstractCharacter.DamageType currBenefit;
    AbstractPower childishness = new Childishness();

    public DreamvilleAdventure(AbstractCharacter<?> owner) {
        super(953, 423, 397, owner);
    }

    @Override
    public void onUseSkill() {
        currBenefit = AbstractCharacter.DamageType.SKILL;
    }

    @Override
    public void onUseBasic() {
        currBenefit = AbstractCharacter.DamageType.BASIC;
    }

    @Override
    public void onUseUltimate() {
        currBenefit = AbstractCharacter.DamageType.ULTIMATE;
    }

    @Override
    public void onCombatStart() {
        getBattle().getPlayers().forEach(c -> c.addPower(childishness));
    }

    public class Childishness extends PermPower {
        public Childishness() {
            this.name = this.getClass().getSimpleName();
        }
        @Override
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (currBenefit == null) {
                return 0;
            }
            if (damageTypes.contains(currBenefit)) {
                return 20;
            }
            return 0;
        }
    }
}
