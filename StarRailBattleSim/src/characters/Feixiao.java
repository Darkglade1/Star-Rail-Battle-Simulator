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
import java.util.HashMap;

public class Feixiao extends AbstractCharacter {

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
    }


    public void increaseStack(int amount) {
        int initialStack = stackCount;
        stackCount += amount;
        Battle.battle.addToLog(String.format("%s gained %d Stack (%d -> %d)", name, amount, initialStack, stackCount));
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
        Battle.battle.addToLog(String.format("%s gained %d Energy (%.1f -> %.1f)", name, energyGain, initialEnergy, currentEnergy));
    }

    public void useSkill() {
        super.useSkill();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);
        BattleHelpers.PreAttackLogic(this, types);

        this.addPower(TempPower.create(PowerStat.ATK_PERCENT, 48, 3,"Fei Atk Bonus"));

        AbstractEnemy enemy;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            enemy = Battle.battle.enemyTeam.get(middleIndex);
        } else {
            enemy = Battle.battle.enemyTeam.get(0);
        }

        float totalMult = 2.0f;
        BattleHelpers.hitEnemy(this, enemy, totalMult * 0.34f, BattleHelpers.MultiplierStat.ATK, types, 0);
        BattleHelpers.hitEnemy(this, enemy, totalMult * 0.33f, BattleHelpers.MultiplierStat.ATK, types, 0);
        BattleHelpers.hitEnemy(this, enemy, totalMult * 0.33f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);

        BattleHelpers.PostAttackLogic(this, types);

        ArrayList<AbstractEnemy> enemiesHit = new ArrayList<>();
        enemiesHit.add(enemy);
        useFollowUp(enemiesHit);
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
        BattleHelpers.hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useFollowUp(ArrayList<AbstractEnemy> enemiesHit) {
        int middleIndex = enemiesHit.size() / 2;
        AbstractEnemy enemy = enemiesHit.get(middleIndex);
        moveHistory.add(MoveType.FOLLOW_UP);
        numFUAs++;
        Battle.battle.addToLog(name + " used Follow Up");

        addPower(TempPower.create(PowerStat.DAMAGE_BONUS, 60, 2, "Fei Damage Bonus"));

        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.FOLLOW_UP);
        BattleHelpers.PreAttackLogic(this, types);

        BattleHelpers.hitEnemy(this, enemy, 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_HALF_UNIT);

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        AbstractEnemy enemy;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            enemy = Battle.battle.enemyTeam.get(middleIndex);
        } else {
            enemy = Battle.battle.enemyTeam.get(0);
        }

        boolean shouldUlt = true;

        if (Battle.battle.hasCharacter(Robin.NAME)) {
            if (!this.hasPower(Robin.ULT_POWER_NAME)) {
                shouldUlt = false;
            }
        }

        if (Battle.battle.hasCharacter(Sparkle.NAME)) {
            if (!this.hasPower(Sparkle.SKILL_POWER_NAME) || !this.hasPower(Sparkle.ULT_POWER_NAME)) {
                shouldUlt = false;
            }
        }

        if (Battle.battle.hasCharacter(Bronya.NAME)) {
            if (!this.hasPower(Bronya.SKILL_POWER_NAME) || !this.hasPower(Bronya.ULT_POWER_NAME)) {
                shouldUlt = false;
            }
        }

        if (Battle.battle.hasCharacter(RuanMei.NAME)) {
            if (!this.hasPower(RuanMei.ULT_POWER_NAME)) {
                shouldUlt = false;
            }
        }

        if (Battle.battle.hasCharacter(Pela.NAME)) {
            if (!enemy.hasPower(Pela.ULT_DEBUFF_NAME)) {
                shouldUlt = false;
            }
        }

        if (Battle.battle.hasCharacter(Hanya.NAME)) {
            if (!this.hasPower(Hanya.ULT_BUFF_NAME)) {
                shouldUlt = false;
            }
        }

        if (Battle.battle.hasCharacter(Bronya.NAME) && Battle.battle.hasCharacter(Robin.NAME)) {
            if (this.hasPower(Bronya.SKILL_POWER_NAME) && this.hasPower(Bronya.ULT_POWER_NAME)) {
                shouldUlt = true;
            }
        }

        if (!shouldUlt) {
            return;
        }

        addPower(ultBreakEffBuff);

        int numHits = 6;
        super.useUltimate();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        types.add(DamageType.FOLLOW_UP);
        BattleHelpers.PreAttackLogic(this, types);

        float totalMult = 0.9f;
        for (int i = 0; i < numHits; i++) {
            BattleHelpers.hitEnemy(this, enemy, totalMult * 0.1f, BattleHelpers.MultiplierStat.ATK, types, 0);
            BattleHelpers.hitEnemy(this, enemy, totalMult * 0.9f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_HALF_UNIT);
        }
        BattleHelpers.hitEnemy(this, enemy, 1.6f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_HALF_UNIT);

        BattleHelpers.PostAttackLogic(this, types);
        removePower(ultBreakEffBuff);
    }

    public void takeTurn() {
        super.takeTurn();
        if (Battle.battle.hasCharacter(Bronya.NAME)) {
            if (!this.hasPower(Bronya.SKILL_POWER_NAME)) {
                if (Battle.battle.numSkillPoints >= 4) {
                    useSkill();
                } else {
                    useBasicAttack();
                }
            } else {
                if (Battle.battle.numSkillPoints > 1) {
                    useSkill();
                } else {
                    useBasicAttack();
                }
            }
        } else {
            if (Battle.battle.numSkillPoints > 1) {
                useSkill();
            } else {
                useBasicAttack();
            }
        }
    }

    public void onTurnStart() {
        
        if (currentEnergy >= ultCost) {
            useUltimate(); // check for ultimate activation at start of turn as well
        }
        if (FUAReady) {
            gainStackEnergy(1);
        }
        FUAReady = true;
    }

    public void onCombatStart() {
        gainStackEnergy(3);
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            AbstractPower feiPower = new FeiTalentPower();
            character.addPower(feiPower);
        }
        addPower(new FeiCritDmgPower());
    }

    public void useTechnique() {
        if (Battle.battle.usedEntryTechnique) {
            return;
        } else {
            Battle.battle.usedEntryTechnique = true;
        }
        ArrayList<DamageType> types = new ArrayList<>();
        BattleHelpers.PreAttackLogic(this, types);

        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            BattleHelpers.hitEnemy(this, enemy, 2.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        }

        BattleHelpers.PostAttackLogic(this, types);
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
        public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<DamageType> types) {
            if (!Feixiao.this.hasPower(ultBreakEffBuff.name)) {
                Feixiao.this.increaseStack(1);
            }
        }
        @Override
        public void afterAttackFinish(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<DamageType> types) {
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
        public float getConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    return 36;
                }
            }
            return 0;
        }
    }
}
