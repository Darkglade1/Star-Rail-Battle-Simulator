package characters.drRatio;

import battleLogic.BattleHelpers;
import battleLogic.log.lines.character.DoMove;
import characters.AbstractCharacter;
import characters.Path;
import characters.goal.shared.*;
import enemies.AbstractEnemy;
import powers.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DrRatio extends AbstractCharacter<DrRatio> {
    private int numFUAs = 0;
    private final String numFUAsMetricName = "Follow up Attacks used";
    public static final String NAME = "Dr. Ratio";

    public DrRatio() {
        super(NAME, 1048, 776, 461, 103, 80, ElementType.IMAGINARY, 140, 75, Path.HUNT);
        this.isDPS = true;
        this.hasAttackingUltimate = true;

        this.addPower(new TracePower()
                .setStat(PowerStat.ATK_PERCENT, 28)
                .setStat(PowerStat.CRIT_CHANCE, 12)
                .setStat(PowerStat.DEF_PERCENT, 12.5f));

        this.registerGoal(10, new AlwaysSkillGoal<>(this, 1));

        this.registerGoal(0, new UltAtEndOfBattle<>(this));
        this.registerGoal(20, DontUltMissingPowerGoal.robin(this));
        this.registerGoal(100, new AlwaysUltGoal<>(this));
    }

    public void useSkill() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();

        enemy.addPower(TempPower.createDebuff(PowerStat.EFFECT_RES, -10, 2, "RatioEffectResDebuff"));
        int debuffs = (int)enemy.powerList.stream().filter(p -> p.type == AbstractPower.PowerType.DEBUFF).count();
        for (int i = 0; i < debuffs; i++) {
            addPower(new Summation());
        }
        getBattle().getHelper().hitEnemy(this, enemy, 1.5f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);

        getBattle().getHelper().PostAttackLogic(this, types);

        // Assume he always gets the FUA from his Skill
        useFollowUp(enemy);
    }
    public void useBasic() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useFollowUp(AbstractEnemy enemy) {
        moveHistory.add(MoveType.FOLLOW_UP);
        numFUAs++;
        getBattle().addToLog(new DoMove(this, MoveType.FOLLOW_UP));
        increaseEnergy(5, FUA_ENERGY_GAIN);

        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.FOLLOW_UP);
        getBattle().getHelper().PreAttackLogic(this, types);

        getBattle().getHelper().hitEnemy(this, enemy, 2.7f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        AbstractEnemy enemy = getBattle().getMiddleEnemy();

        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        getBattle().getHelper().PreAttackLogic(this, types);

        getBattle().getHelper().hitEnemy(this, enemy, 2.4f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_THREE_UNITs);
        enemy.addPower(new WisemanFolly());

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void onCombatStart() {
        addPower(new Deduction());
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(numFUAsMetricName, String.valueOf(numFUAs));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(numFUAsMetricName);
        return list;
    }

    private class WisemanFolly extends PermPower {
        private int numCharges = 2;
        public WisemanFolly() {
            this.name = this.getClass().getSimpleName();
        }

        @Override
        public void onAttacked(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyFromAttacked) {
            if (character != DrRatio.this) {
                if (numCharges > 0) {
                    numCharges--;
                    DrRatio.this.useFollowUp(enemy);
                    if (numCharges <= 0) {
                        enemy.removePower(this);
                    }
                }
            }
        }
    }

    private static class Deduction extends PermPower {
        public Deduction() {
            this.name = this.getClass().getSimpleName();
        }

        @Override
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            int debuffs = Math.min(5, (int) enemy.powerList.stream().filter(p -> p.type == PowerType.DEBUFF).count());
            if (debuffs < 3) {
                return 0;
            }
            return debuffs * 10;
        }
    }

    private static class Summation extends PermPower {
        public Summation() {
            this.name = this.getClass().getSimpleName();
            this.maxStacks = 6;
        }

        @Override
        public float getConditionalCritRate(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            return stacks * 2.5f;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            return stacks * 5;
        }
    }
}
