package lightcones.harmony;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;


public class FlowingNightglow extends AbstractLightcone {

    private int cantillation = 0;

    public FlowingNightglow(AbstractCharacter owner) {
        super(953, 635, 463, owner);
    }

    @Override
    public void onCombatStart() {
        Battle.battle.playerTeam.forEach(c -> c.addPower(new FlowingNightglowPower(this)));
    }

    @Override
    public void onUseUltimate() {
        this.cantillation = 0;
        this.owner.addPower(TempPower.create(PowerStat.ATK_PERCENT, 48, 1, "Flowing Nightglow ATK Boost"));
        Battle.battle.playerTeam.forEach(c -> c.addPower(TempPower.create(PowerStat.DAMAGE_BONUS, 24, 1, "Flowing Nightglow DMG Boost")));
    }

    public static class FlowingNightglowPower extends PermPower {

        FlowingNightglow lightcone;

        public FlowingNightglowPower(FlowingNightglow lightcone) {
            this.name = this.getClass().getSimpleName();
            this.lightcone = lightcone;
        }

        @Override
        public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            lightcone.cantillation = Math.min(5, lightcone.cantillation + 1);
        }

        @Override
        public float getConditionalERR(AbstractCharacter character) {
            if (character == this.lightcone.owner) {
                return 3 * lightcone.cantillation;
            }

            return 0;
        }
    }

}
