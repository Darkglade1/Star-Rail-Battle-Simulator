package characters.lingsha;

import battleLogic.*;
import battleLogic.log.lines.character.EmergencyHeal;
import battleLogic.log.lines.character.lingsha.FuYuanGain;
import battleLogic.log.lines.character.lingsha.FuYuanLose;
import battleLogic.log.lines.character.lingsha.HitSinceLastHeal;
import battleLogic.log.lines.character.lingsha.ResetTracker;
import characters.AbstractCharacter;
import characters.AbstractSummoner;
import characters.Path;
import characters.goal.shared.DontUltNumby;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TracePower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lingsha extends AbstractSummoner<Lingsha> {
    public static String NAME = "Lingsha";

    FuYuan fuYuan;
    AbstractPower damageTrackerPower;
    static final int fuYuanMaxHitCount = 5;
    static final int skillHitCountGain = 3;
    int fuYuanCurrentHitCount = 0;
    private static final int emergencyHealCooldown = 2;
    private int currentEmergencyHealCD = 0;
    private HashMap<AbstractCharacter<?>, Integer> characterTimesDamageTakenMap = new HashMap<>();
    private int fuYuanAttacksMetric = 0;
    private String fuYuanAttacksMetricName = "Number of Fu Yuan Attacks";
    private int numEmergencyHeals = 0;
    private String numEmergencyHealsMetricName = "Number of Emergency Heal Triggers";
    private String leftoverAVFuYuanMetricName = "Leftover AV (Fu Yuan)";

    public Lingsha() {
        super(NAME, 1358, 679, 437, 98, 80, ElementType.FIRE, 110, 100, Path.ABUNDANCE);

        this.addPower(new TracePower()
                .setStat(PowerStat.HP_PERCENT, 18)
                .setStat(PowerStat.BREAK_EFFECT, 37.3f)
                .setStat(PowerStat.ATK_PERCENT, 10));

        this.hasAttackingUltimate = true;
        this.basicEnergyGain = 30;

        damageTrackerPower = new LingshaEmergencyHealTracker();
        fuYuan = new FuYuan(this);

        this.registerGoal(0, new DontUltNumby<>(this));
        this.registerGoal(10, new LingshaUltGoal(this));
        this.registerGoal(0, new LingshaTurnGoal(this));
    }

    @Override
    public float getFinalAttack() {
        if (!getBattle().inCombat()) {
            return super.getFinalAttack();
        } else {
            float atkBonus = 0.25f * getTotalBreakEffect();
            if (atkBonus > 50) {
                atkBonus = 50;
            }
            return super.getFinalAttack() + ((baseAtk + lightcone.baseAtk) *  (1 + atkBonus / 100));
        }
    }

    public void useBasic() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useSkill() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);
        getBattle().getHelper().PreAttackLogic(this, types);

        for (AbstractEnemy enemy : getBattle().getEnemies()) {
            getBattle().getHelper().hitEnemy(this, enemy, 0.8f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        }
        increaseHitCount(skillHitCountGain);
        getBattle().AdvanceEntity(fuYuan, 20);
        fuYuan.speedPriority = 1;
        resetDamageTracker();

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        getBattle().getHelper().PreAttackLogic(this, types);

        for (AbstractEnemy enemy : getBattle().getEnemies()) {
            AbstractPower besotted = new Befog();
            enemy.addPower(besotted);
            getBattle().getHelper().hitEnemy(this, enemy, 1.5f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);
        }
        getBattle().AdvanceEntity(fuYuan, 100);

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void FuYuanAttack(boolean useHitCount) {
        fuYuanAttacksMetric++;

        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.FOLLOW_UP);
        getBattle().getHelper().PreAttackLogic(this, types);

        ArrayList<AbstractEnemy> nonBrokenEnemies = new ArrayList<>();
        for (AbstractEnemy enemy : getBattle().getEnemies()) {
            getBattle().getHelper().hitEnemy(this, enemy, 0.75f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
            if (!enemy.weaknessBroken) {
                nonBrokenEnemies.add(enemy);
            }
        }
        if (nonBrokenEnemies.isEmpty()) {
            getBattle().getHelper().hitEnemy(this, getBattle().getRandomEnemy(), 0.75f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        } else {
            AbstractEnemy randomNonBrokenEnemy = nonBrokenEnemies.get(getBattle().getGetRandomEnemyRng().nextInt(nonBrokenEnemies.size()));
            getBattle().getHelper().hitEnemy(this, randomNonBrokenEnemy, 0.75f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        }
        if (useHitCount) {
            decreaseHitCount(1);
        }
        resetDamageTracker();

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    private void increaseHitCount(int amount) {
        if (fuYuanMaxHitCount <= 0) {
            getBattle().getActionValueMap().put(fuYuan, fuYuan.getBaseAV());
        }
        int initalStack = fuYuanCurrentHitCount;
        fuYuanCurrentHitCount += amount;
        if (fuYuanCurrentHitCount > fuYuanMaxHitCount) {
            fuYuanCurrentHitCount = fuYuanMaxHitCount;
        }
        getBattle().addToLog(new FuYuanGain(amount, initalStack, fuYuanCurrentHitCount));
    }

    private void decreaseHitCount(int amount) {
        int initalStack = fuYuanCurrentHitCount;
        fuYuanCurrentHitCount -= amount;
        if (fuYuanCurrentHitCount <= 0) {
            fuYuanCurrentHitCount = 0;
            getBattle().getActionValueMap().remove(fuYuan);
        }
        getBattle().addToLog(new FuYuanLose(amount, initalStack, fuYuanCurrentHitCount));
    }

    public void onTurnStart() {
        if (currentEmergencyHealCD > 0) {
            currentEmergencyHealCD--;
        }
    }

    public void onCombatStart() {
        getBattle().getActionValueMap().put(fuYuan, fuYuan.getBaseAV());
        increaseHitCount(skillHitCountGain);
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            characterTimesDamageTakenMap.put(character, 0);
            character.addPower(damageTrackerPower);
        }
    }

    public void useTechnique() {
        for (AbstractEnemy enemy : getBattle().getEnemies()) {
            AbstractPower befog = new Befog();
            enemy.addPower(befog);
        }
    }

    public void resetDamageTracker() {
        getBattle().addToLog(new ResetTracker());
        for (Map.Entry<AbstractCharacter<?>, Integer> entry : characterTimesDamageTakenMap.entrySet()) {
            entry.setValue(0);
        }
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(leftoverAVFuYuanMetricName, String.valueOf(getBattle().getActionValueMap().get(fuYuan)));
        map.put(fuYuanAttacksMetricName, String.valueOf(fuYuanAttacksMetric));
        map.put(numEmergencyHealsMetricName, String.valueOf(numEmergencyHeals));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(leftoverAVFuYuanMetricName);
        list.add(fuYuanAttacksMetricName);
        list.add(numEmergencyHealsMetricName);
        return list;
    }

    @Override
    public List<AbstractSummon<Lingsha>> getSummons() {
        return Collections.singletonList(fuYuan);
    }

    private static class Befog extends AbstractPower {
        public Befog() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 2;
            this.type = PowerType.DEBUFF;
        }

        @Override
        public float getConditionalDamageTaken(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<DamageType> damageTypes) {
            if (damageTypes.contains(DamageType.BREAK)) {
                return 25f;
            }
            return 0;
        }
    }

    private class LingshaEmergencyHealTracker extends PermPower {
        public LingshaEmergencyHealTracker() {
            this.name = this.getClass().getSimpleName();
        }

        @Override
        public void onAttacked(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyToGain) {
            int timesHit = characterTimesDamageTakenMap.get(character);
            timesHit++;
            getBattle().addToLog(new HitSinceLastHeal(character, timesHit));
            if (timesHit >= 2 && currentEmergencyHealCD <= 0) {
                getBattle().addToLog(new EmergencyHeal(Lingsha.this));
                numEmergencyHeals++;
                currentEmergencyHealCD = emergencyHealCooldown;
                Lingsha.this.FuYuanAttack(false);
            } else {
                characterTimesDamageTakenMap.put(character, timesHit);
            }
        }
    }
}
