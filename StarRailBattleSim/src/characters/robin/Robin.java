package characters.robin;

import battleLogic.*;
import characters.AbstractCharacter;
import characters.Path;
import characters.goal.shared.AlwaysUltGoal;
import characters.goal.shared.DontUltNumby;
import characters.goal.shared.SkillCounterTurnGoal;
import characters.goal.shared.UltAtEndOfBattle;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TracePower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Robin extends AbstractCharacter<Robin> implements SkillCounterTurnGoal.SkillCounterCharacter {

    PermPower skillPower = PermPower.create(PowerStat.DAMAGE_BONUS, 50, "Robin Skill Power");
    RobinUltPower ultPower = new RobinUltPower();
    RobinFixedCritPower fixedCritPower = new RobinFixedCritPower();
    Concerto concerto = new Concerto(this);

    private int skillCounter = 0;
    private int allyAttacksMetric = 0;
    private int concertoProcs = 0;
    private String allyAttacksMetricName = "Number of Ally Attacks";
    private String concertoProcsMetricName = "Number of Concerto Hits";
    public static final String NAME = "Robin";
    public static final String ULT_POWER_NAME = "RobinUltPower";

    public Robin() {
        super(NAME, 1281, 640, 485, 102, 80, ElementType.PHYSICAL, 160, 100, Path.HARMONY);

        this.skillEnergyGain = 35;

        this.addPower(new TracePower()
                .setStat(PowerStat.ATK_PERCENT, 28)
                .setStat(PowerStat.HP_PERCENT, 18)
                .setStat(PowerStat.FLAT_SPEED, 5));

        this.registerGoal(0, new SkillCounterTurnGoal<>(this));

        this.registerGoal(0, new UltAtEndOfBattle<>(this));
        this.registerGoal(10, new Robin0AVUltGoal(this));
        this.registerGoal(20, new DontUltNumby<>(this));
        this.registerGoal(30, new RobinBroynaFeixiaoUltGoal(this));
        this.registerGoal(100, new AlwaysUltGoal<>(this));
    }

    public void useSkill() {
        skillCounter = 3;
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.addPower(skillPower);
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

    public void useUltimate() {
        AbstractEntity slowestAlly = null;
        float slowestAV = -1;
        AbstractEntity fastestAlly = null;
        float fastestAV = -1;
        AbstractEntity middleAlly = null;
        for (Map.Entry<AbstractEntity,Float> entry : getBattle().getActionValueMap().entrySet()) {
            if (entry.getKey() instanceof AbstractCharacter && !(entry.getKey() instanceof Robin)) {
                if (slowestAlly == null) {
                    slowestAlly = entry.getKey();
                    fastestAlly = entry.getKey();
                    middleAlly = entry.getKey();
                    slowestAV = entry.getValue();
                    fastestAV = entry.getValue();
                } else {
                    if (entry.getValue() < fastestAV) {
                        fastestAlly = entry.getKey();
                        fastestAV = entry.getValue();
                    } else if (entry.getValue() > slowestAV) {
                        slowestAlly = entry.getKey();
                        slowestAV = entry.getValue();
                    }
                }
            }
        }
        for (Map.Entry<AbstractEntity,Float> entry : getBattle().getActionValueMap().entrySet()) {
            if (entry.getKey() instanceof AbstractCharacter && !(entry.getKey() instanceof Robin)) {
                if (entry.getKey() != fastestAlly && entry.getKey() != slowestAlly) {
                    middleAlly = entry.getKey();
                }
            }
        }

        // preserves the order in which allies go next based on their original AVs
        // most recently advanced ally will go first
        getBattle().AdvanceEntity(slowestAlly, 100);
        getBattle().AdvanceEntity(middleAlly, 100);
        getBattle().AdvanceEntity(fastestAlly, 100);

        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.addPower(ultPower);
        }
        this.addPower(fixedCritPower);
        getBattle().getActionValueMap().remove(this);
        getBattle().getActionValueMap().put(concerto, concerto.getBaseAV());
    }

    public void onCombatStart() {
        getBattle().AdvanceEntity(this, 25);
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.addPower(new RobinTalentPower());
        }
    }

    public void onTurnStart() {
        if (skillCounter > 0) {
            skillCounter--;
            if (skillCounter <= 0) {
                for (AbstractCharacter<?> character : getBattle().getPlayers()) {
                    character.removePower(skillPower);
                }
            }
        }
    }

    public void useTechnique() {
        increaseEnergy(5, TECHNIQUE_ENERGY_GAIN);
    }

    public void addPower(AbstractPower power) {
        super.addPower(power);
        if (ultPower != null) {
            ultPower.updateAtkBuff();
        }
    }

    public void onConcertoEnd() {
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.removePower(ultPower);
        }
        this.removePower(fixedCritPower);
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(allyAttacksMetricName, String.valueOf(allyAttacksMetric));
        map.put(concertoProcsMetricName, String.valueOf(concertoProcs));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(allyAttacksMetricName);
        list.add(concertoProcsMetricName);
        return list;
    }

    public HashMap<String, String> addLeftoverCharacterAVMetric(HashMap<String, String> metricMap) {
        Float leftoverAV = getBattle().getActionValueMap().get(this);
        if (leftoverAV == null) {
            leftoverAV = getBattle().getActionValueMap().get(concerto);
            metricMap.put(leftoverAVMetricName, String.format("%.2f (Concerto)", leftoverAV));
        } else {
            return super.addLeftoverCharacterAVMetric(metricMap);
        }

        return metricMap;
    }

    @Override
    public int getSkillCounter() {
        return skillCounter;
    }

    private class RobinTalentPower extends PermPower {
        public RobinTalentPower() {
            this.name = this.getClass().getSimpleName();
            this.setStat(PowerStat.CRIT_DAMAGE, 20);
        }

        @Override
        public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            Robin.this.increaseEnergy(2, TALENT_ENERGY_GAIN);
            Robin.this.allyAttacksMetric++;
        }
    }

    private class RobinUltPower extends AbstractPower {
        public RobinUltPower() {
            this.name = ULT_POWER_NAME;
            lastsForever = true;
        }

        public void updateAtkBuff() {
            float atk = getFinalAttackWithoutConcerto();
            this.setStat(PowerStat.FLAT_ATK, (int)(0.228 * atk) + 200);
        }

        private float getFinalAttackWithoutConcerto() {
            return Robin.this.getFinalAttack() - this.getStat(PowerStat.FLAT_ATK);
        }

        @Override
        public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            AbstractEnemy target = enemiesHit.get(getBattle().getGetRandomEnemyRng().nextInt(enemiesHit.size()));
            getBattle().getHelper().additionalDamageHitEnemy(Robin.this, target, 1.2f, BattleHelpers.MultiplierStat.ATK);
            concertoProcs++;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (damageTypes.contains(DamageType.FOLLOW_UP)) {
                return 25;
            }
            return 0;
        }
    }

    private class RobinFixedCritPower extends AbstractPower {
        public RobinFixedCritPower() {
            this.name = this.getClass().getSimpleName();
            lastsForever = true;
        }

        @Override
        public float setFixedCritRate(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes, float currentCrit) {
            return 100;
        }

        @Override
        public float setFixedCritDmg(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes, float currentCritDmg) {
            return 150;
        }
    }
}
