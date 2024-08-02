package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;

import java.util.ArrayList;
import java.util.HashMap;

public class Feixiao extends AbstractCharacter {

    PermPower ultBreakEffBuff;
    private int numFUAs = 0;
    private int numStacks;
    private int wastedStacks;
    private String numFUAsMetricName = "Follow up Attacks used";
    private String numStacksMetricName = "Amount of Talent Stacks gained";
    private String wastedStacksMetricName = "Amount of overcapped Stacks";
    public int stackCount = 0;
    public final int stackThreshold = 2;
    private boolean FUAReady = true;
    private static final int REAL_ULT_COST = 6;

    public Feixiao() {
        super("Feixiao", 1048, 602, 388, 125, 80, ElementType.WIND, 12, 75);
        this.currentEnergy = 0;
        this.ultCost = REAL_ULT_COST;
        PermPower tracesPower = new PermPower();
        tracesPower.name = "Traces Stat Bonus";
        tracesPower.bonusAtkPercent = 28f;
        tracesPower.bonusCritChance = 12f;
        tracesPower.bonusFlatSpeed = 5;
        this.addPower(tracesPower);
        this.isDPS = true;

        ultBreakEffBuff = new PermPower();
        ultBreakEffBuff.bonusWeaknessBreakEff = 100;
        ultBreakEffBuff.name = "Fei Ult Break Eff Buff";
    }

    // override normal energy gain to do nothing
    public void increaseEnergy(float amount, boolean ERRAffected) {

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

        AbstractEnemy enemy;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            enemy = Battle.battle.enemyTeam.get(middleIndex);
        } else {
            enemy = Battle.battle.enemyTeam.get(0);
        }
        BattleHelpers.hitEnemy(this, enemy, 2.4f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);
        Battle.battle.AdvanceEntity(this, 10);

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
        BattleHelpers.hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useFollowUp(ArrayList<AbstractEnemy> enemiesHit) {
        if (FUAReady) {
            int middleIndex = enemiesHit.size() / 2;
            AbstractEnemy enemy = enemiesHit.get(middleIndex);
            FUAReady = false;
            moveHistory.add(MoveType.FOLLOW_UP);
            numFUAs++;
            Battle.battle.addToLog(name + " used Follow Up");

            ArrayList<DamageType> types = new ArrayList<>();
            types.add(DamageType.FOLLOW_UP);
            BattleHelpers.PreAttackLogic(this, types);

            BattleHelpers.hitEnemy(this, enemy, 2.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_HALF_UNIT);

            BattleHelpers.PostAttackLogic(this, types);
        }
    }

    public void useUltimate() {
        AbstractEnemy enemy;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            enemy = Battle.battle.enemyTeam.get(middleIndex);
        } else {
            enemy = Battle.battle.enemyTeam.get(0);
        }

        if (!canBreakEnemy(enemy)) {
            return;
        }

        if (Battle.battle.hasCharacter(Robin.NAME)) {
            if (!this.hasPower(Robin.ULT_POWER_NAME)) {
                return;
            }
        }

        if (Battle.battle.hasCharacter(Sparkle.NAME)) {
            if (!this.hasPower(Sparkle.SKILL_POWER_NAME) || !this.hasPower(Sparkle.ULT_POWER_NAME)) {
                return;
            }
        }

        if (Battle.battle.hasCharacter(RuanMei.NAME)) {
            if (!this.hasPower(RuanMei.ULT_POWER_NAME)) {
                return;
            }
        }

        addPower(ultBreakEffBuff);

        int numHits = (int) currentEnergy;
        ultCost = currentEnergy;
        super.useUltimate();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        types.add(DamageType.FOLLOW_UP);
        BattleHelpers.PreAttackLogic(this, types);

        for (int i = 0; i < numHits; i++) {
            BattleHelpers.hitEnemy(this, enemy, 1.15f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_HALF_UNIT);
        }
        float finalHitMultiplier = 0.1f;
        if (enemy.weaknessBroken) {
            finalHitMultiplier += 0.15;
        }
        finalHitMultiplier *= numHits;
        BattleHelpers.hitEnemy(this, enemy, finalHitMultiplier, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_HALF_UNIT);

        BattleHelpers.PostAttackLogic(this, types);
        ultCost = REAL_ULT_COST;
        removePower(ultBreakEffBuff);
    }

    private boolean canBreakEnemy(AbstractEnemy enemy) {
        if (enemy.weaknessBroken) {
            return true;
        }
        powerList.add(ultBreakEffBuff);
        if (enemy.toughness <= currentEnergy * BattleHelpers.calculateToughenssDamage(this, TOUGHNESS_DAMAGE_HALF_UNIT)) {
            powerList.remove(ultBreakEffBuff);
            return true;
        }
        powerList.remove(ultBreakEffBuff);
        return false;
    }

    public void takeTurn() {
        super.takeTurn();
        if (firstMove && Battle.battle.numSkillPoints > 0) {
            useSkill();
            firstMove = false;
        } else if (Battle.battle.numSkillPoints > 1) {
            useSkill();
        } else {
            useBasicAttack();
        }
    }

    public void onTurnStart() {
        if (currentEnergy >= ultCost) {
            useUltimate(); // check for ultimate activation at start of turn as well
        }
        FUAReady = true;
    }

    public void onCombatStart() {
        gainStackEnergy(4);
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            AbstractPower feiPower = new FeiTalentPower();
            character.addPower(feiPower);
        }
        addPower(new FeiCritDmgPower());
    }

    public void useTechnique() {
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
            if (!(character instanceof Feixiao)) {
                Feixiao.this.useFollowUp(enemiesHit);
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
                    return 60;
                }
            }
            return 0;
        }
    }
}
