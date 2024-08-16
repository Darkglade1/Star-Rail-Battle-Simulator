package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;
import powers.TracePower;

import java.util.ArrayList;

public class Pela extends AbstractCharacter {

    public static final String NAME = "Pela";
    public static final String ULT_DEBUFF_NAME = "Pela Ult Def Reduction";

    public Pela() {
        super(NAME, 988, 547, 463, 105, 80, ElementType.ICE, 110, 100, Path.NIHILITY);

        this.addPower(new TracePower()
                .addStat(PowerStat.ATK_PERCENT, 18)
                .addStat(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 22.4f)
                .addStat(PowerStat.EFFECT_HIT, 10));
        this.hasAttackingUltimate = true;
    }

    public void useSkill() {
        super.useSkill();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);
        BattleHelpers.PreAttackLogic(this, types);

        AbstractEnemy enemy;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            enemy = Battle.battle.enemyTeam.get(middleIndex);
        } else {
            enemy = Battle.battle.enemyTeam.get(0);
        }
        BattleHelpers.hitEnemy(this, enemy, 2.31f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);

        BattleHelpers.PostAttackLogic(this, types);
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
        BattleHelpers.hitEnemy(this, enemy, 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        super.useUltimate();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        BattleHelpers.PreAttackLogic(this, types);

        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            BattleHelpers.hitEnemy(this, enemy, 1.08f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);
            TempPower exposed = TempPower.create(PowerStat.DEFENSE_REDUCTION, 42, 2, ULT_DEBUFF_NAME);
            exposed.type = AbstractPower.PowerType.DEBUFF;
            enemy.addPower(exposed);
        }

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void takeTurn() {
        super.takeTurn();
        if (Battle.battle.numSkillPoints > 0 && firstMove) {
            useSkill();
            firstMove = false;
        } else if (Battle.battle.numSkillPoints >= 4) {
            useSkill();
        } else {
            useBasicAttack();
        }
    }

    public void useTechnique() {
        if (Battle.battle.usedEntryTechnique) {
            return;
        } else {
            Battle.battle.usedEntryTechnique = true;
        }
        ArrayList<DamageType> types = new ArrayList<>();
        BattleHelpers.PreAttackLogic(this, types);

        BattleHelpers.hitEnemy(this, Battle.battle.getRandomEnemy(), 0.8f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);

        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            TempPower techniqueExposed = TempPower.create(PowerStat.DEFENSE_REDUCTION, 20, 2, "Pela Technique Def Reduction");
            techniqueExposed.type = AbstractPower.PowerType.DEBUFF;
            enemy.addPower(techniqueExposed);
        }

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void onCombatStart() {
        addPower(new PelaTalentPower());
        addPower(new PelaBonusDamageAgainstDebuffPower());
    }

    private class PelaTalentPower extends AbstractPower {
        public PelaTalentPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        @Override
        public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<DamageType> types) {
            for (AbstractEnemy enemy : enemiesHit) {
                for (AbstractPower power : enemy.powerList) {
                    if (power.type == PowerType.DEBUFF) {
                        increaseEnergy(11);
                        break;
                    }
                }
            }
        }
    }

    private static class PelaBonusDamageAgainstDebuffPower extends AbstractPower {
        public PelaBonusDamageAgainstDebuffPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }
        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<DamageType> damageTypes) {
            for (AbstractPower power : enemy.powerList) {
                if (power.type == PowerType.DEBUFF) {
                    return 20;
                }
            }
            return 0;
        }
    }
}
