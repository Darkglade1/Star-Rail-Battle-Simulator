package lightcones.preservation;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;

// TODO: The wave boost isn't implemented yet, doesn't seem like Battle has a concept of waves
public class SheAlreadyShutHerEyes extends AbstractLightcone {

    public SheAlreadyShutHerEyes(AbstractCharacter owner) {
        super(1270, 423, 529, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.HP_PERCENT, 24, "She Already Shut Her Eyes HP Boost"));
        this.owner.addPower(PermPower.create(PowerStat.ENERGY_REGEN, 12, "She Already Shut Her Eyes Energy Regen Boost"));
    }

    @Override
    public void onAttacked(AbstractCharacter c, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyFromAttacked) {
        // TODO: Check if owner has shield, and if it has more than the attack
        // if (...) return;

        for (AbstractCharacter character : Battle.battle.playerTeam) {
            character.addPower(TempPower.create(PowerStat.DAMAGE_BONUS, 9, 2, "She Already Shut Her Eyes Damage Boost"));
        }
    }

}
