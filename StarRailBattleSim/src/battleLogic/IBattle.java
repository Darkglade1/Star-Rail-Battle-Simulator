package battleLogic;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import powers.AbstractPower;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public interface IBattle {

    boolean usedEntryTechnique();
    void setUsedEntryTechnique(boolean usedEntryTechnique);

    List<AbstractCharacter> getPlayers();
    boolean hasCharacter(String name);

    List<AbstractEnemy> getEnemies();
    AbstractEnemy getMiddleEnemy();
    AbstractEnemy getRandomEnemy();

    AbstractEntity getNextUnit();

    boolean isAboutToEnd();
    boolean inCombat();

    void AdvanceEntity(AbstractEntity entity, float advanceAmount);
    void DelayEntity(AbstractEntity entity, float delayAmount);
    void IncreaseSpeed(AbstractEntity entity, AbstractPower speedPower);
    void DecreaseSpeed(AbstractEntity entity, AbstractPower speedPower);

    void useSkillPoint(AbstractCharacter character, int amount);
    void generateSkillPoint(AbstractCharacter character, int amount);
    void increaseMaxSkillPoints(int maxSkillPoints);
    int getSkillPoints();

    void addToLog(String addition);

    HashMap<AbstractCharacter, Float> getDamageContributionMap();
    HashMap<AbstractCharacter, Float> getDamageContributionMapPercent();
    HashMap<AbstractEntity, Float> getActionValueMap();
    void updateContribution(AbstractCharacter character, float damageContribution);
    void increaseTotalPlayerDmg(float dmg);
    float initialLength();

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
