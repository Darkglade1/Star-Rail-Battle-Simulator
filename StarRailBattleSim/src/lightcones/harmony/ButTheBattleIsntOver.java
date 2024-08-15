package lightcones.harmony;

import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;

/**
 * This lightcone assumes onSpecificTrigger is called when the character uses their skill, and that
 * the passed character is next to play
 */
public class ButTheBattleIsntOver extends AbstractLightcone  {

    public ButTheBattleIsntOver(AbstractCharacter owner) {
        super(1164, 529, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ENERGY_REGEN, 10, "But The Battle Isn't Over Energy Regen Boost"));
    }

    public void onSpecificTrigger(AbstractCharacter character, AbstractEnemy enemy) {
        if (character == null) return;

        character.addPower(new BattleIsntOverBoost());
    }

    @Override
    public void onUseUltimate() {
        if (this.owner.numTurnsMetric % 2 == 0) {
            Battle.battle.generateSkillPoint(this.owner, 1);
        }
    }

    public static class BattleIsntOverBoost extends AbstractPower {
        public BattleIsntOverBoost() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 1;
            this.bonusDamageBonus = 30;
        }
    }
}
