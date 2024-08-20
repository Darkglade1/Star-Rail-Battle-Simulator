package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import battleLogic.IBattle;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TracePower;

import java.util.ArrayList;

public class Sparkle extends AbstractCharacter {

    public static final String NAME = "Sparkle";
    public static final String SKILL_POWER_NAME = "SparkleSkillPower";
    public static final String ULT_POWER_NAME = "SparkleUltPower";

    public Sparkle() {
        super(NAME, 1397, 524, 485, 101, 80, ElementType.QUANTUM, 110, 100, Path.HARMONY);

        this.basicEnergyGain = 30;
        this.addPower(new TracePower()
                .setStat(PowerStat.CRIT_DAMAGE, 24)
                .setStat(PowerStat.HP_PERCENT, 28)
                .setStat(PowerStat.EFFECT_RES, 10));
    }

    public void useSkill() {
        super.useSkill();
        AbstractPower skillPower = new SparkleSkillPower();
        for (AbstractCharacter character : getBattle().getPlayers()) {
            if (character.isDPS) {
                character.removePower(skillPower.name); // remove the old power in case sparkle's crit damage changed so we get new snapshot of her buff
                character.addPower(skillPower);
                getBattle().AdvanceEntity(character, 50);
                lightcone.onSpecificTrigger(character, null);
            }
        }
    }
    public void useBasicAttack() {
        super.useBasicAttack();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        super.useUltimate();
        getBattle().generateSkillPoint(this, 4);
        for (AbstractCharacter character : getBattle().getPlayers()) {
            AbstractPower ultPower = new SparkleUltPower();
            character.addPower(ultPower);
        }
    }

    public void takeTurn() {
        super.takeTurn();
        if (getBattle().getSkillPoints() > 0) {
            useSkill();
        } else {
            useBasicAttack();
        }
    }

    public void onCombatStart() {
        getBattle().increaseMaxSkillPoints(2);
        int numQuantumAllies = 0;
        for (AbstractCharacter character : getBattle().getPlayers()) {
            PermPower nocturne = PermPower.create(PowerStat.ATK_PERCENT, 15, "Sparkle Atk Bonus");
            character.addPower(nocturne);
            if (character.elementType == ElementType.QUANTUM) {
                numQuantumAllies++;
            }
        }
        int quantumAtkBonus;
        if (numQuantumAllies == 3) {
            quantumAtkBonus = 30;
        } else if (numQuantumAllies == 2) {
            quantumAtkBonus = 15;
        } else {
            quantumAtkBonus = 5;
        }
        for (AbstractCharacter character : getBattle().getPlayers()) {
            if (character.elementType == ElementType.QUANTUM) {
                PermPower quantumNocturne = PermPower.create(PowerStat.ATK_PERCENT, quantumAtkBonus, "Sparkle Quantum Atk Bonus");
                character.addPower(quantumNocturne);
            }
        }
    }

    public void useTechnique() {
        getBattle().generateSkillPoint(this, 3);
    }

    private class SparkleSkillPower extends PermPower {
        public SparkleSkillPower() {
            super(SKILL_POWER_NAME);
            this.justApplied = true;
            this.setStat(PowerStat.CRIT_DAMAGE, (getTotalCritDamage() * 0.24f) + 45);
        }

        @Override
        public void onTurnStart() {
            if (justApplied) {
                justApplied = false;
            } else {
                owner.removePower(this);
            }
        }
    }

    public static class SparkleTalentPower extends AbstractPower {
        public SparkleTalentPower() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 2;
            this.maxStacks = 3;
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<DamageType> damageTypes) {
            AbstractPower ultPower = new SparkleUltPower();
            if (character.hasPower(ultPower.name)) {
                return stacks * 16;
            } else {
                return stacks * 6;
            }
        }
    }

    private static class SparkleUltPower extends AbstractPower {
        public SparkleUltPower() {
            this.name = ULT_POWER_NAME;
            this.turnDuration = 2;
        }
    }
}
