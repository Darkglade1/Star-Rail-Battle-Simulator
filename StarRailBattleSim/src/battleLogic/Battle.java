package battleLogic;

import characters.AbstractCharacter;
import characters.Yunli;
import enemies.AbstractEnemy;

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

    public HashMap<AbstractCharacter, Integer> damageContributionMap;

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

    public void updateContribution(AbstractCharacter character, int damageContribution) {
        if (damageContributionMap.containsKey(character)) {
            int existingTotal = damageContributionMap.get(character);
            int updatedTotal = existingTotal + damageContribution;
            damageContributionMap.put(character, updatedTotal);
        } else {
            damageContributionMap.put(character, damageContribution);
        }
    }

    public void Start(int battleLength) {
        int initialBattleLength = battleLength;
        totalPlayerDamage = 0;
        log = "";
        damageContributionMap = new HashMap<>();
        numSkillPoints = INITIAL_SKILL_POINTS;

        HashMap<AbstractEntity, Float> actionValueMap = new HashMap<>();
        for (AbstractEnemy enemy : enemyTeam) {
            actionValueMap.put(enemy, enemy.getBaseAV());
        }
        for (AbstractCharacter character : playerTeam) {
            character.lightcone.onCombatStart();
            actionValueMap.put(character, character.getBaseAV());
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
                break;
            }
            if (yunli != null && nextUnit instanceof AbstractEnemy && yunli.currentEnergy >= yunli.maxEnergy / 2) {
                yunli.useUltimate();
            }
            addToLog("Next is " + nextUnit.name + " at " + nextAV + " action value");
            battleLength -= nextAV;
            for (Map.Entry<AbstractEntity,Float> entry : actionValueMap.entrySet()) {
                float newAV = entry.getValue() - nextAV;
                entry.setValue(newAV);
            }
            nextUnit.takeTurn();
            actionValueMap.put(nextUnit, nextUnit.getBaseAV());
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
}
