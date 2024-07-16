package enemies;

import battleLogic.AbstractEntity;
import battleLogic.Battle;
import battleLogic.BattleHelpers;
import characters.AbstractCharacter;
import powers.AbstractPower;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractEnemy extends AbstractEntity {

    public enum EnemyAttackType {
        AOE(25), BLAST(20), SINGLE(55);

        public final int weight;

        EnemyAttackType(int weight) {
            this.weight = weight;
        }
    }
    public int baseHP;
    public int baseAtk;
    public int baseDef;
    public int level;
    public ArrayList<AbstractPower> powerList;
    protected HashMap<AbstractCharacter.ElementType, Integer> resMap;
    public final int DEFAULT_RES = 20;
    public int toughness = 0;
    public boolean weaknessBroken = false;

    public AbstractEnemy(String name, int baseHP, int baseAtk, int baseDef, int baseSpeed, int level, int toughness) {
        this.name = name;
        this.baseHP = baseHP;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;
        this.baseSpeed = baseSpeed;
        this.level = level;
        this.toughness = toughness;
        powerList = new ArrayList<>();
        setUpDefaultRes();
    }

    public float getFinalAttack() {
        int totalBaseAtk = baseAtk;
        int totalBonusAtkPercent = 0;
        int totalBonusFlatAtk = 0;
        for (AbstractPower power : powerList) {
            totalBonusAtkPercent += power.bonusAtkPercent;
            totalBonusFlatAtk += power.bonusFlatAtk;
        }
        return (int) (totalBaseAtk * (1 + (float)totalBonusAtkPercent / 100) + totalBonusFlatAtk);
    }

    public float getFinalDefense() {
        float totalDefenseBonus = 0;
        float totalDefenseReduction = 0;
        for (AbstractPower power : powerList) {
            totalDefenseBonus += power.bonusDefPercent;
            totalDefenseReduction += power.defenseReduction;
        }
        return totalDefenseBonus - totalDefenseReduction;
    }
    public void setUpDefaultRes() {
        resMap = new HashMap<>();
        resMap.put(AbstractCharacter.ElementType.FIRE, DEFAULT_RES);
        resMap.put(AbstractCharacter.ElementType.ICE, DEFAULT_RES);
        resMap.put(AbstractCharacter.ElementType.WIND, DEFAULT_RES);
        resMap.put(AbstractCharacter.ElementType.LIGHTNING, DEFAULT_RES);
        resMap.put(AbstractCharacter.ElementType.PHYSICAL, DEFAULT_RES);
        resMap.put(AbstractCharacter.ElementType.QUANTUM, DEFAULT_RES);
        resMap.put(AbstractCharacter.ElementType.IMAGINARY, DEFAULT_RES);
    }

    public void setRes(AbstractCharacter.ElementType elementType, int resValue) {
        resMap.put(elementType, resValue);
    }

    public int getRes(AbstractCharacter.ElementType elementType) {
        return resMap.get(elementType);
    }

    public void takeTurn() {
        EnemyAttackType attackType = rollAttackType();
        if (attackType == EnemyAttackType.AOE) {
            Battle.battle.addToLog(String.format("%s uses AoE attack", name));
            for (AbstractCharacter character : Battle.battle.playerTeam) {
                BattleHelpers.attackCharacter(this, character, 10);
            }
        } else {
            double totalWeight= 0.0;
            for (AbstractCharacter character : Battle.battle.playerTeam) {
                totalWeight += character.tauntValue;
            }
            int idx = 0;
            for (double r = Math.random() * totalWeight; idx < Battle.battle.playerTeam.size() - 1; ++idx) {
                r -= Battle.battle.playerTeam.get(idx).tauntValue;
                if (r <= 0.0) break;
            }
            AbstractCharacter target = Battle.battle.playerTeam.get(idx);

            if (attackType == EnemyAttackType.SINGLE) {
                Battle.battle.addToLog(String.format("%s uses single target attack against %s", name, target.name));
                BattleHelpers.attackCharacter(this, target, 10);
            } else {
                Battle.battle.addToLog(String.format("%s uses blast attack against %s", name, target.name));
                BattleHelpers.attackCharacter(this, target, 10);
                if (idx + 1 < Battle.battle.playerTeam.size()) {
                    BattleHelpers.attackCharacter(this, Battle.battle.playerTeam.get(idx + 1), 5);
                }
                if (idx - 1 >= 0) {
                    BattleHelpers.attackCharacter(this, Battle.battle.playerTeam.get(idx - 1), 5);
                }
            }
        }
    }

    public EnemyAttackType rollAttackType() {
        double totalWeight= 0.0;
        for (EnemyAttackType type : EnemyAttackType.values()) {
            totalWeight += type.weight;
        }
        int idx = 0;
        for (double r = Math.random() * totalWeight; idx < EnemyAttackType.values().length - 1; ++idx) {
            r -= EnemyAttackType.values()[idx].weight;
            if (r <= 0.0) break;
        }
        return EnemyAttackType.values()[idx];
    }

    @Override
    public float getBaseAV() {
        return (float)10000 / baseSpeed;
    }
}
