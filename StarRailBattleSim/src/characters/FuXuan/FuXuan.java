package characters.FuXuan;

import battleLogic.BattleHelpers;
import characters.AbstractCharacter;
import characters.Path;
import characters.goal.shared.AlwaysUltGoal;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class FuXuan extends AbstractCharacter<FuXuan> {
    public static String NAME = "Fu Xuan";
    
    AbstractPower skillPower = PermPower.create(PowerStat.CRIT_CHANCE, 12, "Fu Xuan Skill Power");
    int skillCounter = 0;

    public FuXuan() {
        super(NAME, 1475, 466, 606, 100, 80, ElementType.QUANTUM, 135, 150, Path.PRESERVATION);

        PermPower tracesPower = new PermPower();
        tracesPower.name = "Traces Stat Bonus";
        tracesPower.setStat(PowerStat.HP_PERCENT, 18);
        tracesPower.setStat(PowerStat.CRIT_CHANCE, 18.7f);
        tracesPower.setStat(PowerStat.EFFECT_RES, 10);
        this.addPower(tracesPower);
        this.hasAttackingUltimate = true;

        this.registerGoal(0, new AlwaysUltGoal<>(this));
        this.registerGoal(0, new FuXuanTurnGoal(this));
    }

    public void useSkill() {
        if (skillCounter >= 1) {
            increaseEnergy(20);
        }
        skillCounter = 3;
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            if (!character.hasPower(skillPower.name)) {
                character.addPower(skillPower);
            }
        }
    }
    public void useBasic() {
        skillCounter--;
        if (skillCounter <= 0) {
            for (AbstractCharacter<?> character : getBattle().getPlayers()) {
                character.removePower(skillPower);
            }
        }
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 0.5f, BattleHelpers.MultiplierStat.HP, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        getBattle().getHelper().PreAttackLogic(this, types);

        for (AbstractEnemy enemy : getBattle().getEnemies()) {
            getBattle().getHelper().hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.HP, types, TOUGHNESS_DAMAGE_TWO_UNITS);
        }

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useTechnique() {
        skillCounter = 2;
        skillPower.setStat(PowerStat.FLAT_HP, 0.06f * FuXuan.this.getFinalHP());
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.addPower(skillPower);
        }
    }
}
