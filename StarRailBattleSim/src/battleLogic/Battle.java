package battleLogic;

import characters.AbstractCharacter;
import characters.SwordMarch;
import characters.Yunli;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PowerStat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Battle implements IBattle {
    public ArrayList<AbstractCharacter> playerTeam;
    public ArrayList<AbstractEnemy> enemyTeam;

    private BattleHelpers battleHelpers;

    public final int INITIAL_SKILL_POINTS = 3;
    public int numSkillPoints = INITIAL_SKILL_POINTS;
    public int MAX_SKILL_POINTS = 5;
    public int totalPlayerDamage;
    public float finalDPAV;
    public int totalSkillPointsUsed = 0;
    public int totalSkillPointsGenerated = 0;
    public String log = "";
    public float initialBattleLength;
    public float battleLength;
    public AbstractEntity nextUnit;
    public boolean usedEntryTechnique = false;
    public boolean isInCombat = false;
    public int actionForwardPriorityCounter = AbstractEntity.SPEED_PRIORITY_DEFAULT;

    public HashMap<AbstractCharacter, Float> damageContributionMap;
    public HashMap<AbstractCharacter, Float> damageContributionMapPercent;
    public HashMap<AbstractEntity, Float> actionValueMap;

    protected static long seed = 154172837382L;
    public Random enemyMoveRng = new Random(seed);
    public Random enemyTargetRng = new Random(seed);
    public Random critChanceRng = new Random(seed);
    public Random getRandomEnemyRng = new Random(seed);
    public Random procChanceRng = new Random(seed);
    public Random gambleChanceRng = new Random(seed);
    public Random qpqRng = new Random(seed);
    public Random milkyWayRng = new Random(seed);
    public Random weaveEffectRng = new Random(seed);
    public Random aetherRng = new Random(seed);

    public Battle() {
        this.battleHelpers = new BattleHelpers(this);
    }

    public void setPlayerTeam(ArrayList<AbstractCharacter> playerTeam) {
        this.playerTeam = playerTeam;
        this.playerTeam.forEach(character -> character.setBattle(this));
    }

    public void setEnemyTeam(ArrayList<AbstractEnemy> enemyTeam) {
        this.enemyTeam = enemyTeam;
        this.enemyTeam.forEach(enemy -> enemy.setBattle(this));
    }

    @Override
    public AbstractEnemy getRandomEnemy() {
        return this.enemyTeam.get(getRandomEnemyRng.nextInt(this.enemyTeam.size()));
    }

    @Override
    public AbstractEntity getNextUnit() {
        return this.nextUnit;
    }

    @Override
    public boolean isAboutToEnd() {
        AbstractEntity next = findLowestAVUnit(actionValueMap);
        return actionValueMap.get(next) > battleLength;
    }

    @Override
    public boolean inCombat() {
        return this.isInCombat;
    }

    @Override
    public void updateContribution(AbstractCharacter character, float damageContribution) {
        if (damageContributionMap.containsKey(character)) {
            float existingTotal = damageContributionMap.get(character);
            float updatedTotal = existingTotal + damageContribution;
            damageContributionMap.put(character, updatedTotal);
        } else {
            damageContributionMap.put(character, damageContribution);
        }
    }

    @Override
    public void increaseTotalPlayerDmg(float dmg) {
        totalPlayerDamage += dmg;
    }

    @Override
    public float initialLength() {
        return this.initialBattleLength;
    }

    @Override
    public BattleHelpers getHelper() {
        return this.battleHelpers;
    }

    @Override
    public void useSkillPoint(AbstractCharacter character, int amount) {
        int initialSkillPoints = numSkillPoints;
        numSkillPoints -= amount;
        totalSkillPointsUsed += amount;
        addToLog(String.format("%s used %d Skill Point(s) (%d -> %d)", character.name, amount, initialSkillPoints, numSkillPoints));
        if (numSkillPoints < 0) {
            throw new RuntimeException("ERROR - SKILL POINTS WENT NEGATIVE");
        }
    }

    @Override
    public void generateSkillPoint(AbstractCharacter character, int amount) {
        int initialSkillPoints = numSkillPoints;
        numSkillPoints += amount;
        totalSkillPointsGenerated += amount;
        if (numSkillPoints > MAX_SKILL_POINTS) {
            numSkillPoints = MAX_SKILL_POINTS;
        }
        addToLog(String.format("%s generated %d Skill Point(s) (%d -> %d)", character.name, amount, initialSkillPoints, numSkillPoints));
    }

    @Override
    public void increaseMaxSkillPoints(int maxSkillPoints) {
        MAX_SKILL_POINTS += maxSkillPoints;
    }

    @Override
    public int getSkillPoints() {
        return this.numSkillPoints;
    }

    public void Start(float battleLength) {
        Start(battleLength, false);
    }

    public void Start(float initialLength, boolean onlyDmg) {
        initialBattleLength = initialLength;
        this.battleLength = initialLength;
        totalPlayerDamage = 0;
        log = "Combat Start\n";
        damageContributionMap = new HashMap<>();
        damageContributionMapPercent = new HashMap<>();
        numSkillPoints = INITIAL_SKILL_POINTS;
        actionValueMap = new HashMap<>();
        usedEntryTechnique = false;

        for (AbstractEnemy enemy : enemyTeam) {
            actionValueMap.put(enemy, enemy.getBaseAV());
        }
        for (AbstractCharacter character : playerTeam) {
            character.generateStatsString();
            character.generateStatsReport();
        }
        isInCombat = true;
        for (AbstractCharacter character : playerTeam) {
            actionValueMap.put(character, character.getBaseAV());
            character.emit(BattleEvents::onCombatStart);
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
            nextUnit = findLowestAVUnit(actionValueMap);
            float nextAV = actionValueMap.get(nextUnit);
            if (nextAV > battleLength) {
                for (Map.Entry<AbstractEntity,Float> entry : actionValueMap.entrySet()) {
                    float newAV = entry.getValue() - battleLength;
                    entry.setValue(newAV);
                }
                addToLog("Battle ended");
                isInCombat = false;
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
                actionValueMap.put(nextUnit, 0.0f);
            }
            nextUnit.emit(BattleEvents::onTurnStart);
            if (nextUnit instanceof AbstractEnemy || nextUnit instanceof AbstractCharacter) {
                if (actionValueMap.get(nextUnit) <= 0) {
                    actionValueMap.put(nextUnit, nextUnit.getBaseAV());
                }
            }
            nextUnit.takeTurn();
            nextUnit.emit(BattleEvents::onEndTurn);
            ArrayList<AbstractPower> powersToRemove = new ArrayList<>();
            for (AbstractPower power : nextUnit.powerList) {
                if (!power.lastsForever && power.turnDuration <= 0) {
                    powersToRemove.add(power);
                }
            }
            for (AbstractPower power : powersToRemove) {
                if (power.getStat(PowerStat.SPEED_PERCENT) > 0 || power.getStat(PowerStat.FLAT_SPEED) > 0) {
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

            if (nextUnit instanceof AbstractSummon) {
                actionValueMap.put(nextUnit, nextUnit.getBaseAV());
            }

            // check again for optimizing robin's ult around numby
            for (AbstractCharacter character : playerTeam) {
                if (character.currentEnergy >= character.ultCost && !(character instanceof Yunli)) {
                    character.useUltimate();
                }
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
        addToLog("Skill Points Used: " + totalSkillPointsUsed);
        addToLog("Skill Points Generated: " + totalSkillPointsGenerated);
        addToLog("Leftover AV: " + actionValueMap);
        StringBuilder leftoverEnergy = new StringBuilder();
        for (AbstractCharacter character : playerTeam) {
            leftoverEnergy.append(String.format("| %s: %.2f | ", character.name, character.currentEnergy));
            if (!damageContributionMap.containsKey(character)) {
                damageContributionMap.put(character, 0.0f);
            }
        }
        addToLog("Leftover Energy: " + leftoverEnergy);

        if (onlyDmg) {
            System.out.println("Damage Contribution: | " + calcPercentContributionString() + "Total Damage " + totalPlayerDamage);
        } else {
            addToLog("Damage Contribution: | " + calcPercentContributionString());
            System.out.println(log);
        }
    }

    public String calcPercentContributionString() {
        StringBuilder log = new StringBuilder();
        damageContributionMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> {
                    float percent = entry.getValue() / totalPlayerDamage * 100;
                    damageContributionMapPercent.put(entry.getKey(), percent);
                    log.append(String.format("%s: %.3f DPAV (%.3f%%) | ", entry.getKey().name, entry.getValue() / initialBattleLength, percent));
                });
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

    @Override
    public boolean usedEntryTechnique() {
        return this.usedEntryTechnique;
    }

    @Override
    public void setUsedEntryTechnique(boolean usedEntryTechnique) {
        this.usedEntryTechnique = usedEntryTechnique;
    }

    @Override
    public List<AbstractCharacter> getPlayers() {
        return this.playerTeam;
    }

    @Override
    public boolean hasCharacter(String name) {
        for (AbstractCharacter character : playerTeam) {
            if (character.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public AbstractCharacter getCharacter(String name) {
        for (AbstractCharacter character : playerTeam) {
            if (character.name.equals(name)) {
                return character;
            }
        }
        return null;
    }

    @Override
    public List<AbstractEnemy> getEnemies() {
        return this.enemyTeam;
    }

    @Override
    public AbstractEnemy getMiddleEnemy() {
        AbstractEnemy enemy;
        if (this.enemyTeam.size() >= 3) {
            int middleIndex = this.enemyTeam.size() / 2;
            enemy = this.enemyTeam.get(middleIndex);
        } else {
            enemy = this.enemyTeam.get(0);
        }
        return enemy;
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

    @Override
    public void addToLog(String addition) {
        String timestamp = String.format("(%.2f AV) - ", initialBattleLength - battleLength);
        if (!isInCombat) {
            timestamp = "";
        }
        log += timestamp + addition + "\n";
    }

    @Override
    public HashMap<AbstractCharacter, Float> getDamageContributionMap() {
        return this.damageContributionMap;
    }

    @Override
    public HashMap<AbstractCharacter, Float> getDamageContributionMapPercent() {
        return this.damageContributionMapPercent;
    }

    @Override
    public HashMap<AbstractEntity, Float> getActionValueMap() {
        return this.actionValueMap;
    }

    @Override
    public void AdvanceEntity(AbstractEntity entity, float advanceAmount) {
        for (Map.Entry<AbstractEntity,Float> entry : actionValueMap.entrySet()) {
            if (entry.getKey() == entity) {
                float baseAV = entity.getBaseAV();
                float AVDecrease = baseAV * (advanceAmount / 100);
                float originalAV = entry.getValue();
                float newAV = originalAV - AVDecrease;
                if (newAV < 0) {
                    newAV = 0;
                }
                entry.setValue(newAV);
                actionForwardPriorityCounter--;
                entity.speedPriority = actionForwardPriorityCounter;
                addToLog(String.format("%s advanced by %.1f%% (%.3f -> %.3f)", entry.getKey().name, advanceAmount, originalAV, newAV));
            }
        }
    }

    @Override
    public void DelayEntity(AbstractEntity entity, float delayAmount) {
        for (Map.Entry<AbstractEntity,Float> entry : actionValueMap.entrySet()) {
            if (entry.getKey() == entity) {
                float baseAV = entity.getBaseAV();
                float AVIncrease = baseAV * (delayAmount / 100);
                float originalAV = entry.getValue();
                float newAV = originalAV + AVIncrease;
                entry.setValue(newAV);
                addToLog(String.format("%s delayed by %.1f%% (%.3f -> %.3f)", entry.getKey().name, delayAmount, originalAV, newAV));
            }
        }
    }

    @Override
    public void IncreaseSpeed(AbstractEntity entity, AbstractPower speedPower) {
        float baseAV = entity.getBaseAV();
        Float currAV = actionValueMap.get(entity);
        if (currAV == null) {
            entity.addPower(speedPower);
            return;
        }
        float percentToNextAction = (baseAV - currAV) / baseAV;

        entity.addPower(speedPower);
        float newBaseAV = entity.getBaseAV();
        float newCurrAV = newBaseAV - (percentToNextAction * newBaseAV);
        actionValueMap.put(entity, newCurrAV);

        addToLog(String.format("%s advanced by speed increase (%.3f -> %.3f)", entity.name, currAV, newCurrAV));
    }

    @Override
    public void DecreaseSpeed(AbstractEntity entity, AbstractPower speedPower) {
        float baseAV = entity.getBaseAV();
        Float currAV = actionValueMap.get(entity);
        if (currAV == null) {
            entity.removePower(speedPower);
            return;
        }
        float percentToNextAction = (baseAV - currAV) / baseAV;

        entity.removePower(speedPower);
        float newBaseAV = entity.getBaseAV();
        float newCurrAV = newBaseAV - (percentToNextAction * newBaseAV);
        actionValueMap.put(entity, newCurrAV);

        addToLog(String.format("%s delayed by speed decrease (%.3f -> %.3f)", entity.name, currAV, newCurrAV));
    }

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public Random getEnemyMoveRng() {
        return enemyMoveRng;
    }

    @Override
    public Random getEnemyTargetRng() {
        return enemyTargetRng;
    }

    @Override
    public Random getCritChanceRng() {
        return critChanceRng;
    }

    @Override
    public Random getGetRandomEnemyRng() {
        return getRandomEnemyRng;
    }

    @Override
    public Random getProcChanceRng() {
        return procChanceRng;
    }

    @Override
    public Random getGambleChanceRng() {
        return gambleChanceRng;
    }

    @Override
    public Random getQpqRng() {
        return qpqRng;
    }

    @Override
    public Random getMilkyWayRng() {
        return milkyWayRng;
    }

    @Override
    public Random getWeaveEffectRng() {
        return weaveEffectRng;
    }

    @Override
    public Random getAetherRng() {
        return aetherRng;
    }
}
