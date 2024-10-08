package characters.feixiao;

import battleLogic.BattleHelpers;
import battleLogic.log.lines.character.DoMove;
import battleLogic.log.lines.character.GainEnergy;
import battleLogic.log.lines.entity.GainCharge;
import characters.AbstractCharacter;
import characters.Path;
import characters.goal.shared.*;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;
import powers.TracePower;

import java.util.ArrayList;
import java.util.HashMap;

public class Feixiao extends AbstractCharacter<Feixiao> {

    PermPower ultBreakEffBuff = PermPower.create(PowerStat.WEAKNESS_BREAK_EFF, 100, "Fei Ult Break Eff Buff");
    private int numFUAs = 0;
    private int numStacks;
    private int wastedStacks;
    private String numFUAsMetricName = "Follow up Attacks used";
    private String numStacksMetricName = "Amount of Talent Stacks gained";
    private String wastedStacksMetricName = "Amount of overcapped Stacks";
    public int stackCount = 0;
    public final int stackThreshold = 2;
    private boolean FUAReady = true;
    public static final String NAME = "Feixiao";

    public Feixiao() {
        super(NAME, 1048, 602, 388, 112, 80, ElementType.WIND, 12, 75, Path.HUNT);

        this.usesEnergy = false;
        this.currentEnergy = 0;
        this.ultCost = 6;
        this.isDPS = true;
        this.hasAttackingUltimate = true;

        this.addPower(new TracePower()
                .setStat(PowerStat.ATK_PERCENT, 28)
                .setStat(PowerStat.CRIT_CHANCE, 12)
                .setStat(PowerStat.DEF_PERCENT, 12.5f));

        this.registerGoal(0, new FeixiaoBronyaTurnGoal(this));
        this.registerGoal(10, new AlwaysSkillGoal<>(this, 1));

        this.registerGoal(0, new UltAtEndOfBattle<>(this));
        this.registerGoal(10, new BroynaRobinUltGoal<>(this));
        this.registerGoal(20, DontUltMissingPowerGoal.robin(this));
        this.registerGoal(30, DontUltMissingPowerGoal.sparkle(this));
        this.registerGoal(40, DontUltMissingPowerGoal.bronya(this));
        this.registerGoal(50, DontUltMissingPowerGoal.ruanmei(this));
        this.registerGoal(60, DontUltMissingPowerGoal.hanya(this));
        this.registerGoal(70, DontUltMissingDebuffGoal.pela(this));
        this.registerGoal(100, new AlwaysUltGoal<>(this));
    }


    public void increaseStack(int amount) {
        int initialStack = stackCount;
        stackCount += amount;
        getBattle().addToLog(new GainCharge(this, amount, initialStack, stackCount, "Stack"));
        if (stackCount >= stackThreshold) {
            int energyGain = stackCount / stackThreshold;
            gainStackEnergy(energyGain);
        }
    }

    public void gainStackEnergy(int energyGain) {
        numStacks += energyGain;
        float initialEnergy = currentEnergy;
        currentEnergy += energyGain;
        if (currentEnergy > maxEnergy) {
            currentEnergy = maxEnergy;
            wastedStacks += energyGain;
        }
        stackCount = stackCount % stackThreshold;
        getBattle().addToLog(new GainEnergy(this, initialEnergy, this.currentEnergy, energyGain, TALENT_ENERGY_GAIN));
    }

