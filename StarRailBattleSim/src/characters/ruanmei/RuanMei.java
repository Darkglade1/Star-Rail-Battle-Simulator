package characters.ruanmei;

import battleLogic.BattleHelpers;
import characters.AbstractCharacter;
import characters.Path;
import characters.goal.shared.AlwaysUltGoal;
import characters.goal.shared.SkillCounterTurnGoal;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TracePower;

import java.util.ArrayList;

public class RuanMei extends AbstractCharacter<RuanMei> implements SkillCounterTurnGoal.SkillCounterCharacter {
    PermPower skillPower;
    AbstractPower ultPower = new RuanMeiUltPower();
    private int skillCounter = 0;
    private int ultCounter = 0;
    public static final String NAME = "Ruan Mei";
    public static final String SKILL_POWER_NAME = "RuanMeiSkillPower";
    public static final String ULT_POWER_NAME = "RuanMeiUltPower";
    public static final String ULT_DEBUFF_NAME = "RuanMeiUltDebuff";

    public RuanMei() {
        super(NAME, 1087, 660, 485, 104, 80, ElementType.ICE, 130, 100, Path.HARMONY);

        this.addPower(new TracePower()
                .setStat(PowerStat.BREAK_EFFECT, 37.3f)
                .setStat(PowerStat.DEF_PERCENT, 22.5f)
                .setStat(PowerStat.FLAT_SPEED, 5));

        this.skillPower = (PermPower) new PermPower(SKILL_POWER_NAME)
                .setStat(PowerStat.DAMAGE_BONUS, 68)
                .setStat(PowerStat.WEAKNESS_BREAK_EFF, 50);

        this.registerGoal(0, new AlwaysUltGoal<>(this));
        this.registerGoal(0, new SkillCounterTurnGoal<>(this));
    }

    public void useSkill() {
        skillCounter = 3;
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.addPower(skillPower);
        }
    }
    public void useBasic() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        ultCounter = 2;
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.addPower(ultPower);
        }
    }

    public void onTurnStart() {
        increaseEnergy(5, TRACE_ENERGY_GAIN);
        if (skillCounter > 0) {
            skillCounter--;
            if (skillCounter <= 0) {
                for (AbstractCharacter<?> character : getBattle().getPlayers()) {
                    character.removePower(skillPower);
                }
            }
        }
        if (ultCounter > 0) {
            ultCounter--;
            if (ultCounter <= 0) {
                for (AbstractCharacter<?> character : getBattle().getPlayers()) {
                    character.removePower(ultPower);
                }
            }
        }
    }

    public void onCombatStart() {
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.addPower(PermPower.create(PowerStat.BREAK_EFFECT, 20, "Ruan Mei Break Buff"));

            if (character != this) {
                character.addPower(PermPower.create(PowerStat.SPEED_PERCENT, 10, "Ruan Mei Speed Buff"));
            }
        }
    }

    public void onWeaknessBreak(AbstractEnemy enemy) {
        getBattle().getHelper().breakDamageHitEnemy(this, enemy, 1.2f);
    }

    public void useTechnique() {
        this.skillSequence();
        getBattle().generateSkillPoint(this, 1);
    }

    @Override
    public int getSkillCounter() {
        return skillCounter;
    }

    public static class RuanMeiUltDebuff extends AbstractPower {

        public boolean triggered = false;
        public AbstractCharacter<?> owner;
        public RuanMeiUltDebuff(AbstractCharacter<?> owner) {
            this.name = ULT_DEBUFF_NAME;
            this.lastsForever = true;
            this.type = PowerType.DEBUFF;
            this.owner = owner;
        }
    }

    private class RuanMeiUltPower extends PermPower {
        public RuanMeiUltPower() {
            super(ULT_POWER_NAME);
            this.setStat(PowerStat.RES_PEN, 25);
        }

        @Override
        public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            for (AbstractEnemy enemy : enemiesHit) {
                if (!enemy.hasPower(ULT_DEBUFF_NAME)) {
                    AbstractPower debuff = new RuanMeiUltDebuff(RuanMei.this);
                    enemy.addPower(debuff);
                }
            }
        }
    }
}
