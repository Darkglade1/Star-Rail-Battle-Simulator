package characters.pela;

import battleLogic.BattleHelpers;
import characters.AbstractCharacter;
import characters.Path;
import characters.goal.shared.AlwaysUltGoal;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PowerStat;
import powers.TempPower;
import powers.TracePower;

import java.util.ArrayList;

public class Pela extends AbstractCharacter<Pela> {

    public static final String NAME = "Pela";
    public static final String ULT_DEBUFF_NAME = "Pela Ult Def Reduction";

    public Pela() {
        super(NAME, 988, 547, 463, 105, 80, ElementType.ICE, 110, 100, Path.NIHILITY);

        this.addPower(new TracePower()
                .setStat(PowerStat.ATK_PERCENT, 18)
                .setStat(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 22.4f)
                .setStat(PowerStat.EFFECT_HIT, 10));
        this.hasAttackingUltimate = true;

        this.registerGoal(0, new PelaTurnGoal(this));
        this.registerGoal(0, new AlwaysUltGoal<>(this));
    }

    public void useSkill() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 2.31f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);

        getBattle().getHelper().PostAttackLogic(this, types);
    }
    public void useBasic() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        getBattle().getHelper().PreAttackLogic(this, types);

        for (AbstractEnemy enemy : getBattle().getEnemies()) {
            getBattle().getHelper().hitEnemy(this, enemy, 1.08f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);
            TempPower exposed = TempPower.create(PowerStat.DEFENSE_REDUCTION, 42, 2, ULT_DEBUFF_NAME);
            exposed.type = AbstractPower.PowerType.DEBUFF;
            enemy.addPower(exposed);
        }

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    /*public void takeTurn() {
        super.takeTurn();
        if (getBattle().getSkillPoints() > 0 && firstMove) {
            useSkill();
            firstMove = false;
        } else if (getBattle().getSkillPoints() >= 4) {
            useSkill();
        } else {
            useBasic();
        }
    }*/

    public void useTechnique() {
        if (getBattle().usedEntryTechnique()) {
            return;
        } else {
            getBattle().setUsedEntryTechnique(true);
        }
        ArrayList<DamageType> types = new ArrayList<>();
        getBattle().getHelper().PreAttackLogic(this, types);

        getBattle().getHelper().hitEnemy(this, getBattle().getRandomEnemy(), 0.8f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);

        for (AbstractEnemy enemy : getBattle().getEnemies()) {
            TempPower techniqueExposed = TempPower.create(PowerStat.DEFENSE_REDUCTION, 20, 2, "Pela Technique Def Reduction");
            techniqueExposed.type = AbstractPower.PowerType.DEBUFF;
            enemy.addPower(techniqueExposed);
        }

        getBattle().getHelper().PostAttackLogic(this, types);
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
        public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<DamageType> types) {
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
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<DamageType> damageTypes) {
            for (AbstractPower power : enemy.powerList) {
                if (power.type == PowerType.DEBUFF) {
                    return 20;
                }
            }
            return 0;
        }
    }
}
