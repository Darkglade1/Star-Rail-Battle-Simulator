package relics.ornament;

import battleLogic.Battle;
import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

public class PenaconyLandOfTheDreams extends AbstractRelicSetBonus {
    public PenaconyLandOfTheDreams(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public PenaconyLandOfTheDreams(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ENERGY_REGEN, 5, "Penacony Land of the Dreams energy regen"));
    }

    @Override
    public void onCombatStart() {
        getBattle().getPlayers().stream()
                .filter(c -> c.elementType == this.owner.elementType)
                .filter(c -> c != this.owner)
                .forEach(c -> c.addPower(PermPower.create(PowerStat.DAMAGE_BONUS, 10, "Penacony Land of the Dreams damage bonus")));
    }
}
