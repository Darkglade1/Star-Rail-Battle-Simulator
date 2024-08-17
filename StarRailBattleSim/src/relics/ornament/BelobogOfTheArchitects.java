package relics.ornament;

import characters.AbstractCharacter;
import powers.PermPower;
import relics.AbstractRelicSetBonus;

public class BelobogOfTheArchitects extends AbstractRelicSetBonus {
    public BelobogOfTheArchitects(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public BelobogOfTheArchitects(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(new BelobogOfTheArchitectsPower());
    }

    public static class BelobogOfTheArchitectsPower extends PermPower {
        public BelobogOfTheArchitectsPower() {
            super("Belobog of the Architects DEF bonus");
        }

        @Override
        public float getConditionalDefenseBonus(AbstractCharacter character) {
            if (character.getTotalEHR() >= 50) {
                return 30;
            }

            return 15;
        }
    }
}
