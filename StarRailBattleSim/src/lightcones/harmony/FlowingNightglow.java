package lightcones.harmony;

import battleLogic.Battle;
import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;

import java.util.ArrayList;


public class FlowingNightglow extends AbstractLightcone {
    public static final String ERPowerName = "FlowingNightglowERRPower";

    // As long as robin has cadenza, everyone has cadenza, so we make this a perm power that we manually add and remove
    private final AbstractPower cadenzaBuff = PermPower.create(PowerStat.DAMAGE_BONUS, 24, "Flowing Nightglow DMG Boost");

    public FlowingNightglow(AbstractCharacter owner) {
        super(953, 635, 463, owner);
    }

    @Override
    public void onCombatStart() {
        AbstractPower power = new FlowingNightglowPower();
        Battle.battle.playerTeam.forEach(c -> c.addPower(power));
    }

    @Override
    public void onEndTurn() {
        Battle.battle.playerTeam.forEach(c -> c.removePower(cadenzaBuff));
    }

    @Override
    public void onUseUltimate() {
        owner.removePower(ERPowerName);
        this.owner.addPower(TempPower.create(PowerStat.ATK_PERCENT, 48, 1, "Flowing Nightglow ATK Boost"));
        Battle.battle.playerTeam.forEach(c -> c.addPower(cadenzaBuff));
    }

    public class FlowingNightglowPower extends PermPower {
        public FlowingNightglowPower() {
            this.name = this.getClass().getSimpleName();
        }
        @Override
        public void onBeforeUseAttack(ArrayList<AbstractCharacter.DamageType> types) {
            FlowingNightglow.this.owner.addPower(new FlowingNightglowERRPower());
        }
    }

    public static class FlowingNightglowERRPower extends PermPower {
        public FlowingNightglowERRPower() {
            this.name = ERPowerName;
            this.maxStacks = 5;
        }
        @Override
        public float getConditionalERR(AbstractCharacter character) {
            return 3 * stacks;
        }
    }

}
