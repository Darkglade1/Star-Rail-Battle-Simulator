package battleLogic;

import characters.AbstractCharacter;
import characters.Yunli;
import enemies.AbstractEnemy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Battle {
    public static Battle battle;
    public ArrayList<AbstractCharacter> playerTeam;
    public ArrayList<AbstractEnemy> enemyTeam;

    public final int INITIAL_SKILL_POINTS = 3;
    public int numSkillPoints = INITIAL_SKILL_POINTS;
    public final int MAX_SKILL_POINTS = 5;
    public int totalPlayerDamage;

    public HashMap<AbstractCharacter, Integer> damageContributionMap;

    public void setPlayerTeam(ArrayList<AbstractCharacter> playerTeam) {
        this.playerTeam = playerTeam;
    }

    public void setEnemyTeam(ArrayList<AbstractEnemy> enemyTeam) {
        this.enemyTeam = enemyTeam;
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
        damageContributionMap = new HashMap<>();
        numSkillPoints = INITIAL_SKILL_POINTS;

        HashMap<AbstractEntity, Float> actionValueMap = new HashMap<>();
        for (AbstractCharacter character : playerTeam) {
            actionValueMap.put(character, character.getBaseAV());
        }
        for (AbstractEnemy enemy : enemyTeam) {
            actionValueMap.put(enemy, enemy.getBaseAV());
        }

        Yunli yunli = getYunli();

        while (battleLength > 0) {
            AbstractEntity nextUnit = findLowestAVUnit(actionValueMap);
            float nextAV = actionValueMap.get(nextUnit);
            if (nextAV > battleLength) {
                break;
            }
            System.out.println("AV until battle ends: " + battleLength);
            System.out.println("Next is " + nextUnit.name + " at " + nextAV + " action value");
            battleLength -= nextAV;
            for (Map.Entry<AbstractEntity,Float> entry : actionValueMap.entrySet()) {
                float newAV = entry.getValue() - nextAV;
                entry.setValue(newAV);
            }
            if (yunli != null && nextUnit instanceof AbstractEnemy && yunli.currentEnergy >= yunli.maxEnergy / 2) {
                yunli.useUltimate();
            }
            nextUnit.takeTurn();
            actionValueMap.put(nextUnit, nextUnit.getBaseAV());
        }

        System.out.println("Total player team damage: " + totalPlayerDamage);
        System.out.println("DPAV: " + (float)totalPlayerDamage / initialBattleLength);
        System.out.println(damageContributionMap);
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
}
