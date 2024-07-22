package battleLogic;

import characters.AbstractCharacter;
import characters.SwordMarch;
import characters.Yunli;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import relicSetBonus.AbstractRelicSetBonus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Battle {
    public static Battle battle;
    public ArrayList<AbstractCharacter> playerTeam;
    public ArrayList<AbstractEnemy> enemyTeam;

    public final int INITIAL_SKILL_POINTS = 3;
    public int numSkillPoints = INITIAL_SKILL_POINTS;
    public int MAX_SKILL_POINTS = 5;
    public int totalPlayerDamage;
    public float finalDPAV;
    public String log = "";
    public float initialBattleLength;

    public HashMap<AbstractCharacter, Float> damageContributionMap;
    public HashMap<AbstractCharacter, Float> damageContributionMapPercent;
    public HashMap<AbstractEntity, Float> actionValueMap;

    long seed = 154172837382L;
    public Random enemyMoveRng = new Random(seed);
    public Random enemyTargetRng = new Random(seed);
    public Random critChanceRng = new Random(seed);
    public Random getRandomEnemyRng = new Random(seed);
    public Random procChanceRng = new Random(seed);
    public Random gambleChanceRng = new Random(seed);

    public void setPlayerTeam(ArrayList<AbstractCharacter> playerTeam) {
        this.playerTeam = playerTeam;
    }

    public void setEnemyTeam(ArrayList<AbstractEnemy> enemyTeam) {
        this.enemyTeam = enemyTeam;
    }

    public AbstractEnemy getRandomEnemy() {
        return Battle.battle.enemyTeam.get(getRandomEnemyRng.nextInt(Battle.battle.enemyTeam.size()));
    }

    public void updateContribution(AbstractCharacter character, float damageContribution) {
        if (damageContributionMap.containsKey(character)) {
            float existingTotal = damageContributionMap.get(character);
            float updatedTotal = existingTotal + damageContribution;
            damageContributionMap.put(character, updatedTotal);
        } else {
            damageContributionMap.put(character, damageContribution);
        }
    }

    public void useSkillPoint(AbstractCharacter character, int amount) {
        int initialSkillPoints = numSkillPoints;
        numSkillPoints -= amount;
        addToLog(String.format("%s used %d Skill Point(s) (%d -> %d)", character.name, amount, initialSkillPoints, numSkillPoints));
        if (numSkillPoints < 0) {
            throw new RuntimeException("ERROR - SKILL POINTS WENT NEGATIVE");
        }
    }

    public void generateSkillPoint(AbstractCharacter character, int amount) {
        int initialSkillPoints = numSkillPoints;
        numSkillPoints += amount;
        if (numSkillPoints > MAX_SKILL_POINTS) {
            numSkillPoints = MAX_SKILL_POINTS;
        }
        addToLog(String.format("%s generated %d Skill Point(s) (%d -> %d)", character.name, amount, initialSkillPoints, numSkillPoints));
    }

    public void Start(float battleLength) {
        initialBattleLength = battleLength;
        totalPlayerDamage = 0;
        log = "Combat Start\n";
        damageContributionMap = new HashMap<>();
        damageContributionMapPercent = new HashMap<>();
        numSkillPoints = INITIAL_SKILL_POINTS;
        actionValueMap = new HashMap<>();

        for (AbstractEnemy enemy : enemyTeam) {
            actionValueMap.put(enemy, enemy.getBaseAV());
        }
        for (AbstractCharacter character : playerTeam) {
            character.generateStatsString();
            character.generateStatsReport();
        }
        for (AbstractCharacter character : playerTeam) {
            actionValueMap.put(character, character.getBaseAV());
            character.lightcone.onCombatStart();
            character.onCombatStart();
            for (AbstractRelicSetBonus relicSetBonus : character.relicSetBonus) {
                relicSetBonus.onCombatStart();
            }
        }

        addToLog("Triggering Techniques");
        for (AbstractCharacter character : playerTeam) {
            if (character.useTechnique) {
                character.useTechnique();
            }
        }

        Yunli yunli = getYunli();
        SwordMarch march = getSwordMarch();

        while (battleLength > 0) {
            addToLog("AV until battle ends: " + battleLength);
            AbstractEntity nextUnit = findLowestAVUnit(actionValueMap);
            float nextAV = actionValueMap.get(nextUnit);
            if (nextAV > battleLength) {
                for (Map.Entry<AbstractEntity,Float> entry : actionValueMap.entrySet()) {
                    float newAV = entry.getValue() - battleLength;
                    entry.setValue(newAV);
                }
                addToLog("Battle ended, leftover AV: " + actionValueMap);
                break;
            }
            if (yunli != null && nextUnit instanceof AbstractEnemy && yunli.currentEnergy >= yunli.ultCost) {
                yunli.useUltimate();
                if (march != null && march.chargeCount >= march.chargeThreshold) {
                    nextUnit = march;
                    nextAV = actionValueMap.get(nextUnit);
                }
            }
            addToLog("Next is " + nextUnit.name + " at " + nextAV + " action value " + actionValueMap);
            battleLength -= nextAV;
            for (Map.Entry<AbstractEntity,Float> entry : actionValueMap.entrySet()) {
                float newAV = entry.getValue() - nextAV;
                entry.setValue(newAV);
            }
            if (nextUnit instanceof Concerto) {
                addToLog("Concerto ends, it's now Robin's turn");
                actionValueMap.remove(nextUnit);
                ((Concerto) nextUnit).owner.onConcertoEnd();
                nextUnit = ((Concerto) nextUnit).owner;
            }
            ArrayList<AbstractPower> powersToTrgger = new ArrayList<>(nextUnit.powerList);
            for (AbstractPower power : powersToTrgger) {
                power.onTurnStart();
            }
            nextUnit.onTurnStart();
            if (nextUnit instanceof AbstractEnemy || nextUnit instanceof AbstractCharacter) {
                actionValueMap.put(nextUnit, nextUnit.getBaseAV());
            }
            nextUnit.takeTurn();
            ArrayList<AbstractPower> powersToRemove = new ArrayList<>();
            for (AbstractPower power : nextUnit.powerList) {
                power.onEndTurn();
                if (!power.lastsForever && power.turnDuration <= 0) {
                    powersToRemove.add(power);
                }
            }
            for (AbstractPower power : powersToRemove) {
                if (power.bonusSpeedPercent > 0 || power.bonusFlatSpeed > 0) {
                    DecreaseSpeed(nextUnit, power);
                } else {
                    nextUnit.removePower(power);
                }
            }

            if (yunli != null && yunli.isParrying) {
                yunli.useSlash(getRandomEnemy());
            }

            for (AbstractCharacter character : playerTeam) {
                if (character.currentEnergy >= character.ultCost && !(character instanceof Yunli)) {
                    character.useUltimate();
                }
            }
            // check again in case of energy regen ultimates
            for (AbstractCharacter character : playerTeam) {
                if (character.currentEnergy >= character.ultCost && !(character instanceof Yunli)) {
                    character.useUltimate();
                }
            }

            if (nextUnit instanceof Numby) {
                actionValueMap.put(nextUnit, nextUnit.getBaseAV());
            }
        }

        addToLog("");
        addToLog("Player Metrics:");
        for (AbstractCharacter character : playerTeam) {
            addToLog(character.getMetrics());
            addToLog("");
        }
        addToLog("Enemy Metrics:");
        for (AbstractEnemy enemy : enemyTeam) {
            addToLog(enemy.getMetrics());
            addToLog("");
        }
        addToLog(String.format("Total player team damage: %d \nAction Value used: %.1f", totalPlayerDamage, initialBattleLength));
        finalDPAV = (float)totalPlayerDamage / initialBattleLength;
        addToLog("DPAV: " + finalDPAV);
        for (AbstractCharacter character : playerTeam) {
            if (!damageContributionMap.containsKey(character)) {
                damageContributionMap.put(character, 0.0f);
            }
        }
        addToLog("Damage Contribution: | " + calcPercentContributionString());
        System.out.println(log);
    }

    public String calcPercentContributionString() {
        StringBuilder log = new StringBuilder();
        for (Map.Entry<AbstractCharacter,Float> entry : damageContributionMap.entrySet()) {
            float percent = entry.getValue() / totalPlayerDamage * 100;
            damageContributionMapPercent.put(entry.getKey(), percent);
            log.append(String.format("%s: %.3f (%.3f%%) | ", entry.getKey().name, entry.getValue(), percent));
        }
        return log.toString();
    }

    private Yunli getYunli() {
        for (AbstractCharacter character : playerTeam) {
            if (character instanceof Yunli) {
                return (Yunli) character;
            }
        }
        return null;
    }

    private SwordMarch getSwordMarch() {
        for (AbstractCharacter character : playerTeam) {
            if (character instanceof SwordMarch) {
                return (SwordMarch) character;
            }
        }
        return null;
    }

    private AbstractEntity findLowestAVUnit(HashMap<AbstractEntity, Float> actionValueMap) {
        AbstractEntity next = null;
        float nextAV = 999.0f;
        ArrayList<AbstractEntity> speedTieList = new ArrayList<>();
        for (Map.Entry<AbstractEntity,Float> entry : actionValueMap.entrySet()) {
            if (entry.getValue() < nextAV) {
                next = entry.getKey();
                nextAV = entry.getValue();
                speedTieList.clear();
            } else if (entry.getValue() == nextAV) {
                speedTieList.add(entry.getKey());
            }
        }
        if (speedTieList.size() != 0) {
            int lowestSpeedTie = next.speedPriority;
            for (AbstractEntity entity : speedTieList) {
                if (entity.speedPriority < lowestSpeedTie) {
                    next = entity;
                    lowestSpeedTie = entity.speedPriority;
                }
            }
        }
        return next;
    }

    public void addToLog(String addition) {
        log += addition + "\n";
    }

    public void AdvanceEntity(AbstractEntity entity, int advanceAmount) {
        for (Map.Entry<AbstractEntity,Float> entry : actionValueMap.entrySet()) {
            if (entry.getKey() == entity) {
                float baseAV = entity.getBaseAV();
                float AVDecrease = baseAV * ((float)advanceAmount / 100);
                float originalAV = entry.getValue();
                float newAV = originalAV - AVDecrease;
                if (newAV < 0) {
                    newAV = 0;
                }
                entry.setValue(newAV);
                addToLog(String.format("%s advanced by %d%% (%.3f -> %.3f)", entry.getKey().name, advanceAmount, originalAV, newAV));
            }
        }
    }

    public void DelayEntity(AbstractEntity entity, int delayAmount) {
        for (Map.Entry<AbstractEntity,Float> entry : actionValueMap.entrySet()) {
            if (entry.getKey() == entity) {
                float baseAV = entity.getBaseAV();
                float AVIncrease = baseAV * ((float)delayAmount / 100);
                float originalAV = entry.getValue();
                float newAV = originalAV + AVIncrease;
                entry.setValue(newAV);
                addToLog(String.format("%s delayed by %d%% (%.3f -> %.3f)", entry.getKey().name, delayAmount, originalAV, newAV));
            }
        }
    }

    public void IncreaseSpeed(AbstractEntity entity, AbstractPower speedPower) {
        float baseAV = entity.getBaseAV();
        float currAV = actionValueMap.get(entity);
        float percentToNextAction = (baseAV - currAV) / baseAV;

        entity.addPower(speedPower);
        float newBaseAV = entity.getBaseAV();
        float newCurrAV = newBaseAV - (percentToNextAction * newBaseAV);
        actionValueMap.put(entity, newCurrAV);

        addToLog(String.format("%s advanced by speed increase (%.3f -> %.3f)", entity.name, currAV, newCurrAV));
    }

    public void DecreaseSpeed(AbstractEntity entity, AbstractPower speedPower) {
        float baseAV = entity.getBaseAV();
        float currAV = actionValueMap.get(entity);
        float percentToNextAction = (baseAV - currAV) / baseAV;

        entity.removePower(speedPower);
        float newBaseAV = entity.getBaseAV();
        float newCurrAV = newBaseAV - (percentToNextAction * newBaseAV);
        actionValueMap.put(entity, newCurrAV);

        addToLog(String.format("%s delayed by speed decrease (%.3f -> %.3f)", entity.name, currAV, newCurrAV));
    }
}
