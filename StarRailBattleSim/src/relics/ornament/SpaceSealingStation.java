package relics.ornament;

import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;
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
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 12, "Space Sealing Station ATK bonus"));
    }

    @Override
    public void onCombatStart() {
        this.owner.addPower(new SpaceSealingStationPower());
    }

    public static class SpaceSealingStationPower extends PermPower {
        public SpaceSealingStationPower() {
            super("Space Sealing Station extra ATK bonus");
        }

        @Override
        public float getConditionalAtkBonus(AbstractCharacter character) {
            if (character.getFinalSpeed() >= 120) {
                return 12;
            }

            return 0;
        }
    }

}
