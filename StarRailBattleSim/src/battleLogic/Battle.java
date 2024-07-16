package battleLogic;

import characters.AbstractCharacter;
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
    public final int MAX_SKILL_POINTS = 5;
    public int totalPlayerDamage;
    public String log = "";

    public HashMap<AbstractCharacter, Float> damageContributionMap;
    public HashMap<AbstractEntity, Float> actionValueMap;

    public void setPlayerTeam(ArrayList<AbstractCharacter> playerTeam) {
        this.playerTeam = playerTeam;
    }

    public void setEnemyTeam(ArrayList<AbstractEnemy> enemyTeam) {
        this.enemyTeam = enemyTeam;
    }

    public AbstractEnemy getRandomEnemy() {
        Random rand = new Random();
        return Battle.battle.enemyTeam.get(rand.nextInt(Battle.battle.enemyTeam.size()));
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
            addToLog("ERROR - SKILL POINTS WENT NEGATIVE");
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

    public void Start(int battleLength) {
        int initialBattleLength = battleLength;
        totalPlayerDamage = 0;
        log = "";
        damageContributionMap = new HashMap<>();
        numSkillPoints = INITIAL_SKILL_POINTS;
        actionValueMap = new HashMap<>();

        for (AbstractEnemy enemy : enemyTeam) {
            actionValueMap.put(enemy, enemy.getBaseAV());
        }
        for (AbstractCharacter character : playerTeam) {
            character.lightcone.onCombatStart();
            character.onCombatStart();
            for (AbstractRelicSetBonus relicSetBonus : character.relicSetBonus) {
                relicSetBonus.onCombatStart();
            }
            actionValueMap.put(character, character.getBaseAV());
        }

        for (AbstractCharacter character : playerTeam) {
            if (character.useTechnique) {
                character.useTechnique();
            }
        }

        Yunli yunli = getYunli();

        while (battleLength > 0) {
            addToLog("AV until battle ends: " + battleLength);
            AbstractEntity nextUnit = findLowestAVUnit(actionValueMap);
            float nextAV = actionValueMap.get(nextUnit);
            if (nextAV > battleLength) {
                for (Map.Entry<AbstractEntity,Float> entry : actionValueMap.entrySet()) {
                    float newAV = entry.getValue() - battleLength;
                    entry.setValue(newAV);
                }
                break;
            }
            if (yunli != null && nextUnit instanceof AbstractEnemy && yunli.currentEnergy >= yunli.ultCost) {
                yunli.useUltimate();
            }
            addToLog("Next is " + nextUnit.name + " at " + nextAV + " action value " + actionValueMap);
            battleLength -= nextAV;
            for (Map.Entry<AbstractEntity,Float> entry : actionValueMap.entrySet()) {
                float newAV = entry.getValue() - nextAV;
                entry.setValue(newAV);
            }
            for (AbstractPower power : nextUnit.powerList) {
                power.onTurnStart();
            }
            nextUnit.onTurnStart();
            nextUnit.takeTurn();
            ArrayList<AbstractPower> powersToRemove = new ArrayList<>();
            for (AbstractPower power : nextUnit.powerList) {
                power.onEndTurn();
                if (!power.lastsForever && power.turnDuration <= 0) {
                    powersToRemove.add(power);
                }
            }
            for (AbstractPower power : powersToRemove) {
                nextUnit.removePower(power);
            }
            float presentAV = actionValueMap.get(nextUnit); // this is so being weakness broken from a counter will delay the next action
            actionValueMap.put(nextUnit, nextUnit.getBaseAV() + presentAV);

            for (AbstractCharacter character : playerTeam) {
                if (character.currentEnergy >= character.ultCost && !(character instanceof Yunli)) {
                    character.useUltimate();
                }
            }
        }

        addToLog("Total player team damage: " + totalPlayerDamage);
        addToLog("DPAV: " + (float)totalPlayerDamage / initialBattleLength);
        addToLog(damageContributionMap.toString());
        System.out.println(log);
    }

    private Yunli getYunli() {
        for (AbstractCharacter character : playerTeam) {
            if (character instanceof Yunli) {
                return (Yunli) character;
            }
        }
        return null;
    }

    private AbstractEntity findLowestAVUnit(HashMap<AbstractEntity, Float> actionValueMap) {
        AbstractEntity next = null;
        float nextAV = 999.0f;
        for (Map.Entry<AbstractEntity,Float> entry : actionValueMap.entrySet()) {
            if (entry.getValue() < nextAV) {
                next = entry.getKey();
                nextAV = entry.getValue();
            }
        }
        return next;
    }

    public void addToLog(String addition) {
        log += addition + "\n";
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
}
