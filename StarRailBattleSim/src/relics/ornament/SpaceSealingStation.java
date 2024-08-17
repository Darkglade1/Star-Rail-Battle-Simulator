package relics.ornament;

import characters.AbstractCharacter;
import powers.PermPower;
import relics.AbstractRelicSetBonus;

public class SpaceSealingStation extends AbstractRelicSetBonus {
    public SpaceSealingStation(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public SpaceSealingStation(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(new SpaceSealingStationPower());
    }

    public static class SpaceSealingStationPower extends PermPower {
        public SpaceSealingStationPower() {
            super("Space Sealing Station ATK bonus");
        }

        @Override
        public float getConditionalAtkBonus(AbstractCharacter character) {
            if (character.getFinalSpeed() >= 120) {
                return 24;
            }

            return 12;
        }
    }

}
