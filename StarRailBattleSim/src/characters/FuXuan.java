package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.TempPower;

import java.util.ArrayList;

public class FuXuan extends AbstractCharacter {
    AbstractPower skillPower = new FuXuanSkillPower();
    private int skillCounter = 0;

    public FuXuan() {
        super("Fu Xuan", 1475, 466, 606, 100, 80, ElementType.QUANTUM, 135, 150);

        this.path = Path.PRESERVATION;

        PermPower tracesPower = new PermPower();
        tracesPower.name = "Traces Stat Bonus";
        tracesPower.bonusHPPercent = 18;
        tracesPower.bonusCritChance = 18.7f;
        tracesPower.bonusEffectRes = 10;
        this.addPower(tracesPower);
        this.hasAttackingUltimate = true;
    }

    public void useSkill() {
        super.useSkill();
        if (skillCounter >= 1) {
            increaseEnergy(20);
        }
        skillCounter = 3;
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            if (!character.hasPower(skillPower.name)) {
                character.addPower(skillPower);
            }
        }
    }
    public void useBasicAttack() {
        super.useBasicAttack();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        BattleHelpers.PreAttackLogic(this, types);

        AbstractEnemy enemy;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            enemy = Battle.battle.enemyTeam.get(middleIndex);
        } else {
            enemy = Battle.battle.enemyTeam.get(0);
        }
        BattleHelpers.hitEnemy(this, enemy, 0.5f, BattleHelpers.MultiplierStat.HP, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        super.useUltimate();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        BattleHelpers.PreAttackLogic(this, types);

        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            BattleHelpers.hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.HP, types, TOUGHNESS_DAMAGE_TWO_UNITS);
        }

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void takeTurn() {
        super.takeTurn();
        if (Battle.battle.numSkillPoints > 0 && skillCounter <= 1) {
            useSkill();
        } else {
            useBasicAttack();
            skillCounter--;
            if (skillCounter <= 0) {
                for (AbstractCharacter character : Battle.battle.playerTeam) {
                    character.removePower(skillPower);
                }
            }
        }
    }

    public void useTechnique() {
        skillCounter = 2;
        skillPower.bonusFlatHP = 0.06f * FuXuan.this.getFinalHP();
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            character.addPower(skillPower);
        }
    }

    private class FuXuanSkillPower extends AbstractPower {
        public FuXuanSkillPower() {
            this.name = this.getClass().getSimpleName();
            lastsForever = true;
            this.bonusCritChance = 12;
        }
    }
}