    public void useSkill() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);
        getBattle().getHelper().PreAttackLogic(this, types);

        this.addPower(TempPower.create(PowerStat.ATK_PERCENT, 48, 3,"Fei Atk Bonus"));

        AbstractEnemy enemy = getBattle().getMiddleEnemy();

        float totalMult = 2.0f;
        getBattle().getHelper().hitEnemy(this, enemy, totalMult * 0.34f, BattleHelpers.MultiplierStat.ATK, types, 0);
        getBattle().getHelper().hitEnemy(this, enemy, totalMult * 0.33f, BattleHelpers.MultiplierStat.ATK, types, 0);
        getBattle().getHelper().hitEnemy(this, enemy, totalMult * 0.33f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);

        getBattle().getHelper().PostAttackLogic(this, types);

        ArrayList<AbstractEnemy> enemiesHit = new ArrayList<>();
        enemiesHit.add(enemy);
        useFollowUp(enemiesHit);
    }
    public void useBasic() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useFollowUp(ArrayList<AbstractEnemy> enemiesHit) {
        int middleIndex = enemiesHit.size() / 2;
        AbstractEnemy enemy = enemiesHit.get(middleIndex);
        moveHistory.add(MoveType.FOLLOW_UP);
        numFUAs++;
        getBattle().addToLog(new DoMove(this, MoveType.FOLLOW_UP));

        addPower(TempPower.create(PowerStat.DAMAGE_BONUS, 60, 2, "Fei Damage Bonus"));

        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.FOLLOW_UP);
        getBattle().getHelper().PreAttackLogic(this, types);

        getBattle().getHelper().hitEnemy(this, enemy, 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_HALF_UNIT);

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        AbstractEnemy enemy = getBattle().getMiddleEnemy();

        addPower(ultBreakEffBuff);

        int numHits = 6;
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        types.add(DamageType.FOLLOW_UP);
        getBattle().getHelper().PreAttackLogic(this, types);

        float totalMult = 0.9f;
        for (int i = 0; i < numHits; i++) {
            if (enemy.weaknessBroken) {
                getBattle().getHelper().hitEnemy(this, enemy, totalMult * 0.1f, BattleHelpers.MultiplierStat.ATK, types, 0);
                getBattle().getHelper().hitEnemy(this, enemy, totalMult * 0.9f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_HALF_UNIT);
            } else {
                getBattle().getHelper().hitEnemy(this, enemy, totalMult, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_HALF_UNIT);
            }
        }
        getBattle().getHelper().hitEnemy(this, enemy, 1.6f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_HALF_UNIT);

        getBattle().getHelper().PostAttackLogic(this, types);
        removePower(ultBreakEffBuff);
    }

    public void onTurnStart() {
        if (currentEnergy >= ultCost) {
            tryUltimate(); // check for ultimate activation at start of turn as well
        }
        if (FUAReady) {
            increaseStack(1);
        }
        FUAReady = true;
    }

    public void onCombatStart() {
        gainStackEnergy(3);
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            AbstractPower feiPower = new FeiTalentPower();
            character.addPower(feiPower);
        }
        addPower(new FeiCritDmgPower());
    }

    public void useTechnique() {
        if (getBattle().usedEntryTechnique()) {
            return;
        } else {
            getBattle().setUsedEntryTechnique(true);
        }
        ArrayList<DamageType> types = new ArrayList<>();
        getBattle().getHelper().PreAttackLogic(this, types);

        for (AbstractEnemy enemy : getBattle().getEnemies()) {
            getBattle().getHelper().hitEnemy(this, enemy, 2.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        }

        getBattle().getHelper().PostAttackLogic(this, types);
        gainStackEnergy(1);
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(numFUAsMetricName, String.valueOf(numFUAs));
        map.put(numStacksMetricName, String.valueOf(numStacks));
        map.put(wastedStacksMetricName, String.valueOf(wastedStacks));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(numFUAsMetricName);
        list.add(numStacksMetricName);
        list.add(wastedStacksMetricName);
        return list;
    }

    public HashMap<String, String> addLeftoverCharacterEnergyMetric(HashMap<String, String> metricMap) {
        metricMap.put(leftoverEnergyMetricName, String.format("%.2f (Flying Aureus)", this.currentEnergy));
        return metricMap;
    }

    private class FeiTalentPower extends AbstractPower {
        public FeiTalentPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        @Override
        public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<DamageType> types) {
            if (!Feixiao.this.hasPower(ultBreakEffBuff.name)) {
                Feixiao.this.increaseStack(1);
            }
        }
        @Override
        public void afterAttackFinish(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<DamageType> types) {
            if (!(character instanceof Feixiao)) {
                if (FUAReady) {
                    FUAReady = false;
                    Feixiao.this.useFollowUp(enemiesHit);
                }
            }
        }
    }

    private class FeiCritDmgPower extends AbstractPower {
        public FeiCritDmgPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    return 36;
                }
            }
            return 0;
        }
    }
}
