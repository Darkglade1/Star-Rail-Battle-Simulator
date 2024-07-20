package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.TauntPower;
import powers.TempPower;

import java.util.ArrayList;

public class SwordMarch extends AbstractCharacter {

    public AbstractCharacter master;

    private int numEBA = 0;
    private int numFUAs = 0;
    private int numUltEnhancedEBA;
    public int chargeCount = 0;
    public final int chargeThreshold = 7;
    private boolean isEnhanced;
    private boolean hasUltEnhancement;
    private boolean FUAReady = true;

    public SwordMarch() {
        super("Sword March", 1058, 564, 441, 102, 80, ElementType.IMAGINARY, 110, 75);
        PermPower tracesPower = new PermPower();
        tracesPower.name = "Traces Stat Bonus";
        tracesPower.bonusAtkPercent = 28f;
        tracesPower.bonusCritDamage = 24f;
        tracesPower.bonusDefPercent = 12.5f;
        this.addPower(tracesPower);
    }

    public void useSkill() {
        super.useSkill();
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            if (character.isDPS) {
                master = character;
                AbstractPower masterPower = new MarchMasterPower();
                Battle.battle.IncreaseSpeed(master, masterPower);

                PermPower marchSpeedBoost = new PermPower();
                marchSpeedBoost.bonusSpeedPercent = 10;
                marchSpeedBoost.name = "March Speed Boost";
                Battle.battle.IncreaseSpeed(this, marchSpeedBoost);
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
        BattleHelpers.hitEnemy(this, enemy, 1.1f, BattleHelpers.MultiplierStat.ATK, types, 30);
        BattleHelpers.hitEnemy(this, enemy, 0.22f, BattleHelpers.MultiplierStat.ATK, new ArrayList<>(), 0, master.elementType);
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
        TempPower ultCritDmgBuff = new TempPower();
        ultCritDmgBuff.bonusCritDamage = 50;
        ultCritDmgBuff.name = "March Ult Crit Dmg Buff";
        TempPower ebaDamageBonus = new TempPower();
        ebaDamageBonus.bonusDamageBonus = 88;
        ebaDamageBonus.name = "March Enhanced Basic Damage Bonus";
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
            }
        }
        Battle.battle.addToLog(String.format("%s rolled %d extra hits", name, numExtraHits));
        for (int i = 0; i < initialHits + numExtraHits; i++) {
            BattleHelpers.hitEnemy(this, enemy, 0.88f, BattleHelpers.MultiplierStat.ATK, types, 15);
            BattleHelpers.hitEnemy(this, enemy, 0.22f, BattleHelpers.MultiplierStat.ATK, new ArrayList<>(), 0, master.elementType);
        }
        if (hasUltEnhancement) {
            hasUltEnhancement = false;
            removePower(ultCritDmgBuff);
        }
        removePower(ebaDamageBonus);
        isEnhanced = false;

        TempPower ebaMasterBuff = new TempPower();
        ebaMasterBuff.bonusCritDamage = 60;
        ebaMasterBuff.turnDuration = 2;
        ebaMasterBuff.name = "Enhanced Basic Master Buff";
        master.addPower(ebaMasterBuff);

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useFollowUp(ArrayList<AbstractEnemy> enemiesHit) {
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

        BattleHelpers.hitEnemy(this, enemy, 0.6f, BattleHelpers.MultiplierStat.ATK, types, 30);
        BattleHelpers.hitEnemy(this, enemy, 0.22f, BattleHelpers.MultiplierStat.ATK, new ArrayList<>(), 0, master.elementType);
        gainCharge(1);

        BattleHelpers.PostAttackLogic(this, types);
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
        BattleHelpers.hitEnemy(this, enemy, 2.59f, BattleHelpers.MultiplierStat.ATK, types, 90);
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

    public String getMetrics() {
        String metrics = super.getMetrics();
        String charSpecificMetrics = String.format("\nFollow Ups: %d \nEBAs: %d \nUlt Boosted EBAs: %d", numFUAs, numEBA, numUltEnhancedEBA);
        return metrics + charSpecificMetrics;
    }

    private class MarchMasterPower extends AbstractPower {
        public MarchMasterPower() {
            this.name = this.getClass().getSimpleName();
            this.bonusSpeedPercent = 10.8f;
            this.lastsForever = true;
        }

        @Override
        public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            SwordMarch.this.gainCharge(1);
            if (types.contains(DamageType.BASIC) || types.contains(DamageType.SKILL)) {
                SwordMarch.this.useFollowUp(enemiesHit);
            }
        }

        public void onUseUltimate() {
            SwordMarch.this.gainCharge(1);
        }
    }
}
