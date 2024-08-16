package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TauntPower;
import powers.TempPower;
import powers.TracePower;

import java.util.ArrayList;
import java.util.HashMap;

public class SwordMarch extends AbstractCharacter {

    public AbstractCharacter master;

    private int numEBA = 0;
    private int numFUAs = 0;
    private int numUltEnhancedEBA;
    private int totalNumExtraHits;
    private String numEBAMetricName = "Enhanced Basic Attacks used";
    private String numFUAsMetricName = "Follow up Attacks used";
    private String numUltEnhancedEBAUsed = "Ult Boosted Enhanced Basic Attacks used";
    private String numExtraHitsMetricName = "Number of extra hits with EBA";
    public int chargeCount = 0;
    public final int chargeThreshold = 7;
    private boolean isEnhanced;
    private boolean hasUltEnhancement;
    private boolean FUAReady = true;

    public SwordMarch() {
        super("Sword March", 1058, 564, 441, 102, 80, ElementType.IMAGINARY, 110, 75, Path.HUNT);

        this.addPower(new TracePower()
                .addStat(PowerStat.ATK_PERCENT, 28)
                .addStat(PowerStat.CRIT_DAMAGE, 24)
                .addStat(PowerStat.DEF_PERCENT, 12.5f));
        this.hasAttackingUltimate = true;
    }

    public void useSkill() {
        super.useSkill();
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            if (character.isDPS) {
                master = character;
                AbstractPower masterPower = new MarchMasterPower();
                Battle.battle.IncreaseSpeed(master, masterPower);
                Battle.battle.IncreaseSpeed(this, PermPower.create(PowerStat.SPEED_PERCENT, 10, "March Speed Boost"));
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
        BattleHelpers.hitEnemy(this, enemy, 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        if (master != null) {
            BattleHelpers.hitEnemy(this, enemy, 0.22f, BattleHelpers.MultiplierStat.ATK, new ArrayList<>(), 0, master.elementType);
        }
        gainCharge(1);

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useEnhancedBasicAttack() {
        moveHistory.add(MoveType.ENHANCED_BASIC);
        numEBA++;
        Battle.battle.addToLog(name + " used Enhanced Basic");
        increaseEnergy(30);

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
        int initialHits = 3;
        int numExtraHits = 0;
        int procChance = 60;

        TempPower ultCritDmgBuff = new TempPower("March Ult Crit Dmg Buff");
        ultCritDmgBuff.setStat(PowerStat.CRIT_DAMAGE, 50);

        TempPower ebaDamageBonus = new TempPower("March Enhanced Basic Damage Bonus");
        ebaDamageBonus.setStat(PowerStat.DAMAGE_BONUS, 88);

        addPower(ebaDamageBonus);
        if (hasUltEnhancement) {
            initialHits += 2;
            procChance = 80;
            numUltEnhancedEBA++;
            addPower(ultCritDmgBuff);
        }
        for (int i = 0; i < 3; i++) {
            double roll = Battle.battle.procChanceRng.nextDouble() * 100 + 1;
            if (roll < procChance) {
                numExtraHits++;
            } else {
                break;
            }
        }
        totalNumExtraHits += numExtraHits;
        Battle.battle.addToLog(String.format("%s rolled %d extra hits", name, numExtraHits));
        for (int i = 0; i < initialHits + numExtraHits; i++) {
            BattleHelpers.hitEnemy(this, enemy, 0.88f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_HALF_UNIT);
            BattleHelpers.hitEnemy(this, enemy, 0.22f, BattleHelpers.MultiplierStat.ATK, new ArrayList<>(), 0, master.elementType);
        }
        if (hasUltEnhancement) {
            hasUltEnhancement = false;
            removePower(ultCritDmgBuff);
        }
        removePower(ebaDamageBonus);
        isEnhanced = false;

        master.addPower(TempPower.create(PowerStat.CRIT_DAMAGE, 60, 2,"Enhanced Basic Master Buff"));

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
            increaseEnergy(5);

            ArrayList<DamageType> types = new ArrayList<>();
            types.add(DamageType.FOLLOW_UP);
            BattleHelpers.PreAttackLogic(this, types);

            BattleHelpers.hitEnemy(this, enemy, 0.6f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
            BattleHelpers.hitEnemy(this, enemy, 0.22f, BattleHelpers.MultiplierStat.ATK, new ArrayList<>(), 0, master.elementType);
            gainCharge(1);

            BattleHelpers.PostAttackLogic(this, types);
        }
    }

    public void useUltimate() {
        super.useUltimate();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        BattleHelpers.PreAttackLogic(this, types);

        AbstractEnemy enemy;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            enemy = Battle.battle.enemyTeam.get(middleIndex);
        } else {
            enemy = Battle.battle.enemyTeam.get(0);
        }
        BattleHelpers.hitEnemy(this, enemy, 2.59f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_THREE_UNITs);
        hasUltEnhancement = true;

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void takeTurn() {
        super.takeTurn();
        if (Battle.battle.numSkillPoints > 0 && firstMove) {
            firstMove = false;
            useSkill();
        } else {
            if (isEnhanced) {
                useEnhancedBasicAttack();
            } else {
                useBasicAttack();
            }
        }
    }

    public void onTurnStart() {
        super.onTurnStart();
        FUAReady = true;
        increaseEnergy(5);
        if (currentEnergy >= ultCost) {
            useUltimate();
        }
    }

    public void onCombatStart() {
        Battle.battle.AdvanceEntity(this, 25);
    }

    public void useTechnique() {
        gainCharge(3);
        increaseEnergy(30);
    }

    public void gainCharge(int amount) {
        int initialCharge = this.chargeCount;
        this.chargeCount += amount;
        Battle.battle.addToLog(String.format("%s gained %d Charge (%d -> %d)", name, amount, initialCharge, chargeCount));
        if (this.chargeCount >= chargeThreshold) {
            this.chargeCount -= chargeThreshold;
            Battle.battle.AdvanceEntity(this, 100);
            isEnhanced = true;
            speedPriority = 0;
        }
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(numFUAsMetricName, String.valueOf(numFUAs));
        map.put(numEBAMetricName, String.valueOf(numEBA));
        map.put(numUltEnhancedEBAUsed, String.valueOf(numUltEnhancedEBA));
        map.put(numExtraHitsMetricName, String.valueOf(totalNumExtraHits));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(numFUAsMetricName);
        list.add(numEBAMetricName);
        list.add(numUltEnhancedEBAUsed);
        list.add(numExtraHitsMetricName);
        return list;
    }

    private class MarchMasterPower extends PermPower {
        public MarchMasterPower() {
            this.name = this.getClass().getSimpleName();
            this.setStat(PowerStat.SPEED_PERCENT, 10.8f);
        }

        @Override
        public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            SwordMarch.this.gainCharge(1);
            if (types.contains(DamageType.BASIC) || types.contains(DamageType.SKILL)) {
                SwordMarch.this.useFollowUp(enemiesHit);
            }
        }

        @Override
        public void onUseUltimate() {
            if (!master.hasAttackingUltimate) {
                SwordMarch.this.gainCharge(1);
            }
        }
    }
}
