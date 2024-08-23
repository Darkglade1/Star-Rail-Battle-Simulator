package battleLogic;

import battleLogic.log.Loggable;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public interface IBattle {

    void setPlayerTeam(List<AbstractCharacter<?>> players);
    void setEnemyTeam(List<AbstractEnemy> enemies);
    void Start(float AV);

    boolean usedEntryTechnique();
    void setUsedEntryTechnique(boolean usedEntryTechnique);

    List<AbstractCharacter<?>> getPlayers();
    boolean hasCharacter(String name);
    AbstractCharacter<?> getCharacter(String name);

    List<AbstractEnemy> getEnemies();
    AbstractEnemy getMiddleEnemy();
    AbstractEnemy getRandomEnemy();

    AbstractEntity getNextUnit();
    void setNextUnit(AbstractEntity entity);
    AbstractEntity getUnit(int index);

    boolean isAboutToEnd();
    boolean inCombat();

    void AdvanceEntity(AbstractEntity entity, float advanceAmount);
    void DelayEntity(AbstractEntity entity, float delayAmount);
    void IncreaseSpeed(AbstractEntity entity, AbstractPower speedPower);
    void DecreaseSpeed(AbstractEntity entity, AbstractPower speedPower);

    void useSkillPoint(AbstractCharacter<?> character, int amount);
    void generateSkillPoint(AbstractCharacter<?> character, int amount);
    void increaseMaxSkillPoints(int maxSkillPoints);
    int getSkillPoints();

    void addToLog(Loggable addition);

    HashMap<AbstractCharacter<?>, Float> getDamageContributionMap();
    HashMap<AbstractCharacter<?>, Float> getDamageContributionMapPercent();
    HashMap<AbstractEntity, Float> getActionValueMap();
    int getTotalPlayerDmg();
    float getActionValueUsed();
    float getFinalDPAV();
    int getTotalSkillPointsUsed();
    int getTotalSkillPointsGenerated();
    void updateContribution(AbstractCharacter<?> character, float damageContribution);
    void increaseTotalPlayerDmg(float dmg);
    float initialLength();
    float battleLength();

    BattleHelpers getHelper();

    long getSeed();
    Random getEnemyMoveRng();
    Random getEnemyTargetRng();
    Random getCritChanceRng();
    Random getGetRandomEnemyRng();
    Random getProcChanceRng();
    Random getGambleChanceRng();
    Random getQpqRng();
    Random getMilkyWayRng();
    Random getWeaveEffectRng();
    Random getAetherRng();



}
