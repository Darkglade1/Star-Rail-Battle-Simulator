package relics.ornament;

import battleLogic.Battle;
import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

public class FleetOfTheAgeless extends AbstractRelicSetBonus {
    public FleetOfTheAgeless(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public FleetOfTheAgeless(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.HP_PERCENT, 12, "Fleet of the Ageless HP bonus"));
    }

    @Override
    public void onCombatStart() {
        Battle.battle.playerTeam.forEach(c -> c.addPower(new FleetOfTheAgelessPower()));
    }

    public class FleetOfTheAgelessPower extends PermPower {
        public FleetOfTheAgelessPower() {
            super("Fleet of the Ageless ATK bonus");
        }

        @Override
        public float getConditionalAtkBonus(AbstractCharacter character) {
            if (FleetOfTheAgeless.this.owner.getFinalSpeed() >= 120) {
                return 8;
            }

            return 0;
        }
    }
}
