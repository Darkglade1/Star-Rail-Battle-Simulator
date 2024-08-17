package relics.ornament;

import characters.AbstractCharacter;
import powers.PermPower;
import relics.AbstractRelicSetBonus;

public class TaliaKingdomOfBanditry extends AbstractRelicSetBonus {
    public TaliaKingdomOfBanditry(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public TaliaKingdomOfBanditry(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(new TaliaKingdomOfBanditryPower());
    }

    public static class TaliaKingdomOfBanditryPower extends PermPower {
        public TaliaKingdomOfBanditryPower() {
            super("Talia Kingdom of Banditry");
        }

        @Override
        public float getConditionalBreakEffectBonus(AbstractCharacter character) {
            if (character.getFinalSpeed() >= 145) {
                return 36;
            }

            return 16;
        }
    }

}
